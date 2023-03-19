package com.tankmilu.spring.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="Orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private int itemId;
    
    private int itemCount;

    private int buyerId;

    private int originalPrice;

    @Column(nullable = true)
    private Integer discountNomal;

    @Column(nullable = true)
    private Integer discountDuplicated;

    @Column(nullable = true)
    private Integer discountCard;

    @Column(nullable = true)
    private Integer discountedPrice;

    // 배달
    @Column(nullable = false, length = 20)
    private String deliveryName;
    @Column(nullable = false, length = 11)
    private String deliveryPhone;
    @Column(nullable = true)
    private String deliveryAddress;

    @CreatedDate
    private LocalDateTime registerDate;
    
    private LocalDateTime modifiedDate;
}
