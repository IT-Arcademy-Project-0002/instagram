package com.instargram.instargram.Notice.Model.Repository;

import com.instargram.instargram.Notice.Model.Entity.Notice_Board_TagMember_Map;
import com.instargram.instargram.Notice.Model.Entity.Notice_Comment_Like_Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Notice_Board_TagMember_MapRepository extends JpaRepository<Notice_Board_TagMember_Map, Long> {

    Notice_Board_TagMember_Map findByNoticeId(Long noticeId);
}
