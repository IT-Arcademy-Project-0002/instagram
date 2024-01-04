package com.instargram.instargram.DM.Controller;

import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Service.RoomService;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.Builder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@Builder
@RequestMapping("/dm")
public class DMController {
    private final RoomService roomService;
    private final MemberService memberService;

    @GetMapping("")
    public String dmUrl(){
        return "Dm/dm";
    }

    @GetMapping("/list")
    public String dmList(Model model, Principal principal){

        List<Room> roomList = roomService.findByMember(memberService.getMember(principal.getName()));
        model.addAttribute("roomList",roomList);
        return "chatTest";
    }

    @GetMapping("/room")
    public String dmRoom(Model model, @RequestParam("id")Long id)
    {
        Room room = roomService.getRoom(id);
        model.addAttribute("room", room);
        return "testChat";
    }
}
