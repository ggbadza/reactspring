package com.tankmilu.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @GetMapping("/hi")
    public String hi(){
        return "<html><body><h1>Hello, ResponseBody!</h1></body></html>";
    }
}
