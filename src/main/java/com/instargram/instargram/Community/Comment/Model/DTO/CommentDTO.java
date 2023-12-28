package com.instargram.instargram.Community.Comment.Model.DTO;

import com.instargram.instargram.Community.Board.Model.DTO.BoardDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Like_Member_Map;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Member.Model.DTO.MemberDTO;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private BoardDTO boardDTO;
    private MemberDTO memberDTO;
    private List<Long> commentLikeMemberIds; // Board_Like_Member_Map의 일부 데이터

    public CommentDTO(Comment comment) {
        id = comment.getId();
        content = comment.getContent();
        createDate = comment.getCreateDate();
        updateDate = comment.getUpdateDate();
        if (comment.getBoard() != null) {
            boardDTO = new BoardDTO(comment.getBoard());
        }
        if (comment.getMember() != null){
            memberDTO = new MemberDTO(comment.getMember());
        }
        // Board_Like_Member_Map에서 필요한 데이터만 가져와서 저장
        commentLikeMemberIds = comment.getCommentLikeMembers().stream()
                                        .map(Comment_Like_Map::getId)
                                        .collect(Collectors.toList());
    }
}
