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
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageDTO;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Member.Model.DTO.MemberDTO;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class Board_Data_MapService {
    private final Board_Data_MapRepository boardDataMapRepository;
    private final BoardService boardService;
    private final ImageService imageService;
    private final CommentRepository commentRepository;

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

    public List<Comment> getCommentsByBoard(Board board) {
        return commentRepository.findByBoard(board);
    }

    public List<FeedListDTO> getFeed(List<Board> boardList) {
        List<FeedListDTO> feedListDTOS = new ArrayList<>();
        for (Board board : boardList) {
            List<Board_Data_Map> maps = getMapByBoard(board);
            List<Comment> comments = getCommentsByBoard(board);
            List<Image> images = new ArrayList<>();
            for(Board_Data_Map map : maps)
            {
                if (Objects.equals(map.getDataType(), Enum_Data.IMAGE.getNumber())) {
                    Image image = imageService.getImageByID(map.getDataId());
                    images.add(image);
                }
            }

            feedListDTOS.add(new FeedListDTO(board, images, comments));
        }
        return feedListDTOS;
    }

    public List<FeedListDTO> getFeedWithComments(Board board) {
        List<FeedListDTO> feedListDTOS = new ArrayList<>();
        List<Board_Data_Map> maps = getMapByBoard(board);
        List<Comment> comments = getCommentsByBoard(board);
        List<Image> images = new ArrayList<>();
        for(Board_Data_Map map : maps)
        {
            if (Objects.equals(map.getDataType(), Enum_Data.IMAGE.getNumber())) {
                Image image = imageService.getImageByID(map.getDataId());
                images.add(image);
            }
        }
        feedListDTOS.add(new FeedListDTO(board, images, comments));
        return feedListDTOS;
    }

//    public FeedDTO getFeedWithComments(Board board) {
//        List<Board_Data_Map> maps = getMapByBoard(board);
//        List<Comment> comments = getCommentsByBoard(board);
//        List<ImageDTO> images = new ArrayList<>();
//
//        for (Board_Data_Map map : maps) {
//            if (Objects.equals(map.getDataType(), Enum_Data.IMAGE.getNumber())) {
//                Image image = imageService.getImageByID(map.getDataId());
//                ImageDTO imageDTO = convertToImageDTO(image);
//                images.add(imageDTO);
//            }
//        }
//        return new FeedDTO(convertToBoardDTO(board), images, convertToCommentDTOs(comments));
//    }
    private BoardDTO convertToBoardDTO(Board board) {
        return new BoardDTO(board, board.getMember());
    }
    private List<CommentDTO> convertToCommentDTOs(List<Comment> comments) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments) {

            CommentDTO commentDTO = new CommentDTO(comment, comment.getBoard(), comment.getMember());
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }
    private ImageDTO convertToImageDTO(Image image) {
        return new ImageDTO(image);
    }
}
