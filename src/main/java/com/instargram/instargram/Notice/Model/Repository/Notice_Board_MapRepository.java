package com.instargram.instargram.Notice.Model.Repository;

import com.instargram.instargram.Notice.Model.Entitiy.Notice_Board_Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Notice_Board_MapRepository extends JpaRepository<Notice_Board_Map, Long>  {

    Notice_Board_Map findByNoticeId(Long noticeId);

}
