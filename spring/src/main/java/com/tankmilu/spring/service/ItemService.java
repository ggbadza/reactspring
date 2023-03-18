package com.tankmilu.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tankmilu.spring.dto.ItemDto;
import com.tankmilu.spring.dto.SearchItemDto;
import com.tankmilu.spring.entity.Item;
import com.tankmilu.spring.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item register(ItemDto itemDto,int uid){
        Item item = new Item();
        item.setItemName(itemDto.getName());
        item.setCategory(itemDto.getCategory());
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());
        item.setStockQuantity(itemDto.getStockQuantity());
        item.setSellStatus(itemDto.getSellStatus());
        item.setSellerUid(uid);
        return itemRepository.save(item);
    }

    public Item registerByAdmin (ItemDto itemDto){
        Item item = new Item();
        item.setItemName(itemDto.getName());
        item.setCategory(itemDto.getCategory());
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
    public Page<Item> getItemList(SearchItemDto searchItemDto) {
        Pageable pageable = PageRequest.of(searchItemDto.getPage(), searchItemDto.getPageSize(), Sort.by("itemName").descending());
        return itemRepository.findAll(pageable);
    }
}
