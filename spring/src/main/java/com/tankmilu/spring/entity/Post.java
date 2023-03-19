package com.tankmilu.spring.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tankmilu.spring.enums.PostState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column(nullable = false, length = 50)
    private String postSubject;  

    @Lob
    @Column(nullable = false)
    private String postContents;  

    private Integer uId;

    @Column(nullable = true, length = 20)
    private Integer category;


    @CreatedDate
    private LocalDateTime registerDate;
    
    private LocalDateTime modifiedDate;
    
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PostState state;
}
