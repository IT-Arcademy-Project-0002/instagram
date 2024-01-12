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
import lombok.Getter;
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

    @Getter
    private final RoomMemberMapService roomMemberMapService;

    private final MessageMemberMapService messageMemberMapService;
    public Room create(Member openMember, List<Member> inviteMember)
    {
        Room room = new Room();
        room.setCreateDate(LocalDateTime.now());

        roomRepository.save(room);

        roomMemberMapService.createRoomMap(room, openMember);

        for (Member friend : inviteMember)
        {
            roomMemberMapService.createRoomMap(room, friend);
        }

        return room;
    }

    public Room findRoom(Member loginMember, List<Member> chatMembers)
    {
        List<Room> loginMemberRooms = roomMemberMapService.findByMember(loginMember);
        List<List<Room>> chattingMemberRooms = new ArrayList<>();

        for(Member mem : chatMembers)
        {
            chattingMemberRooms.add(roomMemberMapService.findByMember(mem));
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

    public RoomDTO getRoomDTO(Member loginUSer, Long id)
    {
        Room room = getRoom(id);
        return new RoomDTO(loginUSer, room, getMemberMapList(room));
    }

    public List<RoomDTO> getRoomDTOList(Member loginUSer)
    {
        List<Room> roomList = roomMemberMapService.findByMember(loginUSer);

        List<RoomDTO> roomDTOList = new ArrayList<>();
        for(Room room : roomList)
        {
            roomDTOList.add(new RoomDTO(loginUSer, room, getMemberMapList(room)));
        }

        return roomDTOList;
    }

    private Map<String, Member> getMemberMapList(Room room) {
        Map<String, Member> memberMap = new HashMap<>();

        for (Room_Member_Map roomMemberMap : roomMemberMapService.getByRoom(room)) {
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

    public void readMessageState(Long id, String username)
    {
        Room room = getRoom(id);

        messageMemberMapService.readMessageState(room, username);
    }
}
