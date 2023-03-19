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
import com.tankmilu.spring.enums.ItemStatus;
import com.tankmilu.spring.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item registerItem(ItemDto itemDto,int uid){
        Item item = Item.builder()
                    .itemName(itemDto.getName())
                    .category(itemDto.getCategory())
                    .description(itemDto.getDescription())
                    .price(itemDto.getPrice())
                    .stockQuantity(itemDto.getStockQuantity())
                    .sellStatus(itemDto.getSellStatus())
                    .sellerUid(uid).build();
        return itemRepository.save(item);
    }

    public Item registerByAdmin (ItemDto itemDto){
        Item item = Item.builder()
                    .itemName(itemDto.getName())
                    .category(itemDto.getCategory())
                    .description(itemDto.getDescription())
                    .price(itemDto.getPrice())
                    .stockQuantity(itemDto.getStockQuantity())
                    .sellStatus(itemDto.getSellStatus())
                    .sellerUid(itemDto.getSeller()).build();
        return itemRepository.save(item);
    }

    public Item deleteItem (int itemId){
        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            return new IllegalArgumentException("상품의 ID를 찾을 수 없습니다.");
            }
        );
        item.setSellStatus(ItemStatus.DELETED);
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
