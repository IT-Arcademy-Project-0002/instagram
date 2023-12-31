package com.instargram.instargram.Community.Board.Service;

import com.instargram.instargram.Community.Board.Model.DTO.BoardDTO;
import com.instargram.instargram.Community.Board.Model.DTO.FeedDTO;
import com.instargram.instargram.Community.Board.Model.DTO.FeedListDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Data_Map;
import com.instargram.instargram.Community.Board.Model.Repository.Board_Data_MapRepository;
import com.instargram.instargram.Community.Comment.Model.DTO.CommentDTO;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Model.Repository.CommentRepository;
import com.instargram.instargram.Data.FileDTO;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageDTO;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Data.Video.Video;
import com.instargram.instargram.Data.Video.VideoService;
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
    private final VideoService videoService;
    private final CommentRepository commentRepository;

    public Board_Data_Map create(Board board, Image image, Integer data_type) {
        Board_Data_Map boardDataMap = new Board_Data_Map();
        boardDataMap.setDataId(image.getId());
        boardDataMap.setDataType(data_type);
        boardDataMap.setBoard(board);

        return this.boardDataMapRepository.save(boardDataMap);
    }

    public Board_Data_Map create(Board board, Video video, Integer data_type) {
        Board_Data_Map boardDataMap = new Board_Data_Map();
        boardDataMap.setDataId(video.getId());
        boardDataMap.setDataType(data_type);
        boardDataMap.setBoard(board);

        return this.boardDataMapRepository.save(boardDataMap);
    }

    public List<Board_Data_Map> getMapByBoard(Board board) {
        return this.boardDataMapRepository.findByBoard(board);
    }

    public List<Comment> getCommentsByBoard(Board board) {
        return commentRepository.findByBoard(board);
    }

    public List<FeedListDTO> getFeed(List<Board> boardList) {
        List<FeedListDTO> feedListDTOS = new ArrayList<>();
        for (Board board : boardList) {
            List<Board_Data_Map> maps = getMapByBoard(board);
            List<Comment> comments = getCommentsByBoard(board);
            List<FileDTO> fileList = new ArrayList<>();
            for (Board_Data_Map map : maps) {
                if (Objects.equals(map.getDataType(), Enum_Data.IMAGE.getNumber())) {
                    Image image = imageService.getImageByID(map.getDataId());
                    fileList.add(new FileDTO(image, null));
                } else if (Objects.equals(map.getDataType(), Enum_Data.VIDEO.getNumber())) {
                    Video video = videoService.getVideoByID(map.getDataId());
                    fileList.add(new FileDTO(null, video));
                }
            }
            feedListDTOS.add(new FeedListDTO(new BoardDTO(board), fileList, comments));
        }
        return feedListDTOS;
    }

    public FeedDTO getFeedWithComments(Board board) {
        List<Board_Data_Map> maps = getMapByBoard(board);
        List<Comment> comments = getCommentsByBoard(board);
        List<FileDTO> selectfileList = new ArrayList<>();
        for (Board_Data_Map map : maps) {
            if (Objects.equals(map.getDataType(), Enum_Data.IMAGE.getNumber())) {
                Image image = imageService.getImageByID(map.getDataId());
                selectfileList.add(new FileDTO(image, null));
            } else if (Objects.equals(map.getDataType(), Enum_Data.VIDEO.getNumber())) {
                Video video = videoService.getVideoByID(map.getDataId());
                selectfileList.add(new FileDTO(null, video));
            }
        }

        return new FeedDTO(new BoardDTO(board), selectfileList, convertToCommentDTOs(comments));
    }
    private List<CommentDTO> convertToCommentDTOs(List<Comment> comments) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments) {

            CommentDTO commentDTO = new CommentDTO(comment);
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }
}
