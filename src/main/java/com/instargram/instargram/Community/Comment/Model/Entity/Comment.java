package com.instargram.instargram.Community.Comment.Model.Entity;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Notice.Model.Entitiy.Notice;
import com.instargram.instargram.Notice.Model.Entitiy.Notice_Board_Map;
import com.instargram.instargram.Notice.Model.Entitiy.Notice_Comment_Map;
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

// 게시글 속 댓글
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 생성날짜 및 수정 날짜
    private LocalDateTime CreateDate;
    private LocalDateTime updateDate;

    // 댓글 내용
    private String content;

    // 댓글의 부모 게시글
    @ManyToOne
    @JoinColumn(name="board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;
    
    // 댓글의 작성자
    @ManyToOne
    @JoinColumn(name="member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    // 댓글의 대댓글 목록
    @OneToMany(mappedBy = "comment",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Recomment> recommentList;

    // 댓글의 좋아요 한 사람 목록
    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Comment_Like_Map> commentLikeMembers;
}
