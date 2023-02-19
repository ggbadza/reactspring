package com.tankmilu.spring.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemStatus {
    SELL(Authority.SELL),
    SOLD_OUT(Authority.SOLD_OUT);

    private final String authority;

    public static class Authority{
        static String SELL = "SELL";
        static String SOLD_OUT = "SOLD_OUT";
    }
}

