package com.instargram.instargram.DM.Controller;

import com.instargram.instargram.DM.Service.RoomService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
}
