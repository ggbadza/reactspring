package com.tankmilu.spring.entity;

import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.CreatedDate;

import com.tankmilu.spring.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    @Column(nullable = false, length = 20)
    private String id;

    @Column(nullable = false, length = 20)
    private String username;  

    @Column(nullable = false, length = 32)    
    private String password;

    @Column(nullable = true, length = 30)
    private String email;

    @Column(nullable = true, length = 11)
    private int phone;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @CreatedDate
    private Timestamp registerDate;

}

