package com.instargram.instargram.Community.Comment.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Repository.BoardRepository;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Model.Repository.CommentRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Comment create(Member member, Board board, String content) {
        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setMember(member);
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        return this.commentRepository.save(comment);
    }
}
