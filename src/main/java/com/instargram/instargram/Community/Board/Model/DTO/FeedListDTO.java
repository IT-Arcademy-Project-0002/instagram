package com.instargram.instargram.Community.Board.Model.DTO;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Data.FileDTO;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Video.Video;

import java.util.List;
public record FeedListDTO(BoardDTO board, List<FileDTO> fileDTOS, List<Comment> comments) {
}
