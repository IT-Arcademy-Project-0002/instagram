package com.instargram.instargram.Explorer.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Data_Map;
import com.instargram.instargram.Community.Board.Service.Board_Data_MapService;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Explorer.Model.DTO.ExploreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ExploreService {

    private final Board_Data_MapService boardDataMapService;
    private final ImageService imageService;

    public List<ExploreDTO> initExplore(List<Board> boardList) {
        List<ExploreDTO> exploreDTOS = new ArrayList<>();
        for (Board board : boardList) {
            List<Board_Data_Map> maps = this.boardDataMapService.getMapByBoard(board);
            List<Image> images = new ArrayList<>();
            for(Board_Data_Map map : maps)
            {
                if (Objects.equals(map.getDataType(), Enum_Data.IMAGE.getNumber())) {
                    Image image = this.imageService.getImageByID(map.getDataId());
                    images.add(image);
                }
            }
            exploreDTOS.add(new ExploreDTO(board, images));
        }
        return exploreDTOS;
    }
    public String formatHashTagCount(long count) {
        if (count < 10000) {
            // 1만 미만은 그냥 숫자만 반환
            return String.valueOf(count);
        } else {
            // 1만 이상은 "만"을 붙이고, 천의 단위로 표현
            double countInThousands = count / 10000.0;
            return String.format("%.1f만", countInThousands);
        }
    }

}
