package com.instargram.instargram.Community.Board.Model.Repository;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.BoardLikeMemberMap;
import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardLikeMemberMapRepository extends JpaRepository<BoardLikeMemberMap, Long> {
    BoardLikeMemberMap findByBoardAndLikeMember(Board board, Member member);

    List<BoardLikeMemberMap> findByBoard(Board board);
}
