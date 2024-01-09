package com.instargram.instargram.DM.Service;

import com.instargram.instargram.DM.Model.Entity.Message.Message;
import com.instargram.instargram.DM.Model.Entity.Message.Message_Member_Map;
import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Model.Repository.MessageMemberMapRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Builder
public class MessageMemberMapService {

    private final MessageMemberMapRepository messageMemberMapRepository;
    private final MemberService memberService;
    private final RoomService roomService;

    public List<Message_Member_Map> getList(Room room)
    {
        return messageMemberMapRepository.findAllByRoomOrderByCreateDateAsc(room);
    }

    public void create(Map<String, Object> msg, Message message)
    {
        Member sender = memberService.getMember(msg.get("sender").toString());
        Message_Member_Map messageMemberMap = new Message_Member_Map();
        messageMemberMap.setMember(sender);
        messageMemberMap.setCreateDate(LocalDateTime.now());
        messageMemberMap.setRoom(roomService.getRoom(Long.valueOf(msg.get("roomId").toString())));
        messageMemberMap.setDataId(message.getId());
        messageMemberMap.setDataType(1);
        messageMemberMap.setEmpathy(null);

        messageMemberMapRepository.save(messageMemberMap);
    }
}
