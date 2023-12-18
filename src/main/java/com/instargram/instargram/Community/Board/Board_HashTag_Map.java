package com.instargram.instargram.Community.Board;

import com.instargram.instargram.Community.HashTag.HashTag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

// 게시글에 입력된 해시태그 매핑 테이블
public class Board_HashTag_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글
    @ManyToOne
    private Board board;

    // 해시 태그
    @ManyToOne
    private HashTag tag;
}
