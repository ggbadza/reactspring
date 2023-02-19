package com.tankmilu.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tankmilu.spring.dto.UserModifyDto;
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

    @GetMapping("/userlist")
    public ResponseEntity<?> UserList() {
        var data = userService.findAll();
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/getuser")
    public ResponseEntity<?> GetUser(@RequestParam(value="uid") int uid) {
        var data = userService.findUser(uid);
        return ResponseEntity.ok().body(data);
    }

    @PutMapping("/modify")
    public ResponseEntity<?> Modify(@RequestBody UserModifyDto userRequest) {
        var data = userService.modifyUser(userRequest);
        return ResponseEntity.ok().body(data);
    }

    

}
