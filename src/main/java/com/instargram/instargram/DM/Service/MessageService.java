package com.instargram.instargram.DM.Service;

import com.instargram.instargram.DM.Model.Entity.Message.Message;
import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Model.Repository.MessageRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Builder
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMemberMapService messageMemberMapService;

    public void create(Map<String, Object> msg, Room room)
    {
        Message message = new Message();
        message.setContent(msg.get("msg").toString());

        messageRepository.save(message);

        messageMemberMapService.create(msg, message, room);
    }
}
