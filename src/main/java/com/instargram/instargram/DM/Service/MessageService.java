package com.instargram.instargram.DM.Service;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.DM.Model.Entity.Message.CommentMessage;
import com.instargram.instargram.DM.Model.Entity.Message.Emoji;
import com.instargram.instargram.DM.Model.Entity.Message.Message;
import com.instargram.instargram.DM.Model.Entity.Message.Message_Member_Map;
import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Model.Repository.CommentMessageRepository;
import com.instargram.instargram.DM.Model.Repository.EmojiRepository;
import com.instargram.instargram.DM.Model.Repository.MessageRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.transaction.Transactional;
import lombok.Builder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return commentMessageRepository.findByMessage(getMessage(id));
    }

    public List<CommentMessage> getCommentList(Message_Member_Map map)
    {
        return commentMessageRepository.findByMessageMap(map);
    }

    public void delete(Long id)
    {
        messageRepository.deleteById(id);
    }

    public void deleteComment(CommentMessage commentMessage)
    {
        commentMessageRepository.delete(commentMessage);
    }

    @Transactional
    public void deleteComment(Long id)
    {
        Message message = getMessage(id);
        commentMessageRepository.deleteByMessage(message);
        messageRepository.delete(message);
    }


    public CommentMessage createComment(Map<String, Object> msg, Message_Member_Map map)
    {
        Message message = new Message();
        message.setContent(msg.get("msg").toString());

        messageRepository.save(message);

        CommentMessage commentMessage = new CommentMessage();
        commentMessage.setMessage(message);
        commentMessage.setMessageMap(map);

        return commentMessageRepository.save(commentMessage);
    }

}
