package com.tankmilu.spring.dto;

import com.tankmilu.spring.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModifyDto {
    private int uid;
    private String email;
    private String passwd;
    private String name;
    private UserRole role;
    private Integer phone;
}
