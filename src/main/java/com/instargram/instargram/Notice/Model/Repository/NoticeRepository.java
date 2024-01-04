package com.instargram.instargram.Notice.Model.Repository;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Notice.Model.Entitiy.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByMember(Member member);
}
