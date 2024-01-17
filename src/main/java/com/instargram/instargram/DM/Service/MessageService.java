package com.instargram.instargram.DM.Service;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.DM.Model.Entity.Message.CommentMessage;
import com.instargram.instargram.DM.Model.Entity.Message.Message;
import com.instargram.instargram.DM.Model.Entity.Message.Message_Member_Map;
import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Model.Repository.CommentMessageRepository;
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
    private final CommentMessageRepository commentMessageRepository;

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

    public CommentMessage getCommentMessage(Long id)
    {
        return commentMessageRepository.findById(id).orElse(null);
    }

    public void delete(Long id)
    {
        messageRepository.deleteById(id);
    }

    public CommentMessage createComment(Map<String, Object> msg, Message_Member_Map map)
    {
        CommentMessage commentMessage = new CommentMessage();
        commentMessage.setContent(msg.get("msg").toString());
        commentMessage.setMessageMap(map);

        return commentMessageRepository.save(commentMessage);
    }
}
