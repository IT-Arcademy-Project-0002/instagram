package com.instargram.instargram.Explorer.Model.DTO;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Data.Image.Image;
import lombok.Getter;

import java.util.List;

public record ExploreDTO(Board board, List<Image> images) {
}
