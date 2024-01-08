package com.instargram.instargram.Community.Board.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.BoardLikeMemberMap;
import com.instargram.instargram.Community.Board.Model.Repository.BoardLikeMemberMapRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardLikeMemberMapService {
    private final BoardLikeMemberMapRepository boardLikeMemberMapRepository;

    public void create(Board board, Member username) {
        BoardLikeMemberMap boardLikeMemberMap = new BoardLikeMemberMap();
        boardLikeMemberMap.setBoard(board);
        boardLikeMemberMap.setLikeMember(username);
        this.boardLikeMemberMapRepository.save(boardLikeMemberMap);
    }

    public BoardLikeMemberMap exists(Board board, Member member) {
        return this.boardLikeMemberMapRepository.findByBoardAndLikeMember(board, member);
    }

    public void delete(BoardLikeMemberMap map) {
        this.boardLikeMemberMapRepository.delete(map);
    }
}
