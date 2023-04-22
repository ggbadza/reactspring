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
import com.tankmilu.spring.repository.ItemElasticsearchRepository;
import com.tankmilu.spring.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemElasticsearchRepository itemElasticsearchRepository;

    public Item registerItem(ItemDto itemDto,int uid){
        Item item = Item.builder()
                    .itemName(itemDto.getName())
                    .category(itemDto.getCategory())
                    .description(itemDto.getDescription())
                    .price(itemDto.getPrice())
                    .stockQuantity(itemDto.getStockQuantity())
                    .sellStatus(itemDto.getSellStatus())
                    .sellerUid(uid).build();
        itemRepository.save(item); // JPA 저장
        itemElasticsearchRepository.save(item); // Elasticsearch 저장

        return item;
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

    public Item deleteItem (int itemId, int Uid){
        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            return new IllegalArgumentException("상품의 ID를 찾을 수 없습니다.");
            }
        );
        if(item.getSellerUid()==Uid) {
            item.setSellStatus(ItemStatus.DELETED);
            return itemRepository.save(item);
        }
        else throw new IllegalArgumentException("본인이 등록한 상품만 삭제 가능합니다.");
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
    
    @Transactional(readOnly = true) // elasticsearch로 검색
    public List<Item> getItemList(SearchItemDto searchItemDto) {
        Pageable pageable = PageRequest.of(searchItemDto.getPage(), searchItemDto.getPageSize());
        // Page<Item> ItemPage= itemRepository.findByItemNameContainingIgnoreCase(searchItemDto.getKeyword(),pageable);
        List<Item> ItemPage= itemElasticsearchRepository.findByItemName(searchItemDto.getKeyword(),pageable); 
        return ItemPage;
    }

    @Transactional(readOnly = true) 
    public List<Item> getItemListByUser(int Uid) {
        List<Item> ItemPage= itemRepository.findBySellerUid(Uid);
        return ItemPage;
    }


}
