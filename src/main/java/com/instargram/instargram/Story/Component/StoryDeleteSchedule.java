package com.instargram.instargram.Story.Component;

import com.instargram.instargram.Story.Model.Entity.Story_Data_Map;
import com.instargram.instargram.Story.Model.Repository.StoryDataMapRepository;
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
public class StoryDeleteSchedule {
    private final StoryDataMapRepository storyDataMapRepository;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul") // 서버시간기준 매일 자정에 실행
    public void deleteOldData() {
        LocalDateTime dueDate = LocalDateTime.now().minusHours(24); // 24시간 이전 데이터 삭제

        List<Story_Data_Map> oldData = this.storyDataMapRepository.findByCreateDateBefore(dueDate);
        storyDataMapRepository.deleteAll(oldData);
    }
}
