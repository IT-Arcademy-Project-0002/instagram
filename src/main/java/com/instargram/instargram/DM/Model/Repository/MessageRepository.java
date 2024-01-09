package com.instargram.instargram.DM.Model.Repository;

import com.instargram.instargram.DM.Model.Entity.Message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
