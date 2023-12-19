package com.instargram.instargram.Member.Controller;

import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Builder
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/login")
    public String login()
    {
        return "Member/Login_form";
    }


}
