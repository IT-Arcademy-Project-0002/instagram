package com.instargram.instargram.Config;

import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import com.instargram.instargram.Notice.Model.DTO.NoticeDTO;
import com.instargram.instargram.Notice.Service.NoticeService;
import com.instargram.instargram.Search.Service.SearchService;
import lombok.Builder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Builder
public class GlobalControllerAdvice {

    private final MemberService memberService;
    private final ImageService imageService;
    private final NoticeService noticeService;
    private final SearchService searchService;

    @ModelAttribute
    public void addAttributes(Principal principal, Model model) {
        // 여기에 모든 컨트롤러에 추가하고 싶은 attribute를 추가합니다

        if(principal != null)
        {

            Member member = this.memberService.getMember(principal.getName());
            model.addAttribute("notices", this.noticeService.createNoticeList(member));
            model.addAttribute("noticesChecked", this.noticeService.checkNoticeList(member));
            model.addAttribute("searches", this.searchService.createSearchFavoriteList(member));


            if(member != null)
            {
                model.addAttribute("loginMember", member);
            }
            else{
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                if(auth.getPrincipal() != "anonymousUser")
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