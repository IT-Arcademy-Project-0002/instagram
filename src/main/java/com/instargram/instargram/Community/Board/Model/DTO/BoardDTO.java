package com.instargram.instargram.Community.Board.Model.DTO;

import com.instargram.instargram.Community.Board.Model.Entity.*;
import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Community.Location.Model.DTO.LocationDTO;
import com.instargram.instargram.Member.Model.DTO.MemberDTO;
import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

import javax.print.attribute.HashPrintJobAttributeSet;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
    private LocationDTO locationDTO;
    private List<Long> boardLikeMemberIds;
    private List<Long> boardSaveIds;
    private boolean pin;
    private List<String> boardHashTags;
    private List<String> boardTagMembers;
    @Transactional
    public void setValue(Board board) {
        this.id = board.getId();
        this.content = board.getContent();
        this.createDate = board.getCreateDate();
        this.updateDate = board.getUpdateDate();
        this.likeHide = board.isLikeHide();
        this.commentDisable = board.isCommentDisable();
        this.pin = board.isPin();
        if (board.getLocation() != null) {
            this.locationDTO = new LocationDTO(board.getLocation());
        } else {
            this.locationDTO = null;
        }
        if (board.getMember() != null) {
            this.memberDTO = new MemberDTO(board.getMember());
        }

        this.boardHashTags = board.getBoardHashTagMaps().stream().map(Board_HashTag_Map::getTag).map(HashTag::getName).collect(Collectors.toList());
        this.boardTagMembers = board.getBoardTagMemberMaps().stream().map(Board_TagMember_Map::getTagMember).map(Member::getUsername).collect(Collectors.toList());
        // BoardLikeMemberMap에서 필요한 데이터만 가져와서 저장
        this.boardLikeMemberIds = board.getBoardLikeMemberMaps().stream().map(BoardLikeMemberMap::getLikeMember).map(Member::getId).collect(Collectors.toList());

        // Board_Save_Map에서 필요한 데이터만 가져와서 저장
        this.boardSaveIds = board.getBoardSaveMaps().stream().map(Board_Save_Map::getBoard).map(Board::getId).collect(Collectors.toList());
    }

    public BoardDTO(Board board) {
        setValue(board);
    }
}
