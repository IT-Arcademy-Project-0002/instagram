package com.instargram.instargram.Community.Board.Model.DTO;

import com.instargram.instargram.Community.Comment.Model.DTO.CommentDTO;
import com.instargram.instargram.Community.Recomment.Model.DTO.RecommentDTO;
import com.instargram.instargram.Data.Image.ImageDTO;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;

import java.util.List;
import java.util.stream.Collectors;

public record FeedDTO(BoardDTO board, List<ImageDTO> imageDTOS, List<CommentDTO> commentDTOS) {
}
