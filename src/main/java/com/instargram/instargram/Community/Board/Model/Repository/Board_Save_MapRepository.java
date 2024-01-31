package com.instargram.instargram.Community.Board.Model.Repository;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Save_Map;
import com.instargram.instargram.Community.SaveGroup.Model.Entity.SaveGroup;
import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Board_Save_MapRepository extends JpaRepository<Board_Save_Map, Long> {
    Board_Save_Map findByBoardAndMemberAndSaveGroup(Board board, Member member, SaveGroup saveGroup);
    List<Board_Save_Map> findByBoard(Board board);

    List<Board_Save_Map> findByMember(Member member);

    List<Board_Save_Map> findTop1BySaveGroupOrderByIdDesc(SaveGroup saveGroup);

    List<Board_Save_Map> findTop4ByMemberAndSaveGroupIsNullOrderByBoardCreateDateDesc(Member member);
}
