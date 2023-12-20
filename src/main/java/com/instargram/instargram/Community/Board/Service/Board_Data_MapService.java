package com.instargram.instargram.Community.Board.Service;

import com.instargram.instargram.Community.Board.Model.Repository.Board_Data_MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Board_Data_MapService {
    private final Board_Data_MapRepository boardDataMapRepository;

//    public Board_Data_Map create(Board board, Image image) {
//        Board_Data_Map boardDataMap = new Board_Data_Map();
//        boardDataMap.setData_id(image.getId());
//        boardDataMap.setData_type();
//        boardDataMap.setBoard(board);
//        return this.boardDataMapRepository.save(boardDataMap);
//    }
}
