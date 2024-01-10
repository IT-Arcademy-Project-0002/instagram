package com.instargram.instargram.Notice.Model.Repository;

import com.instargram.instargram.Notice.Model.Entity.Notice_Comment_Map;
import com.instargram.instargram.Notice.Model.Entity.Notice_Recomment_Like_Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Notice_Recomment_Like_MapRepository extends JpaRepository<Notice_Recomment_Like_Map, Long> {

    Notice_Recomment_Like_Map findByNoticeId(Long noticeId);

}
