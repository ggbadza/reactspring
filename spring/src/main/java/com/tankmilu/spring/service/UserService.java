package com.tankmilu.spring.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tankmilu.spring.dto.UserRegisterDto;
import com.tankmilu.spring.dto.UserModifyDto;
import com.tankmilu.spring.entity.User;
import com.tankmilu.spring.enums.UserRole;
import com.tankmilu.spring.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public User register(UserRegisterDto userRegisterDto){
        User user = new User();
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(bcryptPasswordEncoder.encode(userRegisterDto.getPasswd()));
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
}
