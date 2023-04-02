package com.tankmilu.spring.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tankmilu.spring.enums.UserRole;
import com.tankmilu.spring.enums.UserState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="Users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true, length = 20)
    private String nickname;  

    @Column(nullable = false, length = 64)    
    private String password;

    @Column(nullable = true, length = 11)
    private String phone;

    @Column(nullable = true)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @CreatedDate
    private LocalDateTime registerDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserState state;

    @Column(nullable = true, unique = true)
    private String naverId;

    @Column(nullable = true, unique = true)
    private Long kakaoId;


    

}

