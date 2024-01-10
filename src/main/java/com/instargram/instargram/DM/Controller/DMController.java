package com.instargram.instargram.DM.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instargram.instargram.DM.Model.DTO.ChattingMemberDTO;
import com.instargram.instargram.DM.Model.DTO.RoomDTO;
import com.instargram.instargram.DM.Model.Entity.Message.Message_Member_Map;
import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Service.RoomService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.Builder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@Builder
@RequestMapping("/direct")
public class DMController {
    private final RoomService roomService;
    private final MemberService memberService;


    @GetMapping("")
    public String dmUrl(){
        return "Dm/dm";
    }

    @GetMapping("/inbox")
    public String dmList(Model model, Principal principal){

        List<Room> roomList = roomService.findByMember(memberService.getMember(principal.getName()));
        model.addAttribute("roomList",roomList);
        return "Dm/DirectInbox";
    }

    @GetMapping("/room")
    public String dmRoom(Model model, @RequestParam("id")Long id)
    {
        List<Long> a = new ArrayList<>();
        Room room = roomService.getRoom(id);
        model.addAttribute("room", room);
        return "testChat";
    }

    @GetMapping("/chatting")
    public String chatting(@RequestParam("member-search-input")String usernames,
                           Principal principal)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 배열을 List로 변환
            List<ChattingMemberDTO> userList = objectMapper.readValue(usernames, new TypeReference<List<ChattingMemberDTO>>() {});

            Member loginMember = memberService.getMember(principal.getName());

            List<Member> memberList = new ArrayList<>();
            for(ChattingMemberDTO dto : userList)
            {
                memberList.add(memberService.getMember(dto.getValue()));
            }

            Room room = roomService.findRoom(loginMember, memberList);

            if(room != null)
            {
                return "redirect:/direct/t/"+room.getId();
            }
            else{
                return "redirect:/direct/t/"+roomService.create(loginMember, memberList).getId();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/direct/create";
    }

    @GetMapping("/t/{id}")
    public String room(@PathVariable("id")Long id, Model model, Principal principal)
    {
        RoomDTO roomDTO = roomService.getRoomDTO(id);
        List<Message_Member_Map> messageList = roomService.getList(id);

        model.addAttribute("messageList", messageList);
        List<Room> roomList = roomService.findByMember(memberService.getMember(principal.getName()));
        model.addAttribute("roomList",roomList);
        model.addAttribute("nowRoomDTO",roomDTO);
        return "Dm/DirectRoom";
    }
}
