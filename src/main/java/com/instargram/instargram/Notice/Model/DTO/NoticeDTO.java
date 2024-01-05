package com.instargram.instargram.Notice.Model.DTO;

import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeDTO {

    // 공통 객체
    private Long id;
    private Integer type;
    private Member requestMember;
    private String createDate;

    private boolean follow;
    private boolean follower;


    // 게시글 좋아요 : 1

    // 게시글 댓글 : 2
    private String commentContent; // 댓글 내용
    private String boardMainImage; // 게시글 메인 이미지

    // 댓글 좋아요 : 3

    // 댓글 대댓글 : 4

    // 디엠 왔을 때 : 5

    // 디엠 좋아요 : 6

    // 스토리 좋아요 : 7

    // 팔로우 요청 : 8

    // 게시글 멤버태그 : 9

}
