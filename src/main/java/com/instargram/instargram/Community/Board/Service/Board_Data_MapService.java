package com.instargram.instargram.Community.Board.Service;

import com.instargram.instargram.Community.Board.Model.DTO.FeedListDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Data_Map;
import com.instargram.instargram.Community.Board.Model.Repository.Board_Data_MapRepository;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Enum_Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class Board_Data_MapService {
    private final Board_Data_MapRepository boardDataMapRepository;

    private final ImageService imageService;

    public Board_Data_Map create(Board board, Image image, Integer data_type) {
        Board_Data_Map boardDataMap = new Board_Data_Map();
        boardDataMap.setDataId(image.getId());
        boardDataMap.setDataType(data_type);
        boardDataMap.setBoard(board);

        return this.boardDataMapRepository.save(boardDataMap);
    }

    public List<Board_Data_Map> getMapByBoard(Board board) {
        return this.boardDataMapRepository.findByBoard(board);
    }

    public List<FeedListDTO> getFeed(List<Board> boardList) {
        List<FeedListDTO> feedListDTOS = new ArrayList<>();
        for (Board board : boardList) {
            List<Board_Data_Map> maps = getMapByBoard(board);
            List<Image> images = new ArrayList<>();
            for(Board_Data_Map map : maps)
            {
                if (Objects.equals(map.getDataType(), Enum_Data.IMAGE.getNumber())) {
                    Image image = imageService.getImgaeByID(map.getDataId());
                    images.add(image);
                }
            }

            feedListDTOS.add(new FeedListDTO(board, images));
        }
        return feedListDTOS;
    }
}
