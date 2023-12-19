package com.instargram.instargram.Member.Config.Enum;

import lombok.Getter;


@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }


    private final String value;
}