package com.instargram.instargram;

import lombok.Getter;

@Getter
public enum Enum_Data {
    MESSAGE(1),
    IMAGE(2),
    VIDEO(3),

    // 알림 ENUM

    BOARD_LIKE(1), // 게시글 좋아요 : 1
    BOARD_COMMENT(2), // 게시글 댓글 : 2
    COMMENT_LIKE(3), // 댓글 좋아요 : 3
    COMMENT_RECOMMENT(4), // 댓글 대댓글 : 4
    RECOMMENT_LIKE(5), // 댓글 대댓글 좋아요 : 5
    BOARD_TAGMEMBER(6), // 게시글 멤버태그 : 6
    FOLLOW_REQUEST(7), // 팔로우 요청 : 7
    FOLLOW_STATE(8), // 팔로우 상태 : 8
    DM(9), // 디엠 왔을 때 : 9
    DM_LIKE(10), // 디엠 좋아요 : 10
    STORY_LIKE(11); // 스토리 좋아요 : 11

    Enum_Data(Integer number) {
        this.number = number;
    }

    private final Integer number;

}
