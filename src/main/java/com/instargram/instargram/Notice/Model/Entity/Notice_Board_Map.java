package com.instargram.instargram.Notice.Model.Entity;


import com.instargram.instargram.Community.Board.Model.Entity.Board;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notice_Board_Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="notice_id")
    private Notice notice;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;
}
