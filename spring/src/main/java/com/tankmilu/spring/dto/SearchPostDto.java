package com.tankmilu.spring.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchPostDto {
    
    private int page;             
    private int recordSize;     
    private int pageSize;        
    private String keyword;    
    private String searchType;   

    public SearchPostDto() {
        this.page = 1;
        this.recordSize = 10;
        this.pageSize = 10;
    }

    public int getOffset() {
        return (page - 1) * recordSize;
    }

}
