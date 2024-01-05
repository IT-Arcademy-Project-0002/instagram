package com.instargram.instargram.Notice.Model.Entity;

import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    //    게시글 좋아요 : 1 (프론트 : 하트)
    //    게시글 댓글 : 2 (프론트 : 말풍선)
    //    댓글 좋아요 : 3 (프론트 : 하트)
    //    댓글 대댓글 : 4 (프론트 : 말풍선)
    //    디엠 왔을 때 : 5 (프론트 : 배지)
    //    디엠 좋아요 : 6 (프론트 : 배지)
    //    스토리 좋아요 : 7 (프론트 : 하트)
    //    (계정주가 비공개 계정일 시) 팔로우 요청 : 8 (프론트 : 인물)
    //    (공개 계정) 팔로우 : 9 (프론트 : 인물)
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

    @OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Notice_Board_Map> noticeBoardMap;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Notice_Comment_Map> noticeCommentMap;
}
