package com.instargram.instargram;

import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Service.RoomService;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@Builder
public class mainController {
    @GetMapping("/")
    public String mainUrl(){
        return "redirect:/main";
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
