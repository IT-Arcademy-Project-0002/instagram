package com.instargram.instargram.Community.Board.Model.Entity;

import com.instargram.instargram.Community.Comment.Comment;
import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity

// 인스타그램 게시글
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글 내용
    @Column(columnDefinition = "Text")
    private String content;

    // 생성 날짜, 수정 날짜
    private LocalDateTime create_date;
    private LocalDateTime update_date;

    // 공개 범위(true : 전체 공개, false : 친한 친구 공개)
    private boolean scope;

    // 상단 고정 여부(true : 상단 고정, false : 일반 게시글)
    private boolean pin;

    // 보관 여부(true : 보관 게시글, false : 일반 게시글)
    private boolean keep;

    // 좋아요 숨기기 여부(true : 좋아요 숨기기, false : 일반 게시글)
    private boolean like_hide;

    // 댓글 비활성화 여부(true : 댓글 비활성화, false : 일반 게시글)
    private boolean comment_disable;

    // 게시글 작성자
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;


    // 지연 로딩 (매번 필요하지 않은 데이터)

    // 게시글에 달린 댓글 목록
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> commentList;

    // 게시글에 언급된 회원 목록
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Board_TagMember_Map> boardTagMembers;

    // 게시글에 입력된 해시태그 목록
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Board_HashTag_Map> boardHashTagMaps;

    // 게시글에 좋아요 한 회원 목록
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Board_Like_Member_Map> boardLikeMemberMaps;

    // 게시글에 작성되 이미지 혹은 비디오 목록
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Board_Data_Map> boardDataMaps;


    // 회원이 저장한 게시글 매핑 테이블 목록
    // 해당 리스트에서 직접 접근하지 말 것
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Board_Save_Map> boardSaveMaps;
}