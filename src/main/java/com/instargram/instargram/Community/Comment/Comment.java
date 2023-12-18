package com.instargram.instargram.Community.Comment;

import com.instargram.instargram.Community.Board.Board;
import com.instargram.instargram.Community.Recomment.Recomment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime create_date;
    private LocalDateTime update_date;

    private String content;


    @ManyToOne
    private Board board;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Recomment> recommentList;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comment_Like_Map> commentLikeMapList;
}
