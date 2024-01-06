package com.instargram.instargram.Community.Board.Model.DTO;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Like_Member_Map;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Save_Map;
import com.instargram.instargram.Community.Comment.Model.DTO.CommentDTO;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.Member.Model.DTO.MemberDTO;
import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.transaction.Transactional;
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
    private Boolean commentDisable;
    private String location;
    private List<Long> boardLikeMemberIds;
    private List<Long> boardSaveIds;
    private boolean pin;

    @Transactional
    public void setValue(Board board)
    {
        this.id = board.getId();
        this.content = board.getContent();
        this.createDate = board.getCreateDate();
        this.updateDate = board.getUpdateDate();
        this.likeHide = board.isLikeHide();
        this.commentDisable = board.isCommentDisable();
        this.pin = board.isPin();
        if (board.getLocation() != null) {
            this.location = board.getLocation().getPlaceName();
        } else {
            this.location = null;
        }
        if (board.getMember() != null) {
            this.memberDTO = new MemberDTO(board.getMember());
        }
        // Board_Like_Member_Map에서 필요한 데이터만 가져와서 저장
        var a = board.getBoardLikeMemberMaps();
        this.boardLikeMemberIds = a.stream().map(Board_Like_Member_Map::getLikeMember).map(Member::getId).collect(Collectors.toList());

        // Board_Save_Map에서 필요한 데이터만 가져와서 저장
        this.boardSaveIds = board.getBoardSaveMaps().stream().map(Board_Save_Map::getBoard).map(Board::getId).collect(Collectors.toList());
    }

    public BoardDTO(Board board) {
       setValue(board);
    }
}
