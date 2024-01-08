package com.instargram.instargram.Community.Board.Model.Entity;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Notice.Model.Entity.Notice_Board_Like_Member_Map;
import com.instargram.instargram.Notice.Model.Entity.Notice_Board_TagMember_Map;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@Entity

// 게시글에 언급된 회원 매핑 테이블
public class Board_TagMember_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글
    @ManyToOne
    @JoinColumn(name="board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    // 언급된 회원
    @ManyToOne
    @JoinColumn(name="tag_member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member tagMember;

    @OneToMany(mappedBy = "boardTagMember", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Notice_Board_TagMember_Map> noticeBoardTagMemberMap;
}
