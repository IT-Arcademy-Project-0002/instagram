package com.instargram.instargram.Community.Board.Model.Repository;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b WHERE b.member = :member and b.keep = false ORDER BY CASE WHEN b.pin = true THEN b.pinDate ELSE b.createDate END DESC")
    List<Board> findByMemberOrderByPinDateOrCreateDateDesc(@Param("member") Member member);


    Integer countByMemberAndPin(Member member, boolean pin);
}
