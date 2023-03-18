package com.tankmilu.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tankmilu.spring.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    
}
