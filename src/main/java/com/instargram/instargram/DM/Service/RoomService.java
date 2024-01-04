package com.instargram.instargram.DM.Service;

import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Model.Entity.Room.Room_Member_Map;
import com.instargram.instargram.DM.Model.Repository.RoomMemberMapRepository;
import com.instargram.instargram.DM.Model.Repository.RoomRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMemberMapRepository roomMemberMapRepository;

    public void create(Member openMember, List<Member> inviteMember)
    {
        Room room = new Room();
        String name = openMember.getNickname() == null ? openMember.getUsername() : openMember.getNickname();

        for(Member friend : inviteMember)
        {
            name += friend.getNickname() == null ? friend.getUsername() : friend.getNickname();
        }

        room.setName(name);

        roomRepository.save(room);

        createRoomMap(room, openMember);

        for (Member friend : inviteMember)
        {
            createRoomMap(room, friend);
        }
    }

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

    public Room getRoom(Long id)
    {
        return roomRepository.findById(id).orElse(null);
    }
}
