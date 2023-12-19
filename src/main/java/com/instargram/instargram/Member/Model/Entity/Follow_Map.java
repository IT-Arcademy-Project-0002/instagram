package com.instargram.instargram.Member.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

// 회원간 팔로워 팔로잉 관계 매핑 테이블
public class Follow_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 팔로우 당한 회원
    @ManyToOne
    @JoinColumn(name="following_member_id")
    Member following_member;

    // 팔로우 한 회원
    @ManyToOne
    @JoinColumn(name="follower_member_id")
    Member follower_member;
}
