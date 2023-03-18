package com.tankmilu.spring.service;

import org.springframework.stereotype.Service;

import com.tankmilu.spring.dto.OrderPageDto;
import com.tankmilu.spring.entity.Item;
import com.tankmilu.spring.entity.Order;
import com.tankmilu.spring.repository.ItemRepository;
import com.tankmilu.spring.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    public OrderPageDto getdetails (int userDetails, int itemId, int itemCount){
        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            return new IllegalArgumentException("상품의 ID를 찾을 수 없습니다.");
        });
        // 쿠폰 할인 메커니즘 구현 필요
        return OrderPageDto.builder()
                                .itemId(itemId)
                                .itemCount(itemCount)
                                .itemName(item.getItemName())
                                .itemPrice(item.getPrice())
                                .salePrice(0) //쿠폰 할인 구현 필요
                                .totalPrice(item.getPrice()-0).build();
         
    }

    public Order takeorder(OrderPageDto orderPageDto,int uid){
        //오더 변조 검증기능 구현 필요
        Order order = Order.builder()
                            .buyerId(uid)
                            .itemId(orderPageDto.getItemId())
                            .itemCount(orderPageDto.getItemCount())
                            .originalPrice(orderPageDto.getItemPrice())
                            .discountNomal(orderPageDto.getDiscountNomal())
                            .discountDuplicated(orderPageDto.getDiscountDuplicated())
                            .discountCard(orderPageDto.getDiscountCard())
                            .discountedPrice(orderPageDto.getTotalPrice()).build();
        return orderRepository.save(order);
    }

}
