package com.instargram.instargram.Community.Board.Model.Repository;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_HashTag_Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Board_HashTag_MapRepository extends JpaRepository<Board_HashTag_Map, Long> {
    Board_HashTag_Map findByBoard(Board board);
}
