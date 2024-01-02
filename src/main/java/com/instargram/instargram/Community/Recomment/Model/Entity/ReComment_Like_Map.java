package com.instargram.instargram.Community.Recomment.Model.Entity;

import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

// 대댓글에 좋아요 한 사람 매핑 테이블
public class ReComment_Like_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 대댓글
    @ManyToOne
    private Recomment recomment;

    // 회원
    @ManyToOne
    private Member member;
}
