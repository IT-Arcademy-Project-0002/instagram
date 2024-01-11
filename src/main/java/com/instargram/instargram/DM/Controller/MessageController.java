package com.instargram.instargram.DM.Controller;

import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Service.MessageMemberMapService;
import com.instargram.instargram.DM.Service.MessageService;
import com.instargram.instargram.DM.Service.RoomService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@Builder
@RequestMapping("/message")
public class MessageController {

    private final MessageMemberMapService messageMemberMapService;
    private final RoomService roomService;

    @PostMapping("/create")
    public void create(
            @RequestBody Map<String, Object> talkMsg)
    {
        Room room = roomService.getRoom(Long.valueOf(talkMsg.get("roomId").toString()));
        messageMemberMapService.createMessage(talkMsg, room);
    }
}
