package com.tankmilu.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tankmilu.spring.dto.ItemDto;
import com.tankmilu.spring.service.ItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    
    @GetMapping("/getitem")
    public ResponseEntity<?> GetItem(@RequestParam(value="id") int itemId) {
        var data = itemService.findItem(itemId);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/register")
    public ResponseEntity<?> RegisterItem(@RequestBody ItemDto itemRequest) {
        var data = itemService.register(itemRequest);
        return ResponseEntity.ok().body(data);
    }
}
