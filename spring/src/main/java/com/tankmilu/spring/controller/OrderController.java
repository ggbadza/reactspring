package com.tankmilu.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tankmilu.spring.dto.OrderGetDto;
import com.tankmilu.spring.dto.OrderPageDto;
import com.tankmilu.spring.security.CustomUserDetails;
import com.tankmilu.spring.service.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    //주문 정보 획득(order페이지)
    @PostMapping("/getdetails")
    public ResponseEntity<?> OrderGetDetails(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody OrderGetDto orderGetDto) {
        var data = orderService.getdetails(userDetails.getUid(), orderGetDto.getItemId(), orderGetDto.getItemCount());
        return ResponseEntity.ok().body(data);
    }

    //상품 주문
    @PostMapping("/takeorder")
    public ResponseEntity<?> TakeOrder(@AuthenticationPrincipal CustomUserDetails userDetails,@RequestBody OrderPageDto orderPageDto) {
        var data = orderService.takeorder(orderPageDto, userDetails.getUid());
        return ResponseEntity.ok().body(data);
    }
    
}
