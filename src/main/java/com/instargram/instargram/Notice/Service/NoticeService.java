package com.instargram.instargram.Notice.Service;

import com.instargram.instargram.Community.Board.Model.Repository.BoardRepository;
import com.instargram.instargram.Community.Comment.Service.CommentService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.FollowMapService;
import com.instargram.instargram.Notice.Model.DTO.NoticeDTO;
import com.instargram.instargram.Notice.Model.Entity.Notice;
import com.instargram.instargram.Notice.Model.Entity.Notice_Comment_Map;
import com.instargram.instargram.Notice.Model.Repository.NoticeRepository;
import com.instargram.instargram.Notice.Model.Repository.Notice_Comment_Like_MapRepository;
import com.instargram.instargram.Notice.Model.Repository.Notice_Comment_MapRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Builder
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final BoardRepository boardRepository;
    private final FollowMapService followMapService;
    private final CommentService commentService;
    private final NoticeCommentMapService noticeCommentMapService;
    private final Notice_Comment_MapRepository noticeCommentMapRepository;
    private final NoticeBoardMapService noticeBoardMapService;

    public Notice createNotice(Integer type, Member loginMember, Member member)
    {
        Notice notice = new Notice();

        notice.setType(type);
        notice.setRequestMember(loginMember);
        notice.setMember(member);
        notice.setChecked(false);
        notice.setCreateDate(LocalDateTime.now());

        noticeRepository.save(notice);

        return notice;
    }

    public List<NoticeDTO> getNoticeDTOsByMember(Member loginUser)
    {
        List<Notice> noticeList = this.noticeRepository.findByMember(loginUser);

        List<NoticeDTO> noticeDTOS = new ArrayList<>();

        for(Notice notice : noticeList)
        {
            NoticeDTO noticeDTO = new NoticeDTO();

            // 공통 객체 + 팔로우 요청 : 8
            noticeDTO.setRequestMember(notice.getRequestMember());
            noticeDTO.setType(notice.getType());
            noticeDTO.setId(notice.getId());
            noticeDTO.setCreateDate(getElapsedTime(notice.getCreateDate()));
            noticeDTO.setFollower(followMapService.isFollower(loginUser, notice.getRequestMember()));
            noticeDTO.setFollow(followMapService.isFollow(loginUser, notice.getRequestMember()));


            //  (notice 타입을 식별자로 사용하여 코드 간소화 진행)

            // 보드 이미지 : 게시글 좋아요(1), 게시글 댓글(2), 댓글 좋아요(3), 게시글 멤버태그(9)
            noticeDTO.setBoardMainImage(noticeBoardMapService.getNoticeBoardImage(notice.getId(), notice.getType()));

            // 댓글 내용 : 게시글 댓글(2), 댓글 좋아요(3)
            noticeDTO.setCommentContent(noticeCommentMapService.getNoticeComment(notice.getId(), notice.getType()).getContent());

            // 댓글 대댓글 : 4

            // 디엠 왔을 때 : 5

            // 디엠 좋아요 : 6

            // 스토리 좋아요 : 7

            noticeDTOS.add(noticeDTO);
        }

        return noticeDTOS;
    }

    public String getElapsedTime(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(localDateTime, now);

        if (duration.toSeconds() < 60) {
            return "지금";
        } else if (duration.toMinutes() < 60) {
            return duration.toMinutes() + "분";
        } else if (duration.toHours() < 24) {
            return duration.toHours() + "시간";
        } else {
            long days = ChronoUnit.DAYS.between(localDateTime, now);
            return days + "일";
        }
    }

    public void deleteById(Long id)
    {
        noticeRepository.deleteById(id);
    }

    public Notice getNotice(Long id)
    {
        return noticeRepository.findById(id).orElse(null);
    }

}
