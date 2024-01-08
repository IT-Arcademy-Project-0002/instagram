package com.instargram.instargram.Notice.Service;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Community.Comment.Model.Repository.CommentRepository;
import com.instargram.instargram.Notice.Model.Entity.Notice;
import com.instargram.instargram.Notice.Model.Entity.Notice_Comment_Like_Map;
import com.instargram.instargram.Notice.Model.Entity.Notice_Comment_Map;
import com.instargram.instargram.Notice.Model.Repository.Notice_Comment_MapRepository;
import com.instargram.instargram.Notice.Model.Repository.Notice_comment_Like_MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeCommentMapService {

    private final Notice_Comment_MapRepository noticeCommentMapRepository;
    private final CommentRepository commentRepository;
    private final Notice_comment_Like_MapRepository noticeCommentLikeMapRepository;

    public void createNoticeComment(Comment comment, Notice notice) {

        Notice_Comment_Map noticeCommentMap = new Notice_Comment_Map();
        noticeCommentMap.setComment(comment);
        noticeCommentMap.setNotice(notice);
        this.noticeCommentMapRepository.save(noticeCommentMap);

    }

    public void createNoticeCommentLike(Comment_Like_Map commentLikeMap, Notice notice) {

        Notice_Comment_Like_Map noticeCommentLikeMap = new Notice_Comment_Like_Map();
        noticeCommentLikeMap.setCommentLike(commentLikeMap);
        noticeCommentLikeMap.setNotice(notice);
        this.noticeCommentLikeMapRepository.save(noticeCommentLikeMap);;

    }

    public Comment getNoticeComment(Long noticeId) {
        Notice_Comment_Map commentMap = this.noticeCommentMapRepository.findByNoticeId(noticeId);

        if (commentMap != null) {
            // commentMap이 null이 아닌 경우에만 comment를 반환
            return commentMap.getComment();
        } else {
            // commentMap이 null인 경우, 빈 Comment 객체 또는 특정 값을 반환하거나 예외를 던질 수 있음
            return new Comment(); // 빈 Comment 객체를 반환하는 예제
        }
    }

    public void createNoticeRecommentLike() {

    }

}
