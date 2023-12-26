package com.instargram.instargram.Community.Comment.Model.Repository;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Comment_like_MapRepository extends JpaRepository<Comment_Like_Map, Long> {
    Comment_Like_Map findByCommentAndMember(Comment comment, Member member);
}
