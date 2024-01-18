package com.instargram.instargram.Notice.Service;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Community.Comment.Model.Repository.CommentRepository;
import com.instargram.instargram.Community.Recomment.Model.Entity.ReComment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Model.Repository.MemberRepository;
import com.instargram.instargram.Notice.Model.Entity.*;
import com.instargram.instargram.Notice.Model.Repository.Notice_Comment_MapRepository;
import com.instargram.instargram.Notice.Model.Repository.Notice_Comment_Like_MapRepository;
import com.instargram.instargram.Notice.Model.Repository.Notice_Recomment_Like_MapRepository;
import com.instargram.instargram.Notice.Model.Repository.Notice_Recomment_MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class NoticeCommentMapService {

    private final MemberRepository memberRepository;
    private final Notice_Comment_MapRepository noticeCommentMapRepository;
    private final Notice_Recomment_MapRepository noticeRecommentMapRepository;
    private final Notice_Comment_Like_MapRepository noticeCommentLikeMapRepository;
    private final Notice_Recomment_Like_MapRepository noticeRecommentLikeMapRepository;

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

    public void createNoticeRecomment(Recomment recomment, Notice notice) {

        Notice_Recomment_Map noticeRecommentMap = new Notice_Recomment_Map();
        noticeRecommentMap.setRecomment(recomment);
        noticeRecommentMap.setNotice(notice);
        this.noticeRecommentMapRepository.save(noticeRecommentMap);

    }

    public void createNoticeRecommentLike(ReComment_Like_Map recommentLikeMap, Notice notice) {

        Notice_Recomment_Like_Map noticeRecommentLikeMap = new Notice_Recomment_Like_Map();
        noticeRecommentLikeMap.setRecommentLike(recommentLikeMap);
        noticeRecommentLikeMap.setNotice(notice);
        this.noticeRecommentLikeMapRepository.save(noticeRecommentLikeMap);

    }

    public Member createTagMember(String recommentContent) {

        if (recommentContent != null) {
            // 정규표현식을 사용하여 '@' 뒤의 단어 추출
            final String regex = "@(\\S+)";
            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(recommentContent);

            // '@' 뒤의 회원아이디를 찾기 전에 매치 확인
            if (matcher.find()) {
                final String username = matcher.group(1);

                return this.memberRepository.findByUsername(username);
            }
        }

        return new Member();
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

    public Recomment getNoticeRecomment(Long noticeId, Integer type) {

        if (type == 4) {

            Notice_Recomment_Map recommentMap = this.noticeRecommentMapRepository.findByNoticeId(noticeId);
            if (recommentMap != null) {
                return recommentMap.getRecomment();
            }
        }

        if (type == 5) {

            Notice_Recomment_Like_Map recommentLikeMap = this.noticeRecommentLikeMapRepository.findByNoticeId(noticeId);
            if (recommentLikeMap != null) {
                return recommentLikeMap.getRecommentLike().getRecomment();
            }
        }

        return new Recomment();
    }

}
