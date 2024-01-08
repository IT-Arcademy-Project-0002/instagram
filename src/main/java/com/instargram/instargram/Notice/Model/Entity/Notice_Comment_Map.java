package com.instargram.instargram.Notice.Model.Entity;


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
    @JoinColumn(name="notice_id")
    private Notice notice;

    @ManyToOne
    @JoinColumn(name="comment_id")
    private Comment comment;

}
