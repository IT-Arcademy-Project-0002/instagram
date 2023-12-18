package com.instargram.instargram.Community.Recomment;

import com.instargram.instargram.Community.Comment.Comment;
import com.instargram.instargram.Community.Comment.Comment_Like_Map;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity

// 게시글 속 댓글의 대댓글 테이블
public class Recomment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 생성날짜 및 수정날짜
    private LocalDateTime create_date;
    private LocalDateTime update_date;

    // 대댓글 내용
    private String content;

    // 대댓글의 부모 댓글
    @ManyToOne
    private Comment comment;

    // 해당 대댓글에 좋아요 한 회원 목록
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<ReComment_Like_Map> reCommentLikeMembers;
}
