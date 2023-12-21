package com.instargram.instargram.Community.Board.Model.Repository;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Data_Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Board_Data_MapRepository extends JpaRepository<Board_Data_Map, Long> {

    List<Board_Data_Map> findByBoard(Board board);
}
