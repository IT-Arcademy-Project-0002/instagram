package com.instargram.instargram.DM.Room;

import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

// 디엠 방과 방에 참여한 회원을 매핑하는 테이블
public class Room_Member_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 디엠 방
    @ManyToOne
    @JoinColumn(name="room_id")
    Room room;

    // 디엠 방 참여 회원
    @ManyToOne
    @JoinColumn(name="member_id")
    Member member;
}
