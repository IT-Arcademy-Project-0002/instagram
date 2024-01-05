package com.instargram.instargram.Notice.Model.Repository;

import com.instargram.instargram.Notice.Model.Entitiy.Notice_Comment_Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Notice_Comment_MapRepository extends JpaRepository<Notice_Comment_Map, Long> {

    Notice_Comment_Map findByNoticeId(Long noticeId);

}
