package com.instargram.instargram.DM.Model.Repository;

import com.instargram.instargram.DM.Model.Entity.Message.CommentMessage;
import com.instargram.instargram.DM.Model.Entity.Message.Message;
import com.instargram.instargram.DM.Model.Entity.Message.Message_Member_Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentMessageRepository extends JpaRepository<CommentMessage, Long> {

    CommentMessage findByMessage(Message message);

    List<CommentMessage> findByMessageMap(Message_Member_Map map);
}
