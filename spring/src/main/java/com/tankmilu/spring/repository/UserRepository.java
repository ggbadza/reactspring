package com.tankmilu.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tankmilu.spring.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByNaverId(String naverId);
    Optional<User> findByKakaoId(String kakaoId);
}
