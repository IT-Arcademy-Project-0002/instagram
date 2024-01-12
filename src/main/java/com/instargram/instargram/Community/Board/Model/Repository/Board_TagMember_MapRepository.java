package com.instargram.instargram.Community.Board.Model.Repository;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_TagMember_Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Board_TagMember_MapRepository extends JpaRepository<Board_TagMember_Map, Long> {

    List<Board_TagMember_Map> findByBoardId(Long boardId);

    List<Board_TagMember_Map> findByBoard(Board board);
}
