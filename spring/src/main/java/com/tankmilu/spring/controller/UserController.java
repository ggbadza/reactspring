package com.tankmilu.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tankmilu.spring.dto.UserRegisterDto;
import com.tankmilu.spring.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    @GetMapping("/hi")
    public String hi(){
        return "<html><body><h1>Hello, ResponseBody!</h1></body></html>";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> Signup(@RequestBody UserRegisterDto userRequest) {
        var data = userService.register(userRequest);
        return ResponseEntity.ok().body(data);
    }

}
