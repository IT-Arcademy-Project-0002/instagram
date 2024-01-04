package com.instargram.instargram.Config;

import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import com.instargram.instargram.Notice.Service.NoticeService;
import lombok.Builder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
@Builder
public class GlobalControllerAdvice {

    private final MemberService memberService;
    private final ImageService imageService;
    private final NoticeService noticeService;

    @ModelAttribute
    public void addAttributes(Principal principal, Model model) {
        // 여기에 모든 컨트롤러에 추가하고 싶은 attribute를 추가합니다

        if(principal != null)
        {

            Member member = memberService.getMember(principal.getName());
            model.addAttribute("notices", noticeService.getNoticeDTOsByMember(memberService.getFollowMapService(), member));

            if(member != null)
            {
                model.addAttribute("loginMember", member);
            }
            else{
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                var a = auth.getPrincipal();

                if(a != "anonymousUser")
                {
                    member = memberService.getMember(principal.getName());

                    if(member != null)
                    {
                        model.addAttribute("loginMember", member);
                    }
                }
            }
        }
    }
}