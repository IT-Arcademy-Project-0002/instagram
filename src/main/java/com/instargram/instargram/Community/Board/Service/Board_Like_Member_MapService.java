package com.instargram.instargram.Community.Board.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Like_Member_Map;
import com.instargram.instargram.Community.Board.Model.Repository.Board_Like_Member_MapRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Board_Like_Member_MapService {
    private final Board_Like_Member_MapRepository boardLikeMemberMapRepository;

    public void create(Board board, Member username) {
        Board_Like_Member_Map boardLikeMemberMap = new Board_Like_Member_Map();
        boardLikeMemberMap.setBoard(board);
        boardLikeMemberMap.setLike_member(username);
        this.boardLikeMemberMapRepository.save(boardLikeMemberMap);
    }

    public boolean exists(Board board, Member member) {
        return this.boardLikeMemberMapRepository.existsByBoardAndMember(board, member);
    }
}
