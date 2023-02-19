package com.tankmilu.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tankmilu.spring.dto.ItemDto;
import com.tankmilu.spring.entity.Item;
import com.tankmilu.spring.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item register(ItemDto itemDto){
        Item item = new Item();
        item.setItemName(itemDto.getName());
        item.setCategory(itemDto.getCategory());;
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());
        item.setStockQuantity(itemDto.getStockQuantity());
        item.setSellStatus(itemDto.getSellStatus());
        item.setSellerUid(itemDto.getSeller());
        return itemRepository.save(item);
    }
    
    public List<Item> findAll() {
        return itemRepository.findAll();
    }
    
    public Item findItem(int itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> {
                return new IllegalArgumentException("상품의 ID를 찾을 수 없습니다.");
            }
        );
    }
}
