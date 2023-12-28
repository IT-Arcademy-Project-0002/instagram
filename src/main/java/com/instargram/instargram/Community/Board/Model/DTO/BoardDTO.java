package com.instargram.instargram.Community.Board.Model.DTO;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Like_Member_Map;
import com.instargram.instargram.Member.Model.DTO.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BoardDTO {
    private Long id;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private MemberDTO memberDTO;
    private Boolean likeHide;
    private List<Long> boardLikeMemberIds; // Board_Like_Member_Map의 일부 데이터

    public BoardDTO(Board board){
        id = board.getId();
        content = board.getContent();
        createDate = board.getCreateDate();
        updateDate = board.getUpdateDate();
        likeHide = board.isLikeHide();
        if (board.getMember() != null) {
            memberDTO = new MemberDTO(board.getMember());
        }
        // Board_Like_Member_Map에서 필요한 데이터만 가져와서 저장
        boardLikeMemberIds = board.getBoardLikeMemberMaps().stream()
                                    .map(Board_Like_Member_Map::getId)
                                    .collect(Collectors.toList());
    }
}
