package com.instargram.instargram.Notice.Service;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.FollowMapService;
import com.instargram.instargram.Notice.Model.DTO.NoticeDTO;
import com.instargram.instargram.Notice.Model.Entitiy.Notice;
import com.instargram.instargram.Notice.Model.Repository.NoticeRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Builder
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void createNotice(Integer type, Member loginMember, Member member)
    {
        Notice notice = new Notice();

        notice.setType(type);
        notice.setRequestMember(loginMember);
        notice.setMember(member);
        notice.setChecked(false);
        notice.setCreateDate(LocalDateTime.now());

        noticeRepository.save(notice);
    }

    public List<NoticeDTO> getNoticeDTOsByMember(FollowMapService followMapService, Member member)
    {
        List<NoticeDTO> noticeDTOS = new ArrayList<>();

        for(Notice notice : noticeRepository.findByMember(member))
        {
            noticeDTOS.add(new NoticeDTO(member, notice , followMapService));
        }

        return noticeDTOS;
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
