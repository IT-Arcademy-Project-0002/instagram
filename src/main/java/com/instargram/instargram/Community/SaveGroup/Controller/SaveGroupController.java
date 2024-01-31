package com.instargram.instargram.Community.SaveGroup.Controller;


import com.instargram.instargram.Community.SaveGroup.Service.SaveGroupService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Builder
@RequestMapping("/saveGroup")
public class SaveGroupController {
    private final SaveGroupService saveGroupService;
    private final MemberService memberService;
    @GetMapping("/create/{name}")
    public String saveGroupCreate(@PathVariable("name")String name, Principal principal)
    {
        Member member = memberService.getMember(principal.getName());
        saveGroupService.create(name, member);
        return "redirect:/member/page/"+principal.getName()+"/saved";
    }


}
