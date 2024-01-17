package com.instargram.instargram;

import lombok.Getter;

@Getter
public enum Enum_Data {
    MESSAGE(1),
    IMAGE(2),
    VIDEO(3),
    COMMENT_MESSAGE(4),

    // 알림 이넘
    //    게시글 좋아요 : 1
    BOARD_LIKE(1),
    //    게시글 댓글 : 2
    BOARD_COMMENT(2),
    //    댓글 좋아요 : 3
    COMMENT_LIKE(3),
    //    댓글 대댓글 : 4
    COMMENT_RECOMMENT(4),
    //    디엠 왔을 때 : 5
    DM(5),
    //    디엠 좋아요 : 6
    DM_LIKE(6),
    //    스토리 좋아요 : 7
    STORY_LIKE(7),
    //    팔로우 요청 : 8
    FOLLOW_REQUEST(8),
    //     게시글 멤버태그 : 9
    BOARD_TAGMEMBER(9),
    //     댓글 대댓글 좋아요 : 10
    RECOMMENT_LIKE(10);

    Enum_Data(Integer number) {
        this.number = number;
    }

    private final Integer number;

}
