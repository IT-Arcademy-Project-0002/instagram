package com.instargram.instargram.DM.Model.Repository;

import com.instargram.instargram.DM.Model.Entity.Message.Emoji;
import com.instargram.instargram.DM.Model.Entity.Message.Message_Member_Map;
import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmojiRepository extends JpaRepository<Emoji, Long> {

    Emoji findByMemberAndMap(Member member, Message_Member_Map map);
}
