package com.instargram.instargram.DM.Controller;

import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Service.RoomService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Builder
@RequestMapping("/room")
public class RoomController {
    private RoomService roomService;
    private MemberService memberService;

    @GetMapping("/create")
    public String createRoom(Principal principal)
    {
        List<Member> members = new ArrayList<>();
        members.add(memberService.getMember("test1"));
        roomService.create(memberService.getMember(principal.getName()), members);
        return "redirect:/test/chat";
    }

    @PostMapping("/readMessage")
    public ResponseEntity<Map<String, Object>> readMessage(
            @RequestBody Map<String, Object> quitMsg)
    {
        Map<String, Object> result = new HashMap<>();

        roomService.readMessageState(quitMsg);
        return ResponseEntity.ok(result);
    }
}
