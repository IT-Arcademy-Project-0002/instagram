package com.instargram.instargram.DM.Room;

import com.instargram.instargram.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Room_Member_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name="room_id")
    Room room;

    @ManyToOne
    @JoinColumn(name="member_id")
    Member member;
}
