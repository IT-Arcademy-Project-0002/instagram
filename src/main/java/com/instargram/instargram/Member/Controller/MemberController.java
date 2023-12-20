package com.instargram.instargram.Member.Controller;

import com.instargram.instargram.Member.Config.OAuth2.Model.OAuth2UserInfo;
import com.instargram.instargram.Member.Model.Form.MemberCreateForm;
import com.instargram.instargram.Member.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Builder
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login()
    {
        return "Member/Login_form";
    }

    @GetMapping("/signup")
    public String signup(MemberCreateForm memberCreateForm)
    {
        return "Member/Signup_form";
    }

    @PostMapping("/signup")
    public String string(MemberCreateForm memberCreateForm)
    {
        memberService.create(memberCreateForm);
        return "redirect:/member/login";
    }

    @GetMapping("/signup/social")
    public String signup(MemberCreateForm memberCreateForm,
                         HttpServletRequest request) {
        HttpSession session = request.getSession();
        OAuth2UserInfo socialLogin = (OAuth2UserInfo) session.getAttribute("SOCIAL_LOGIN");

        if (socialLogin != null) {
            memberCreateForm.setName(socialLogin.getName());
            memberCreateForm.setEmail(socialLogin.getEmail());
            memberCreateForm.setProvider(socialLogin.getProvider());
            memberCreateForm.setProviderID(socialLogin.getProviderId());
        }

        return "Member/Signup_form";
    }
}
