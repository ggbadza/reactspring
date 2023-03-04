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
        User user = new User();
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(bcryptPasswordEncoder.encode(userRegisterDto.getPassword()));
        user.setNickname(userRegisterDto.getName());
        user.setRole(UserRole.USER);
        return userRepository.save(user);
    }
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public User findUser(int id) {
        return userRepository.findById(id).orElseThrow(() -> {
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

    @Transactional
    public User modifyUser(UserModifyDto userModifyDto) {
        User user = userRepository.findById(userModifyDto.getUid()).orElseThrow(() -> new IllegalArgumentException("유저 ID를 찾을 수 없습니다."));
        
        if(userModifyDto.getEmail()!=null){user.setEmail(userModifyDto.getEmail());}
        if(userModifyDto.getPasswd()!=null){user.setPassword(bcryptPasswordEncoder.encode(userModifyDto.getPasswd()));}
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
