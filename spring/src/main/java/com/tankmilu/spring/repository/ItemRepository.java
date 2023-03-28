package com.tankmilu.spring.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tankmilu.spring.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Page<Item> findByItemNameContainingIgnoreCase(String itemName,Pageable pageable);
}
