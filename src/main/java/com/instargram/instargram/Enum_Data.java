package com.instargram.instargram;

import lombok.Getter;

@Getter
public enum Enum_Data {
    MESSAGE(1),
    IMAGE(2),
    VIDEO(3);

    Enum_Data(Integer number) {
        this.number = number;
    }

    private final Integer number;

}
