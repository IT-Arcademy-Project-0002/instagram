package com.instargram.instargram.Notice.Component;

import com.instargram.instargram.Notice.Model.Entity.Notice;
import com.instargram.instargram.Notice.Model.Repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@EnableScheduling
@Component
public class NoticeDeleteSchedule {

    private final NoticeRepository noticeRepository;
    @Scheduled(cron = "0 0 0 * * ?",  zone = "Asia/Seoul") // 서버시간기준 매일 자정에 실행
    public void deleteOldData() {
        LocalDateTime dueDate = LocalDateTime.now().minusWeeks(11); // 11주(약 84일) 이전 데이터 삭제

        List<Notice> oldData = noticeRepository.findByCreateDateBefore(dueDate);
        noticeRepository.deleteAll(oldData);
    }
}
