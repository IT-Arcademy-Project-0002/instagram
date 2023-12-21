package com.instargram.instargram.Community.Board.Model.Repository;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Like_Member_Map;
import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Board_Like_Member_MapRepository extends JpaRepository<Board_Like_Member_Map, Long> {
    boolean existsByBoardAndMember(Board board, Member member);
}
