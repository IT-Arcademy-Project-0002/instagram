package com.instargram.instargram.Search.Config.Enum;

import lombok.Getter;

@Getter
public enum SearchType {

    USER(1),
    LOCATION(2),
    HASHTAG(3);

    SearchType(Integer number) {
        this.number = number;
    }

    private final Integer number;
}
