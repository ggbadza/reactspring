package com.tankmilu.spring.dto;

import com.tankmilu.spring.enums.ItemStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {
    String name;
    Integer price;
    Integer stockQuantity;
    Integer category;
    String description;
    ItemStatus sellStatus;
    Integer seller;
}
