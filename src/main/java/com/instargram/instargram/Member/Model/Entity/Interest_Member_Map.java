package com.instargram.instargram.Member.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity

// 관심 계정 : 관심계정이 게시글, 스토리등을 올렸을 때 계정주에게 알림을 보내는 기능에 활용
// 계정주와 관신 계정을 매핑해주는 테이블
public class Interest_Member_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 계정주
    @ManyToOne()
    @JoinColumn(name="owner_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Member owner;


    // 관심 계정
    @ManyToOne
    @JoinColumn(name="interest_member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Member interestMemberId;
}
