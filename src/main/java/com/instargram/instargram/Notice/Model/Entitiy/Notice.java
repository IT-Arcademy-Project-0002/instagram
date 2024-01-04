package com.instargram.instargram.Notice.Model.Entitiy;

import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity

// 알림 테이블
// 최대 11주 보관
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 생성 날짜
    private LocalDateTime CreateDate;

    // 알림 타입
    //    게시글 좋아요 : 1
    //    게시글 댓글 : 2
    //    댓글 좋아요 : 3
    //    댓글 대댓글 : 4
    //    디엠 왔을 때 : 5
    //    디엠 좋아요 : 6
    //    스토리 좋아요 : 7
    //    (계정주가 비공개 계정일 시) 팔로우 요청 : 8
    //    (공개 계정) 팔로우 : 9
    private Integer type;

    // 알림 확인 여부(확인 : true, 확인안함 : false)
    private boolean checked;

    // 알림을 발생하게 한 회원
    @ManyToOne
    @JoinColumn(name = "request_member_id")
    private Member requestMember;

    // 알림을 받을 계정주
    @ManyToOne
    private Member member;
}
