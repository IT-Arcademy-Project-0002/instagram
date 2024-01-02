package com.instargram.instargram.Notice;

import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByMember(Member member);
}
