package com.instargram.instargram.Member.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

// 계정주와 친한 친구를 매핑해주는 테이블
// 스토리 혹은 게시글에서 공개범위를 친한 친구 공개로 했을 때 활용
public class Love_Member_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 계정주
    @ManyToOne
    @JoinColumn(name="owner_id")
    Member owner;

    // 친한 친구
    @ManyToOne
    @JoinColumn(name="love_member_id")
    Member loveMember;
}
