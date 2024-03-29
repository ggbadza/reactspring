package com.tankmilu.spring.service;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.tankmilu.spring.dto.UserRegisterDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tankmilu.spring.dto.UserLoginDto;
import com.tankmilu.spring.dto.UserModifyDto;
import com.tankmilu.spring.entity.User;
import com.tankmilu.spring.enums.UserRole;
import com.tankmilu.spring.enums.UserState;
import com.tankmilu.spring.repository.UserRepository;
import com.tankmilu.spring.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    //kakao api 세팅값
    @Value("${spring.security.oauth2.client.registration.Kakao.client-id}")
    private String kakaoClientId;
    @Value("${spring.security.oauth2.client.registration.Kakao.authorization-grant-type}")
    private String kakaoGrantType;

    @Value("${spring.security.oauth2.client.registration.Naver.client-id}")
    private String naverClientId;
    @Value("${spring.security.oauth2.client.registration.Naver.authorization-grant-type}")
    private String naverGrantType;
    @Value("${spring.security.oauth2.client.registration.Naver.client-secret}")
    private String naverClientsecret;

    public User register(UserRegisterDto userRegisterDto){
        if(userRepository.existsByEmail(userRegisterDto.getEmail())){
            throw new IllegalArgumentException("이미 가입된 이메일 입니다.");
        }
        User user = User.builder()
                    .email(userRegisterDto.getEmail())
                    .password(bcryptPasswordEncoder.encode(userRegisterDto.getPassword()))
                    .nickname(userRegisterDto.getNickname())
                    .phone(userRegisterDto.getPhone())
                    .role(UserRole.USER)
                    .state(UserState.NOMAL).build();
        return userRepository.save(user);
    }
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public User findUser(int uid) {
        return userRepository.findById(uid).orElseThrow(() -> {
                return new IllegalArgumentException("유저 ID를 찾을 수 없습니다.");
            }
        );
    }

    public User findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
                return new IllegalArgumentException("유저 Email을 찾을 수 없습니다.");
            }
        );
    }

    //유저 삭제 처리
    public User deleteUser(int uid) {
        User user = userRepository.findById(uid).orElseThrow(() -> {
            return new IllegalArgumentException("유저 ID를 찾을 수 없습니다.");
        }
    );
        user.setState(UserState.DELETED);
        return userRepository.save(user);
    }

    @Transactional
    public User modifyUser(UserModifyDto userModifyDto,int uid) {
        User user = userRepository.findById(uid).orElseThrow(() -> new IllegalArgumentException("유저 ID를 찾을 수 없습니다.")); // 로그인 한 계정 uid
        
        // if(userModifyDto.getEmail()!=null){user.setEmail(userModifyDto.getEmail());} // 계정명 변경 제외
        if(userModifyDto.getPassword()!=null){user.setPassword(bcryptPasswordEncoder.encode(userModifyDto.getPassword()));}
        if(userModifyDto.getName()!=null){user.setNickname(userModifyDto.getName());}
        // if(userModifyDto.getRole()!=null){user.setRole(userModifyDto.getRole());} // 역할 변경 제외
        if(userModifyDto.getPhone()!=null){user.setPhone(userModifyDto.getPhone());}
        return user;
    }

    @Transactional
    public User modifyUserByAdmin(UserModifyDto userModifyDto) {
        User user = userRepository.findById(userModifyDto.getUid()).orElseThrow(() -> new IllegalArgumentException("유저 ID를 찾을 수 없습니다.")); // uid 따로 요청
        
        if(userModifyDto.getEmail()!=null){user.setEmail(userModifyDto.getEmail());}
        if(userModifyDto.getPassword()!=null){user.setPassword(bcryptPasswordEncoder.encode(userModifyDto.getPassword()));}
        if(userModifyDto.getName()!=null){user.setNickname(userModifyDto.getName());}
        if(userModifyDto.getRole()!=null){user.setRole(userModifyDto.getRole());}
        if(userModifyDto.getPhone()!=null){user.setPhone(userModifyDto.getPhone());}
        return user;
    }


    @Transactional(readOnly = true)
    public void login(UserLoginDto loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if(!bcryptPasswordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 토큰값 쿠키 설정
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail(), user.getRole()).substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    // ---------------------- 카카오 OAUTH -------------------------------------
    public String kakaoToken(String code, String url) throws JsonProcessingException {
        // 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // 보디
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", kakaoGrantType);
        body.add("client_id", kakaoClientId);
        body.add("redirect_uri", url);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // JSON에서 토큰 추출
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    @Transactional
    public User registerKakao(int uid,String token) throws JsonProcessingException {
        // 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String kakaoId = jsonNode.get("id").asText();

        // 카카오 ID 등록
        User user = userRepository.findById(uid).orElseThrow(() -> new IllegalArgumentException("유저 ID를 찾을 수 없습니다.")); // 로그인 한 계정 uid
        if(user.getKakaoId()!=null){
            throw new IllegalArgumentException("이미 카카오 계정이 등록되어 있습니다.");
        }
        user.setKakaoId(kakaoId);
        return user;
    }

    @Transactional(readOnly = true)
    public void kakaoLogin(String token, HttpServletResponse res) throws JsonProcessingException {
        // 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String id = jsonNode.get("id").asText();

        // 사용자 확인
        User user = userRepository.findByKakaoId(id).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다. 회원가입을 진행하신 뒤 카카오 계정을 등록해주세요.")
        );

        // 토큰값 쿠키 설정
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail(), user.getRole()).substring(7));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        res.addCookie(cookie);
    }

    // ---------------------- 네이버 OAUTH -------------------------------------
    public String naverToken(String code, String url) throws JsonProcessingException {
        // 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // 보디
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", naverGrantType);
        body.add("client_id", naverClientId);
        body.add("client_secret",naverClientsecret);
        body.add("redirect_uri", url);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // JSON에서 토큰 추출
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    @Transactional
    public User registerNaver(int uid,String token) throws JsonProcessingException {
        // 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> naverUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                naverUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody).path("response");
        String naverId = jsonNode.get("id").asText();

        // 네이버 ID 등록
        User user = userRepository.findById(uid).orElseThrow(() -> new IllegalArgumentException("유저 ID를 찾을 수 없습니다.")); // 로그인 한 계정 uid
        if(user.getNaverId()!=null){
            throw new IllegalArgumentException("이미 네이버 계정이 등록되어 있습니다.");
        }
        user.setNaverId(naverId);
        return user;
    }

    @Transactional(readOnly = true)
    public void naverLogin(String token, HttpServletResponse res) throws JsonProcessingException {
        // 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> naverUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                naverUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody).path("response");
        String id = jsonNode.get("id").asText();

        // 사용자 확인
        User user = userRepository.findByNaverId(id).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다. 회원가입을 진행하신 뒤 네이버 계정을 등록해주세요.")
        );

        // 토큰값 쿠키 설정
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail(), user.getRole()).substring(7));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        res.addCookie(cookie);
    }
}
