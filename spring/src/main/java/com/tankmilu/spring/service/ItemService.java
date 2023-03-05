package com.tankmilu.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Transactional(readOnly = true)
    public List<Item> getItemList(int page, int size ,HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size, sort);
    }
}
