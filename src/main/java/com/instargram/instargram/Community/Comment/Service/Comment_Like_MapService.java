package com.instargram.instargram.Community.Comment.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Like_Member_Map;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Community.Comment.Model.Repository.Comment_like_MapRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Comment_Like_MapService {
    private final Comment_like_MapRepository commentLikeMapRepository;

    public Comment_Like_Map create(Comment comment, Member member) {
        Comment_Like_Map commentLikeMap = new Comment_Like_Map();
        commentLikeMap.setComment(comment);
        commentLikeMap.setMember(member);
        this.commentLikeMapRepository.save(commentLikeMap);
        return commentLikeMap;
    }

    public void delete(Comment_Like_Map map) {
        this.commentLikeMapRepository.delete(map);
    }


    public Comment_Like_Map exists(Comment comment, Member member) {
        return this.commentLikeMapRepository.findByCommentAndMember(comment, member);
    }
}
