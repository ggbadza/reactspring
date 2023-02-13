package com.tankmilu.spring.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tankmilu.spring.dto.UserRegisterDto;
import com.tankmilu.spring.entity.User;
import com.tankmilu.spring.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public User register(UserRegisterDto userRegisterDto){
        User user = new User();
        user.setId(userRegisterDto.getId());
        user.setPassword(bcryptPasswordEncoder.encode(userRegisterDto.getPasswd()));
        user.setUsername(userRegisterDto.getName());
        // user.setRole("user");
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
}
