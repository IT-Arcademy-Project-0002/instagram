package com.instargram.instargram.Community.Comment.Model.DTO;

import com.instargram.instargram.Community.Board.Model.DTO.BoardDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Member.Model.DTO.MemberDTO;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private BoardDTO boardDTO;
    private MemberDTO memberDTO; // 추가된 부분

    public CommentDTO(Comment comment, Board board, Member member) {
        id = comment.getId();
        content = comment.getContent();
        createDate = comment.getCreateDate();
        updateDate = comment.getUpdateDate();
        boardDTO = new BoardDTO(board, member);
        memberDTO = new MemberDTO(member);
    }
}
