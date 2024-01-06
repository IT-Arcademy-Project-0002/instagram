package com.instargram.instargram.Community.Board.Model.Entity;

import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

// 게시글을 좋아요한 회원 매핑 테이블
public class Board_Like_Member_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    // 게시글을 좋아요 한 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="like_member_id")
    private Member likeMember;
}
