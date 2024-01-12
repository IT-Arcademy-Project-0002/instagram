package com.instargram.instargram.DM.Service;

import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Model.Entity.Room.Room_Member_Map;
import com.instargram.instargram.DM.Model.Repository.RoomMemberMapRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
public class RoomMemberMapService {
    private final RoomMemberMapRepository roomMemberMapRepository;


    public void createRoomMap(Room room, Member member)
    {
        Room_Member_Map roomMemberMap = new Room_Member_Map();
        roomMemberMap.setRoom(room);
        roomMemberMap.setMember(member);

        roomMemberMapRepository.save(roomMemberMap);
    }

    public List<Room> findByMember(Member member)
    {
        return roomMemberMapRepository.findByMember(member).stream().map(Room_Member_Map::getRoom).toList();
    }

    public List<Room_Member_Map> getByRoom(Room room)
    {
        return roomMemberMapRepository.findByRoom(room);
    }
}
