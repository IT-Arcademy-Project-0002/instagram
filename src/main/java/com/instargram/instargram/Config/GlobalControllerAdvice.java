package com.instargram.instargram.Config;

import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.Builder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.Objects;

@ControllerAdvice
@Builder
public class GlobalControllerAdvice {

    private final MemberService memberService;
    private final ImageService imageService;

    @ModelAttribute
    public void addAttributes(Principal principal, Model model) {
        // 여기에 모든 컨트롤러에 추가하고 싶은 attribute를 추가합니다

        if(principal != null)
        {
            Member member = memberService.getMember(principal.getName());

            if(member != null)
            {
                model.addAttribute("loginMember", member);
            }
        }
    }
}