package com.tankmilu.spring.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.tankmilu.spring.entity.Item;

public interface ItemElasticsearchRepository extends ElasticsearchRepository<Item, Integer> {

    List<Item> findByItemName(String name, Pageable pageable);

}
