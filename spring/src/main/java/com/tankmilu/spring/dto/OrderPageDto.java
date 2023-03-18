package com.tankmilu.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPageDto {
    private int itemId;
    private int itemCount;

    private String itemName;
    private int itemPrice;

    private Integer discountNomal;
    private Integer discountDuplicated;
    private Integer discountCard;

    private int salePrice;
    private int totalPrice;
}
