package com.tankmilu.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tankmilu.spring.dto.UserRegisterDto;
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

    public User register(UserRegisterDto userRegisterDto){
        User user = User.builder()
                    .email(userRegisterDto.getEmail())
                    .password(bcryptPasswordEncoder.encode(userRegisterDto.getPassword()))
                    .nickname(userRegisterDto.getName())
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
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail(), user.getRole()));
    }
}
