package com.instargram.instargram.Member.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity

// 비공개 계정일 시 팔로우 요청 매핑 테이블
// 요청 수락 시 해당 테이블에선 데이터를 삭제하고 Follow_Map에 save 해줘야함
public class Follow_Request_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 계정주
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Member owner;

    // 팔로우를 요청한 사람
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="request_member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Member requestMember;
}
