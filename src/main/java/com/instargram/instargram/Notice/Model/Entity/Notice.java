package com.instargram.instargram.Notice.Model.Entity;

import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Notice {

    // 알림 테이블
    // 최대 11주 보관

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 생성 날짜
    private LocalDateTime createDate;

    // 알림 타입
    //    게시글 좋아요 : 1
    //    게시글 댓글 : 2
    //    댓글 좋아요 : 3
    //    댓글 대댓글 : 4
    //    댓글 대댓글 좋아요 : 5
    //    게시믈 멤버태그 : 6
    //    스토리 좋아요 : 7
    //    (계정주가 비공개 계정일 시) 팔로우 요청 : 8
    //    (공개 계정) 팔로우 : 9
    //    디엠 왔을 때 : 10
    //    디엠 좋아요 : 11
    private Integer type;

    // 알림 확인 여부(확인 : true, 확인안함 : false)
    private boolean checked;

    // 알림을 발생하게 한 회원
    @ManyToOne
    @JoinColumn(name = "request_member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member requestMember;

    // 알림을 받을 계정주
    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Notice_Board_Like_Member_Map> noticeBoardLikeMap;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Notice_Board_Map> noticeBoardMap;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Notice_Board_TagMember_Map> noticeBoardTagMemberMap;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Notice_Comment_Like_Map> noticeCommentLikeMap;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Notice_Comment_Map> noticeCommentMap;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Notice_Recomment_Map> noticeRecommentMap;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Notice_Recomment_Like_Map> noticeRecommentLikeMap;
}
