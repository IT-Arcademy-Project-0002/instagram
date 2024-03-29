package com.instargram.instargram.Notice.Service;

import com.instargram.instargram.Community.Board.Model.Repository.BoardRepository;
import com.instargram.instargram.Community.Comment.Service.CommentService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.FollowMapService;
import com.instargram.instargram.Notice.Config.Enum.NoticeType;
import com.instargram.instargram.Notice.Model.DTO.NoticeDTO;
import com.instargram.instargram.Notice.Model.Entity.Notice;
import com.instargram.instargram.Notice.Model.Repository.NoticeRepository;
import com.instargram.instargram.Notice.Model.Repository.Notice_Comment_MapRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    public List<NoticeDTO> createNoticeList(Member loginMember) {

        List<NoticeDTO> notices = new ArrayList<>();

        List<NoticeDTO> noticesByWeek = getNoticeDTOsByWeek(loginMember);
        List<NoticeDTO> noticesByMonth = getNoticeDTOsByMonth(loginMember);
        List<NoticeDTO> noticesUntilDueDate = getNoticeDTOsUntilDueDate(loginMember);

        notices.addAll(noticesByWeek);
        notices.addAll(noticesByMonth);
        notices.addAll(noticesUntilDueDate);

        return notices;
    }

    public Notice createNotice(Integer type, Member loginMember, Member member)
    {
        Notice notice = new Notice();

        if (type == 7 || type == 8) {
            Notice existingNotice = this.noticeRepository.findByTypeAndRequestMemberAndMember(type, loginMember, member);
            if (existingNotice != null) {
                return existingNotice;
            }
        }

        notice.setType(type);
        notice.setRequestMember(loginMember);
        notice.setMember(member);
        notice.setChecked(false);
        notice.setCreateDate(LocalDateTime.now());

        this.noticeRepository.save(notice);

        return notice;
    }

    public List<NoticeDTO> getNoticeDTOsByWeek(Member loginUser)
    {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);

        List<Notice> noticesLastWeek = this.noticeRepository.findNoticesInLastWeek(loginUser, oneWeekAgo);

        List<NoticeDTO> noticeWeekDTOS = new ArrayList<>();

        for(Notice notice : noticesLastWeek)
        {
            NoticeDTO noticeWeekDTO = new NoticeDTO();

            // 공통 객체 : 모든 경우(ALL) + 팔로우 요청(7) + 팔로우 상태 (8)
            noticeWeekDTO.setRequestMember(notice.getRequestMember());
            noticeWeekDTO.setType(notice.getType());
            noticeWeekDTO.setId(notice.getId());
            noticeWeekDTO.setCreateTime(notice.getCreateDate());
            noticeWeekDTO.setElapsedTime(getElapsedTime(notice.getCreateDate()));
            noticeWeekDTO.setPeriod(NoticeType.WEEK.getNumber());
            noticeWeekDTO.setFollower(followMapService.isFollower(loginUser, notice.getRequestMember()));
            noticeWeekDTO.setFollow(followMapService.isFollow(loginUser, notice.getRequestMember()));

            // 보드 내용 : 보드내용에는 언급된 회원의 정보가 포함되도록 getBoardContent 내부에 로직을 작성하였음 (게시물 멤버태그(6))
            noticeWeekDTO.processContent(noticeBoardMapService.getBoardContent(notice.getId(), notice.getType()));

            // 보드 이미지 : 게시글 좋아요(1), 게시글 댓글(2), 댓글 좋아요(3), 댓글 대댓글(4), 댓글 대댓글 좋아요(5), 게시글 멤버태그(6)
            noticeWeekDTO.setBoardMainImage(noticeBoardMapService.getNoticeBoardImage(notice.getId(), notice.getType()));

            // 댓글 내용 : 게시글 댓글(2), 댓글 좋아요(3)
            noticeWeekDTO.setCommentContent(noticeCommentMapService.getNoticeComment(notice.getId(), notice.getType()).getContent());

            // 대댓글 내용 : 댓글 대댓글(4), 댓글 대댓글 좋아요(5)
            noticeWeekDTO.processRecommentContent(noticeCommentMapService.getNoticeRecomment(notice.getId(), notice.getType()).getContent());

            // 미사용중 = 디엠 왔을 때(9), 디엠 좋아요(10), 스토리 좋아요 (11)

            noticeWeekDTOS.add(noticeWeekDTO);
        }

        return noticeWeekDTOS;
    }

    public List<NoticeDTO> getNoticeDTOsByMonth(Member loginUser)
    {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        List<Notice> noticesLastMonth = this.noticeRepository.findNoticesInLastMonth(loginUser, oneWeekAgo, oneMonthAgo);

        List<NoticeDTO> noticeMonthDTOS = new ArrayList<>();

        for(Notice notice : noticesLastMonth)
        {
            NoticeDTO noticeMonthDTO = new NoticeDTO();

            // 공통 객체 : 모든 경우(ALL) + 팔로우 요청(7) + 팔로우 상태 (8)
            noticeMonthDTO.setRequestMember(notice.getRequestMember());
            noticeMonthDTO.setType(notice.getType());
            noticeMonthDTO.setId(notice.getId());
            noticeMonthDTO.setCreateTime(notice.getCreateDate());
            noticeMonthDTO.setElapsedTime(getElapsedTime(notice.getCreateDate()));
            noticeMonthDTO.setPeriod(NoticeType.MONTH.getNumber());
            noticeMonthDTO.setFollower(followMapService.isFollower(loginUser, notice.getRequestMember()));
            noticeMonthDTO.setFollow(followMapService.isFollow(loginUser, notice.getRequestMember()));

            // 보드 내용 : 보드내용에는 언급된 회원의 정보가 포함되도록 getBoardContent 내부에 로직을 작성하였음 (게시물 멤버태그(6))
            noticeMonthDTO.processContent(noticeBoardMapService.getBoardContent(notice.getId(), notice.getType()));

            // 보드 이미지 : 게시글 좋아요(1), 게시글 댓글(2), 댓글 좋아요(3), 댓글 대댓글(4), 댓글 대댓글 좋아요(5), 게시글 멤버태그(6)
            noticeMonthDTO.setBoardMainImage(noticeBoardMapService.getNoticeBoardImage(notice.getId(), notice.getType()));

            // 댓글 내용 : 게시글 댓글(2), 댓글 좋아요(3)
            noticeMonthDTO.setCommentContent(noticeCommentMapService.getNoticeComment(notice.getId(), notice.getType()).getContent());

            // 대댓글 내용 : 댓글 대댓글(4), 댓글 대댓글 좋아요(5)
            noticeMonthDTO.processRecommentContent(noticeCommentMapService.getNoticeRecomment(notice.getId(), notice.getType()).getContent());

            // 미사용중 = 디엠 왔을 때(9), 디엠 좋아요(10), 스토리 좋아요 (11)

            noticeMonthDTOS.add(noticeMonthDTO);
        }

        return noticeMonthDTOS;
    }

    public List<NoticeDTO> getNoticeDTOsUntilDueDate(Member loginUser)
    {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        LocalDateTime DueDate = LocalDateTime.now().minusMonths(11);

        List<Notice> noticesUntilDueDate = this.noticeRepository.findNoticesInPast(loginUser, oneMonthAgo, DueDate);

        List<NoticeDTO> noticeDueDateDTOS = new ArrayList<>();

        for(Notice notice : noticesUntilDueDate)
        {
            NoticeDTO noticeMonthDTO = new NoticeDTO();

            // 공통 객체 : 모든 경우(ALL) + 팔로우 요청(7) + 팔로우 상태 (8)
            noticeMonthDTO.setRequestMember(notice.getRequestMember());
            noticeMonthDTO.setType(notice.getType());
            noticeMonthDTO.setId(notice.getId());
            noticeMonthDTO.setCreateTime(notice.getCreateDate());
            noticeMonthDTO.setElapsedTime(getElapsedTime(notice.getCreateDate()));
            noticeMonthDTO.setPeriod(NoticeType.OLD.getNumber());
            noticeMonthDTO.setFollower(followMapService.isFollower(loginUser, notice.getRequestMember()));
            noticeMonthDTO.setFollow(followMapService.isFollow(loginUser, notice.getRequestMember()));

            // 보드 내용 : 보드내용에는 언급된 회원의 정보가 포함되도록 getBoardContent 내부에 로직을 작성하였음 (게시물 멤버태그(6))
            noticeMonthDTO.processContent(noticeBoardMapService.getBoardContent(notice.getId(), notice.getType()));

            // 보드 이미지 : 게시글 좋아요(1), 게시글 댓글(2), 댓글 좋아요(3), 댓글 대댓글(4), 댓글 대댓글 좋아요(5), 게시글 멤버태그(6)
            noticeMonthDTO.setBoardMainImage(noticeBoardMapService.getNoticeBoardImage(notice.getId(), notice.getType()));

            // 댓글 내용 : 게시글 댓글(2), 댓글 좋아요(3)
            noticeMonthDTO.setCommentContent(noticeCommentMapService.getNoticeComment(notice.getId(), notice.getType()).getContent());

            // 대댓글 내용 : 댓글 대댓글(4), 댓글 대댓글 좋아요(5)
            noticeMonthDTO.processRecommentContent(noticeCommentMapService.getNoticeRecomment(notice.getId(), notice.getType()).getContent());

            // 미사용중 = 디엠 왔을 때(9), 디엠 좋아요(10), 스토리 좋아요 (11)

            noticeDueDateDTOS.add(noticeMonthDTO);
        }

        return noticeDueDateDTOS;
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
        } else if (duration.toDays() < 30) {
            return duration.toDays() + "일";
        } else {
            long weeks = duration.toDays() / 7;
            return weeks + "주";
        }
    }

    public List<Notice> checkDMList(Member member) {
        return this.noticeRepository.findByMemberAndCheckedAndTypeIn(member, false, Arrays.asList(10, 11));
    }

    public List<Notice> checkNoticeList(Member member) {
        return this.noticeRepository.findByMemberAndCheckedAndTypeNotIn(member, false, Arrays.asList(10, 11));
    }

    @Transactional
    public void noticeChecking(Member member, List<Integer> types) {
        List<Notice> uncheckedNotices = this.noticeRepository.findByMemberAndCheckedAndTypeNotIn(member, false, types);

        for (Notice notice : uncheckedNotices) {
            notice.setChecked(true);
        }

        noticeRepository.saveAll(uncheckedNotices);
    }

    @Transactional
    public void noticeDMChecking(Member member, List<Integer> types) {
        List<Notice> uncheckedNotices = this.noticeRepository.findByMemberAndCheckedAndTypeIn(member, false, types);

        for (Notice notice : uncheckedNotices) {
            notice.setChecked(true);
        }
        noticeRepository.saveAll(uncheckedNotices);
    }

    public void deleteNotice(Notice notice)
    {
        this.noticeRepository.delete(notice);
    }

    public void deleteNoticeById(Long id)
    {
        this.noticeRepository.deleteById(id);
    }

    public void deleteNoticeByMemberAndTarget(Integer type, Member loginMember, Member member) {

        if (type == 7 || type == 8) {
            Notice existingNotice = this.noticeRepository.findByTypeAndRequestMemberAndMember(type, loginMember, member);
            if (existingNotice != null) {
                this.noticeRepository.delete(existingNotice);
            }
        }

    }

    public Notice getNoticeByMemberAndTarget(Integer type, Member loginMember, Member member) {
        return this.noticeRepository.findByTypeAndRequestMemberAndMember(type, loginMember, member);
    }

    public Notice getNotice(Long id)
    {
        return this.noticeRepository.findById(id).orElse(null);
    }

}
