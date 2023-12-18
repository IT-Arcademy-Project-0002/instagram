package com.instargram.instargram.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

// 계정주와 차단 당한 계정을 매핑해주는 테이블
public class Hate_Member_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 계정주
    @ManyToOne
    @JoinColumn(name="owner_id")
    private Member owner;

    // 차단 당한 회원
    @ManyToOne
    @JoinColumn(name="hate_member_id")
    private Member hate_member;
}
