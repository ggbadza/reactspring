package com.tankmilu.spring.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginDto {
    private String email;
    private String password;
}
