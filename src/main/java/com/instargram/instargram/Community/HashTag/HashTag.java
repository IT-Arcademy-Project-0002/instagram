package com.instargram.instargram.Community.HashTag;

import com.instargram.instargram.Community.Board.Board_HashTag_Map;
import com.instargram.instargram.Community.Board.Board_Save_Map;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Board_HashTag_Map> boardHashTagMaps;
}