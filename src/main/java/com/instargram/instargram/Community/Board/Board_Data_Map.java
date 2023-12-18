package com.instargram.instargram.Community.Board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board_Data_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer data_type;
    private Long data_id;

    @ManyToOne
    private Board board;
}
