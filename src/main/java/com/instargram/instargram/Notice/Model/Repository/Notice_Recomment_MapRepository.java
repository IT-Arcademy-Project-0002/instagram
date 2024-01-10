package com.instargram.instargram.Notice.Model.Repository;

import com.instargram.instargram.Notice.Model.Entity.Notice_Recomment_Like_Map;
import com.instargram.instargram.Notice.Model.Entity.Notice_Recomment_Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Notice_Recomment_MapRepository extends JpaRepository<Notice_Recomment_Map, Long> {

    Notice_Recomment_Map findByNoticeId(Long noticeId);
}
