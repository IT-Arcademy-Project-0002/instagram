package com.instargram.instargram.Notice.Model.Entity;

import com.instargram.instargram.Community.Board.Model.Entity.BoardLikeMemberMap;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@Entity
public class Notice_Board_Like_Member_Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @ManyToOne
    @JoinColumn(name = "board_like_member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BoardLikeMemberMap boardLikeMember;
}
