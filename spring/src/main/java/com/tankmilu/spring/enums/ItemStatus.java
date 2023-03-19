package com.tankmilu.spring.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemStatus {
    SELL,
    SOLD_OUT,
    MODIFYNG,
    DELETED;
}

