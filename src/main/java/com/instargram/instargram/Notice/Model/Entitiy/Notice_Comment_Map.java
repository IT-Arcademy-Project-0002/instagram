package com.instargram.instargram.Notice.Model.Entitiy;


import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notice_Comment_Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Notice notice;

    @ManyToOne
    private Comment comment;

}