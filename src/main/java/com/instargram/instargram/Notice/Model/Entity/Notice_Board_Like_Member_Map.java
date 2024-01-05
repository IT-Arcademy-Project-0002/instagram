package com.instargram.instargram.Notice.Model.Entity;

import com.instargram.instargram.Community.Board.Model.Entity.Board_Like_Member_Map;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notice_Board_Like_Member_Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Notice notice;

    @ManyToOne
    private Board_Like_Member_Map boardLikeMember;

}
