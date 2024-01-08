package com.instargram.instargram.Notice.Service;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Community.Comment.Model.Repository.CommentRepository;
import com.instargram.instargram.Notice.Model.Entity.Notice;
import com.instargram.instargram.Notice.Model.Entity.Notice_Comment_Like_Map;
import com.instargram.instargram.Notice.Model.Entity.Notice_Comment_Map;
import com.instargram.instargram.Notice.Model.Repository.Notice_Comment_MapRepository;
import com.instargram.instargram.Notice.Model.Repository.Notice_Comment_Like_MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeCommentMapService {

    private final Notice_Comment_MapRepository noticeCommentMapRepository;
    private final CommentRepository commentRepository;
    private final Notice_Comment_Like_MapRepository noticeCommentLikeMapRepository;

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
        this.noticeCommentLikeMapRepository.save(noticeCommentLikeMap);

    }

    public Comment getNoticeComment(Long noticeId, Integer type) {

        if (type == 2) {

            Notice_Comment_Map commentMap = this.noticeCommentMapRepository.findByNoticeId(noticeId);
            if (commentMap != null) {
                return commentMap.getComment();
            }
        }

        if (type == 3) {

            Notice_Comment_Like_Map commentLikeMap = this.noticeCommentLikeMapRepository.findByNoticeId(noticeId);
            if (commentLikeMap != null) {
                return commentLikeMap.getCommentLike().getComment();
            }
        }

        return new Comment();
    }
}
