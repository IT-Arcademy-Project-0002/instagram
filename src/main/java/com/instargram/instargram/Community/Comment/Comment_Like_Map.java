package com.instargram.instargram.Community.Comment;

import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

// 댓글을 좋아요한 회원 매핑 테이블
public class Comment_Like_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글
    @ManyToOne
    private Comment comment;

    // 댓글을 좋아요 한 회원
    @ManyToOne
    private Member member;
}
