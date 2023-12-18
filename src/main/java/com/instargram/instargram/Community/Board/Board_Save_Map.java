package com.instargram.instargram.Community.Board;

import com.instargram.instargram.Community.SaveGroup.SaveGroup;
import com.instargram.instargram.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

// 회원이 저장한 게시글 목록 매핑 테이블
public class Board_Save_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글
    @ManyToOne
    private Board board;

    // 회원
    @ManyToOne
    private Member member;

    // 저장 그룹
    @ManyToOne
    private SaveGroup saveGroup;
}
