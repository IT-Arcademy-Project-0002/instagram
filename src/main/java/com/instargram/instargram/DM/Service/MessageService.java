package com.instargram.instargram.DM.Service;

import com.instargram.instargram.DM.Model.Entity.Message.Message;
import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Model.Repository.MessageRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Builder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Builder
public class MessageService {

    private final MessageRepository messageRepository;

    public Message create(Map<String, Object> msg)
    {
        Message message = new Message();
        message.setContent(msg.get("msg").toString());

        return messageRepository.save(message);
    }

    public Message getMessage(Long id)
    {
        return messageRepository.findById(id).orElse(null);
    }

    public void delete(Long id)
    {
        messageRepository.deleteById(id);
    }
}
