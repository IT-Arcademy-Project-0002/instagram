package com.instargram.instargram.Community.Board.Model.DTO;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Data.Image.Image;

import java.util.List;

public record FeedListDTO(Board board, List<Image> images, List<Comment> comments) {
}
