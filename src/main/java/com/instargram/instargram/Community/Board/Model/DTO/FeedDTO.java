package com.instargram.instargram.Community.Board.Model.DTO;

import com.instargram.instargram.Community.Comment.Model.DTO.CommentDTO;
import com.instargram.instargram.Data.Image.ImageDTO;
import java.util.List;

public record FeedDTO(BoardDTO boardDTO, List<ImageDTO> imageDTOS, List<CommentDTO> commentDTOS) {
}
