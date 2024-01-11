package com.instargram.instargram.DM.Service;

import com.instargram.instargram.DM.Model.DTO.MessageDTO;
import com.instargram.instargram.DM.Model.DTO.RoomDTO;
import com.instargram.instargram.DM.Model.Entity.Message.Message_Member_Map;
import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Model.Entity.Room.Room_Member_Map;
import com.instargram.instargram.DM.Model.Repository.RoomMemberMapRepository;
import com.instargram.instargram.DM.Model.Repository.RoomRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Builder
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMemberMapRepository roomMemberMapRepository;

    private final MessageMemberMapService messageMemberMapService;
    public Room create(Member openMember, List<Member> inviteMember)
    {
        Room room = new Room();
        String name = "";

        for(int i = 0 ; i < inviteMember.size(); i ++)
        {
            name += inviteMember.get(i).getNickname() == null ? inviteMember.get(i).getUsername() : inviteMember.get(i).getNickname();
            if(i != inviteMember.size() -1)
            {
                name += ", ";
            }
        }

        room.setName(name);
        room.setCreateDate(LocalDateTime.now());

        roomRepository.save(room);

        createRoomMap(room, openMember);

        for (Member friend : inviteMember)
        {
            createRoomMap(room, friend);
        }

        return room;
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

    public Room findRoom(Member loginMember, List<Member> chatMembers)
    {
        List<Room> loginMemberRooms = findByMember(loginMember);
        List<List<Room>> chattingMemberRooms = new ArrayList<>();

        for(Member mem : chatMembers)
        {
            chattingMemberRooms.add(findByMember(mem));
        }

        for(Room room : loginMemberRooms)
        {
            for(List<Room> chatRooms : chattingMemberRooms)
            {
                if(!chatRooms.contains(room))
                {
                    loginMemberRooms.remove(room);
                }
                else{
                    if(loginMemberRooms.size() == 1)
                    {
                        return loginMemberRooms.get(0);
                    }
                }
            }
        }
        return null;
    }
    public Room getRoom(Long id)
    {
        return roomRepository.findById(id).orElse(null);
    }

    public RoomDTO getRoomDTO(Long id)
    {
        Room room = getRoom(id);
        return new RoomDTO(room, getMemberMapList(room));
    }

    private Map<String, Member> getMemberMapList(Room room) {
        Map<String, Member> memberMap = new HashMap<>();

        for (Room_Member_Map roomMemberMap : roomMemberMapRepository.findByRoom(room)) {
            Member member = roomMemberMap.getMember();
            memberMap.put(member.getUsername(), member);
        }

        return memberMap;
    }

    public List<Message_Member_Map> getList(Long id)
    {
        return messageMemberMapService.getList(getRoom(id));
    }

    public List<MessageDTO> getMessageDTOList(Long id)
    {
        Room room = getRoom(id);

        return messageMemberMapService.getMessageDTOList(room);
    }

    public void readMessageState(Map<String, Object> quitMsg)
    {
        Room room = getRoom(Long.valueOf(quitMsg.get("roomId").toString()));

        messageMemberMapService.readMessageState(room, quitMsg.get("sender").toString());
    }
}
