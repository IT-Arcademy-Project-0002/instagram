package com.instargram.instargram.Community.Board.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

// 게시글에 작성된 이미지 혹은 비디오 데이터 매핑 테이블
public class Board_Data_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 데이터 타입 ( 1 : 텍스트, 2 : 이미지, 3 : 비디오)
    private Integer data_type;

    // 데이터 아이디 : 데이터 타입에 따라 따로 처리 필요
    private Long data_id;

    // 게시글
    @ManyToOne
    private Board board;
}
