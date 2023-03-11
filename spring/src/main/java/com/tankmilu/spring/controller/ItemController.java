package com.tankmilu.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tankmilu.spring.dto.ItemDto;
import com.tankmilu.spring.dto.SearchItemDto;
import com.tankmilu.spring.security.CustomUserDetails;
import com.tankmilu.spring.service.ItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    
    //상품 세부정보 획득
    @GetMapping("/itemdetail")
    public ResponseEntity<?> GetItem(@RequestParam(value="id") int itemId) {
        var data = itemService.findItem(itemId);
        return ResponseEntity.ok().body(data);
    }

    //상품 리스트 획득
    @GetMapping("/itemlist")
    public ResponseEntity<?> getItemList(@RequestParam SearchItemDto searchItemDto) {
        var data = itemService.getItemList(searchItemDto);
        return ResponseEntity.ok().body(data);
    }

    //판매자가 상품 등록(판매자 지정 불가)
    @PostMapping("/register")
    public ResponseEntity<?> RegisterItem(@AuthenticationPrincipal CustomUserDetails userDetails,@RequestBody ItemDto itemRequest) {
        var data = itemService.register(itemRequest,userDetails.getUid());
        return ResponseEntity.ok().body(data);
    }

    //어드민이 상품 등록(판매자 지정 가능)
    @Secured("ROLE_ADMIN") 
    @PostMapping("/registerbyadmin")
    public ResponseEntity<?> RegisterItemAdmin(@RequestBody ItemDto itemRequest) {
        var data = itemService.registerByAdmin(itemRequest);
        return ResponseEntity.ok().body(data);
    }
}
