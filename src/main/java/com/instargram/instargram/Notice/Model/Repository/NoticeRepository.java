package com.instargram.instargram.Notice.Model.Repository;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Notice.Model.Entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n WHERE n.member = :member AND n.createDate >= :oneWeekAgo ORDER BY n.createDate DESC")
    List<Notice> findNoticesInLastWeek(@Param("member") Member member, @Param("oneWeekAgo") LocalDateTime oneWeekAgo);

    @Query("SELECT n FROM Notice n WHERE n.member = :member AND n.createDate <= :oneWeekAgo AND n.createDate >= :oneMonthAgo ORDER BY n.createDate DESC")
    List<Notice> findNoticesInLastMonth(@Param("member") Member member, @Param("oneWeekAgo") LocalDateTime oneWeekAgo, @Param("oneMonthAgo") LocalDateTime oneMonthAgo);

    @Query("SELECT n FROM Notice n WHERE n.member = :member AND n.createDate <= :oneMonthAgo AND n.createDate >= :dueDate ORDER BY n.createDate DESC")
    List<Notice> findNoticesInPast(@Param("member") Member member, @Param("oneMonthAgo") LocalDateTime oneMonthAgo, @Param("dueDate") LocalDateTime dueDate);

    Notice findByTypeAndRequestMemberAndMember(Integer type, Member requestmember, Member member);

    List<Notice> findByMemberAndCheckedAndTypeNotIn(Member member, boolean checked, List<Integer> types); // 미열람 알림 중 타입이 아닌 것을 찾기 (DM외, NotIn)

    List<Notice> findByMemberAndCheckedAndTypeIn(Member member, boolean checked,  List<Integer> types); // 미열람 알림 중 타입에 해당하는 것을 찾기 (DM만, In)

    List<Notice> findByCreateDateBefore(LocalDateTime dueDate); // 12주를 넘어가는 알림을 자동삭제 하기 위한 함수
}
