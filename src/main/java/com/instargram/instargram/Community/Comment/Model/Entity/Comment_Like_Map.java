package com.instargram.instargram.Community.Comment.Model.Entity;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Notice.Model.Entity.Notice_Comment_Like_Map;
import com.instargram.instargram.Notice.Model.Entity.Notice_Comment_Map;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

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
    @JoinColumn(name = "comment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment comment;

    // 댓글을 좋아요 한 회원
    @ManyToOne
    @JoinColumn(name="member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @OneToMany(mappedBy = "commentLike", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Notice_Comment_Like_Map> noticeCommentLikeMap;
}
