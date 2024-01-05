package com.instargram.instargram.Notice.Service;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Notice.Model.Entity.Notice;
import com.instargram.instargram.Notice.Model.Entity.Notice_Comment_Map;
import com.instargram.instargram.Notice.Model.Repository.Notice_Comment_MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeCommentMapService {

    private final Notice_Comment_MapRepository noticeCommentMapRepository;

    public void createNoticeComment(Comment comment, Notice notice) {

        Notice_Comment_Map noticeCommentMap = new Notice_Comment_Map();
        noticeCommentMap.setComment(comment);
        noticeCommentMap.setNotice(notice);
        this.noticeCommentMapRepository.save(noticeCommentMap);

    }
}
