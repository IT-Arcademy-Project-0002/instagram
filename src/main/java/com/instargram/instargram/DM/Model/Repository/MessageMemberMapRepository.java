package com.instargram.instargram.DM.Model.Repository;

import com.instargram.instargram.DM.Model.Entity.Message.Message_Member_Map;
import com.instargram.instargram.DM.Model.Entity.Room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageMemberMapRepository extends JpaRepository<Message_Member_Map, Long> {

    List<Message_Member_Map> findAllByRoomOrderByCreateDateAsc(Room room);

    List<Message_Member_Map> findByRoomAndMember_UsernameNotAndSeeMemberNotContainingOrderByCreateDateAsc(Room room, String username, String name);
}
