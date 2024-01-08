package com.instargram.instargram.Notice.Model.Entity;

import com.instargram.instargram.Community.Board.Model.Entity.Board_TagMember_Map;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notice_Board_TagMember_Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Notice notice;

    @ManyToOne
    private Board_TagMember_Map boardTagMember;

}
