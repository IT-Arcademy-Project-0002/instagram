package com.instargram.instargram.Community.Board;

import com.instargram.instargram.Community.HashTag.HashTag;
import com.instargram.instargram.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board_Like_Member_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Board board;
    @ManyToOne
    private Member member;
}
