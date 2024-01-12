package com.instargram.instargram.Notice.Config.Enum;

import lombok.Getter;

@Getter
public enum NoticeType {


    WEEK(1),
    MONTH(2),
    OLD(3);

    NoticeType(Integer number) {
        this.number = number;
    }

    private final Integer number;

}
