package com.instargram.instargram.Community.Board.Model.DTO;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Data.Image.Image;
import lombok.Getter;

public record FeedListDTO(Board board, Image image) {
}
