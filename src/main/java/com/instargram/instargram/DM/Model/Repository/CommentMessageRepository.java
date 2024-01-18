package com.instargram.instargram.DM.Model.Repository;

import com.instargram.instargram.DM.Model.Entity.Message.CommentMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentMessageRepository extends JpaRepository<CommentMessage, Long> {
}
