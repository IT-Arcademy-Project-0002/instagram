package com.instargram.instargram.Community.Board.Model.Repository;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_HashTag_Map;
import com.instargram.instargram.Community.Board.Model.Entity.Board_TagMember_Map;
import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Board_HashTag_MapRepository extends JpaRepository<Board_HashTag_Map, Long> {
    List<Board_HashTag_Map> findByBoard(Board board);

    long countByTag_Id(Long tagId);

    List<Board_HashTag_Map> findByTag(HashTag tag);

}
