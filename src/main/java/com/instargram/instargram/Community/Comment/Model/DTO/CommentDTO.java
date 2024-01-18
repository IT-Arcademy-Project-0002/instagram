package com.instargram.instargram.Community.Comment.Model.DTO;

import com.instargram.instargram.Community.Board.Model.DTO.BoardDTO;
import com.instargram.instargram.Community.Board.Model.Entity.BoardLikeMemberMap;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.DTO.RecommentDTO;
import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
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
    private Boolean pin;
    private BoardDTO boardDTO;
    private MemberDTO memberDTO;
    private List<Long> commentLikeMemberIds;
    private List<RecommentDTO> recommentDTOS;

    public CommentDTO(Comment comment) {
        id = comment.getId();
        content = comment.getContent();
        createDate = comment.getCreateDate();
        updateDate = comment.getUpdateDate();
        pin = comment.isPin();
        if (comment.getBoard() != null) {
            boardDTO = new BoardDTO(comment.getBoard());
        }
        if (comment.getMember() != null){
            memberDTO = new MemberDTO(comment.getMember());
        }

        // BoardLikeMemberMap에서 필요한 데이터만 가져와서 저장
        if (comment.getCommentLikeMembers() == null){
            this.commentLikeMemberIds = null;
        } else{
            this.commentLikeMemberIds = comment.getCommentLikeMembers().stream().map(Comment_Like_Map::getMember).map(Member::getId).collect(Collectors.toList());
        }

        // RecommentDTO로 매핑
        if(comment.getRecommentList() == null){
            this.recommentDTOS = null;
        }else{
            this.recommentDTOS = comment.getRecommentList().stream().map(RecommentDTO::new).collect(Collectors.toList());
        }


    }
}
