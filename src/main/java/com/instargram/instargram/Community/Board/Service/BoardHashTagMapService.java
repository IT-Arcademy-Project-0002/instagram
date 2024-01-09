package com.instargram.instargram.Community.Board.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_HashTag_Map;
import com.instargram.instargram.Community.Board.Model.Repository.Board_HashTag_MapRepository;
import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Community.HashTag.Model.Repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardHashTagMapService {
    private final Board_HashTag_MapRepository boardHashTagMapRepository;
    private final HashTagRepository hashTagRepository;
    public Board_HashTag_Map createBoardHashTag(Board board, HashTag hashTag) {
        Board_HashTag_Map map = new Board_HashTag_Map();
        map.setBoard(board);
        map.setTag(hashTag);
        return this.boardHashTagMapRepository.save(map);
    }

    public Board_HashTag_Map getHashTag(Board board) {
        return this.boardHashTagMapRepository.findByBoard(board);
    }
}
