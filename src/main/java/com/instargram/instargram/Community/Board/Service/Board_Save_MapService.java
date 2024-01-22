package com.instargram.instargram.Community.Board.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Save_Map;
import com.instargram.instargram.Community.Board.Model.Repository.Board_Save_MapRepository;
import com.instargram.instargram.Community.SaveGroup.Model.Entity.SaveGroup;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Board_Save_MapService {
    private final Board_Save_MapRepository boardSaveMapRepository;
    public Board_Save_Map create(Board board, Member member, SaveGroup saveGroup) {
        Board_Save_Map boardSaveMap = new Board_Save_Map();
        boardSaveMap.setBoard(board);
        boardSaveMap.setMember(member);
        boardSaveMap.setSaveGroup(saveGroup);
        return this.boardSaveMapRepository.save(boardSaveMap);
    }

    public Board_Save_Map exists(Board board, Member member, SaveGroup saveGroup) {
        return this.boardSaveMapRepository.findByBoardAndMemberAndSaveGroup(board, member, saveGroup);
    }


    public void delete(Board board) {
        List<Board_Save_Map> boardSaveMaps = boardSaveMapRepository.findByBoard(board);
        boardSaveMapRepository.deleteAll(boardSaveMaps);
    }

    public List<Board_Save_Map> getSaveGroup(Member member) {
        List<Board_Save_Map> boardSaveMaps = this.boardSaveMapRepository.findByMember(member);
        return boardSaveMaps;
    }
}
