package com.tankmilu.spring.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ADMIN(Authority.ADMIN),
    MANAGER(Authority.MANAGER),
    USER(Authority.USER),
    SELLER(Authority.SELLER);

    private final String authority;

    public static class Authority{
        static String ADMIN = "ROLE_ADMIN";
        static String MANAGER = "ROLE_MANAGER";
        static String USER = "ROLE_USER";
        static String SELLER = "ROLE_SELLER";
    }
}