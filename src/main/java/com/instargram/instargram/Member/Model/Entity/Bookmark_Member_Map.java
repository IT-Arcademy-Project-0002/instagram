package com.instargram.instargram.Member.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

// 즐겨찾기 : 1. 즐겨찾기한 계정의 게시글들은 즐겨찾기 목록에서 확인 가능,
//          2. 타임라인에 즐겨찾기한 계정이 올린 최근 게시글을 우선적으로 1회 먼저 보여주는 기능
// 즐겨찾기 한 회원과 즐겨찾기 당한 회원 매핑 테이블
public class Bookmark_Member_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 계정주
    @ManyToOne
    @JoinColumn(name="owner_id")
    Member owner;

    // 즐겨찾기 한 계정
    @ManyToOne
    @JoinColumn(name="bookmark_member_id")
    Member bookmarkMember;
}
