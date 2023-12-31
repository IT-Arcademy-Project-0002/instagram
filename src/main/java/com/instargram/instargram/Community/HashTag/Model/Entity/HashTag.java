package com.instargram.instargram.Community.HashTag.Model.Entity;

import com.instargram.instargram.Community.Board.Model.Entity.Board_HashTag_Map;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity

// 해시태그
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 해시태그 내용
    private String name;
}