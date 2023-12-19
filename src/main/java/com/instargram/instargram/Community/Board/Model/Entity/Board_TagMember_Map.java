package com.instargram.instargram.Community.Board.Model.Entity;

import com.instargram.instargram.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

// 게시글에 언급된 회원 매핑 테이블
public class Board_TagMember_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글
    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    // 언급된 회원
    @ManyToOne
    @JoinColumn(name="tag_member_id")
    private Member tag_member;
}
