package com.tankmilu.spring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchItemDto {

    private int page;           
    private int recordSize;     
    private int pageSize;      
    private String keyword;      
    private String itemType;    
    public SearchItemDto() {
        this.page = 0;
        this.recordSize = 10;
        this.pageSize = 10;
        this.keyword = "";
    }

    public int getOffset() {
        return (page - 1) * recordSize;
    }

    
}
