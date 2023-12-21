package com.instargram.instargram.Community.Board.Model.DTO;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Data.Image.Image;
import lombok.Getter;

import java.util.List;

public record FeedListDTO(Board board, List<Image> images) {
}
