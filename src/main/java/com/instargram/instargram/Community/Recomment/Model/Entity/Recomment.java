package com.instargram.instargram.Community.Recomment.Model.Entity;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private LocalDateTime CreateDate;
    private LocalDateTime updateDate;

    // 대댓글 내용
    private String content;

    // 대댓글의 부모 댓글
    @ManyToOne
    @JoinColumn(name = "comment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name="member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    // 해당 대댓글에 좋아요 한 회원 목록
    @OneToMany(mappedBy = "recomment", cascade = CascadeType.REMOVE)
    private List<ReComment_Like_Map> reCommentLikeMembers;
}
