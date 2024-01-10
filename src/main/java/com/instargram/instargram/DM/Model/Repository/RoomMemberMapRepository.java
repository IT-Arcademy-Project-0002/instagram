package com.instargram.instargram.DM.Model.Repository;

import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Model.Entity.Room.Room_Member_Map;
import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomMemberMapRepository extends JpaRepository<Room_Member_Map, Long> {
    List<Room_Member_Map> findByMember(Member member);

    List<Room_Member_Map> findByRoom(Room room);
}
