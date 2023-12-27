package com.instargram.instargram.Member.Controller;

import com.instargram.instargram.Community.Board.Service.BoardService;
import com.instargram.instargram.Community.Board.Service.Board_Data_MapService;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Member.Config.OAuth2.Model.OAuth2UserInfo;
import com.instargram.instargram.Member.Model.DTO.UserPageDTO;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Model.Form.MemberCreateForm;
import com.instargram.instargram.Member.Service.FollowMapService;
import com.instargram.instargram.Member.Service.MemberService;
import com.instargram.instargram.Story.Service.StoryHighlightMapService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import lombok.Builder;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@Builder
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final FollowMapService followMapService;
    private final StoryHighlightMapService storyHighlightMapService;
    private final Board_Data_MapService dataMapService;
    private final ImageService imageService;

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

        return "Member/SNS_Signup_form";
    }

    @PostMapping("/signup/social")
    public String signup(MemberCreateForm memberCreateForm, BindingResult bindingResult) {

        memberService.create(memberCreateForm);
        return "redirect:/member/login";
    }

    @PostMapping("/profile/upload")
    public String ProfileImageUpload(@RequestParam("profile-photo-input") MultipartFile multipartFile,
                                     Principal principal) throws IOException, NoSuchAlgorithmException {

        Member member = memberService.getMember(principal.getName());

        if (!multipartFile.isEmpty()) {
            String currName = multipartFile.getOriginalFilename();
            assert currName != null;

            int lastDotIndex = currName.lastIndexOf('.');
            String nameWithoutExtension = currName;

            if (lastDotIndex != -1) {
                nameWithoutExtension = currName.substring(0, lastDotIndex);
            }

            String[] type = Objects.requireNonNull(multipartFile.getContentType()).split("/");
            if (!type[type.length - 1].equals("octet-stream")) {
                String fileExtension = type[type.length - 1];
                Image image = this.imageService.memberImageChange(memberService.getMember(principal.getName()).getImage()
                        ,multipartFile, nameWithoutExtension, fileExtension);

                if(image != null)
                {
                    imageService.deleteImage(memberService.ProfileImageUpload(member, image));
                }
            }
        }

        return "redirect:/member/page/"+member.getUsername();
    }

    @GetMapping("/page/{username}")
    public String userPage(@PathVariable("username")String username, Model model, Principal principal)
    {
        Member member = memberService.getMember(username);
        Member loginMember = memberService.getMember(principal.getName());

        UserPageDTO userPageDTO = new UserPageDTO(member, loginMember, boardService, followMapService, storyHighlightMapService, dataMapService);

        model.addAttribute("member", member);
        model.addAttribute("userPageDTO", userPageDTO);

        return "Member/UserPage_form";
    }


    @PostMapping("/profile/delete")
    public String ProfileImageDelete(Principal principal)
    {
        Member member = memberService.getMember(principal.getName());

        imageService.deleteImage(memberService.ProfileImageDelete(member));

        return "redirect:/member/page/"+member.getUsername();
    }

    @GetMapping("/follow/{username}")
    public ResponseEntity<Map<String, Object>> follow(@PathVariable("username")String username, Principal principal)
    {
        Map<String, Object> result = new HashMap<>();
        Member member = memberService.getMember(principal.getName());
        Member target =  memberService.getMember(username);

        if(memberService.UserFollow(member, target))
        {
            result.put("result", true);
        }
        else{
            result.put("result", false);
        }

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/account/{menu}")
    public String accountEdit(@PathVariable("menu")String menu, Model model)
    {
        model.addAttribute("menu", menu);
        return "Member/AccountEdit_form";
    }
}
