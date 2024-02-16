package com.instargram.instargram.Member.Controller;

import com.instargram.instargram.Community.Board.Model.DTO.FeedDTO;
import com.instargram.instargram.Community.Board.Model.DTO.SavedBoardDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Save_Map;
import com.instargram.instargram.Community.Board.Service.BoardService;
import com.instargram.instargram.Community.Board.Service.Board_Data_MapService;
import com.instargram.instargram.Community.Board.Service.Board_Save_MapService;
import com.instargram.instargram.Community.SaveGroup.Service.SaveGroupService;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Member.Config.OAuth2.Model.OAuth2UserInfo;
import com.instargram.instargram.Member.Config.SpringSecurity.MemberSecurityService;
import com.instargram.instargram.Member.Config.SpringSecurity.PrincipalDetails;
import com.instargram.instargram.Member.Model.DTO.UserPageDTO;
import com.instargram.instargram.Member.Model.Entity.Hate_Member_Map;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Model.Form.MemberCreateForm;
import com.instargram.instargram.Member.Service.FollowMapService;
import com.instargram.instargram.Member.Service.MemberService;
import com.instargram.instargram.Story.Service.StoryHighlightMapService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import jakarta.websocket.server.PathParam;
import lombok.Builder;

import lombok.Getter;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.*;

@Controller
@Builder
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final FollowMapService followMapService;
    private final StoryHighlightMapService storyHighlightMapService;
    private final Board_Data_MapService boardDataMapService;
    private final ImageService imageService;
    private final Board_Save_MapService boardSaveMapService;
    private final SaveGroupService saveGroupService;
    private final AuthenticationManager authenticationManager;

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
                                     @RequestParam(value = "account", defaultValue = "false") boolean account,
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

        if(account)
        {
            return "redirect:/member/account/edit";
        }
        else
        {
            return "redirect:/member/page/"+member.getUsername();
        }
    }

    @GetMapping(value = {"/page/{username}/{page-type}", "/page/{username}"})
    public String userPage(@PathVariable("username")String username,
                           @PathVariable(value = "page-type", required = false) Optional<String> pageType,
                           @RequestParam(value = "id", defaultValue = "-1")Long id,
                           @RequestParam(value = "page", defaultValue = "default")String referer,
                           @RequestParam(value = "scroll", defaultValue = "0")double scroll,
                           Model model, Principal principal)
    {
        String pageTypeValue = pageType.orElse("default");

        model.addAttribute("pageType", pageTypeValue);

        if(pageTypeValue.equals("default"))
        {
            if(referer.equals("detail"))
            {
                Board board = this.boardService.getBoardById(id);

                FeedDTO selectFeed = this.boardDataMapService.getFeedWithComments(board);
                model.addAttribute("selectFeed", selectFeed);
            }

            model.addAttribute("referer", referer);
            model.addAttribute("scroll", scroll);
        }


        Member member = memberService.getMember(username);
        Member loginMember = memberService.getMember(principal.getName());

        model.addAttribute("member", member);

        if(memberService.isBlock(username, principal.getName()))
        {
            model.addAttribute("blocked", true);
        }
        else{
            model.addAttribute("blocked", false);
            model.addAttribute("blocking", memberService.isBlock(principal.getName(), username));
            UserPageDTO userPageDTO = new UserPageDTO(member, loginMember, boardService, memberService.getFollowMapService(), memberService.getStoryHighlightMapService(), boardDataMapService);
            model.addAttribute("userPageDTO", userPageDTO);
        }


        if(pageTypeValue.equals("saved"))
        {
            if (member.getUsername().equals(principal.getName())) {
                SavedBoardDTO savedBoardDTO = new SavedBoardDTO(member, loginMember,
                        memberService.getFollowMapService(), memberService.getStoryHighlightMapService(),
                        boardDataMapService, boardSaveMapService);
                model.addAttribute("savedBoardDTO", savedBoardDTO);
            }
        }


        return "Member/UserPage_form";
    }


//    @GetMapping("/page/{username}/saved")
//            public String SavedPage(@PathVariable("username")String username, Principal principal, Model model)
//    {
//        Member member = memberService.getMember(username);
//        Member loginMember = memberService.getMember(principal.getName());
//
//
//        model.addAttribute("member", member);
//
//        if(memberService.isBlock(username, principal.getName()))
//        {
//            model.addAttribute("blocked", true);
//        }
//        else{
//            model.addAttribute("blocked", false);
//            model.addAttribute("blocking", memberService.isBlock(principal.getName(), username));
//            UserPageDTO userPageDTO = new UserPageDTO(member, loginMember, boardService, memberService.getFollowMapService(), memberService.getStoryHighlightMapService(), boardDataMapService);
//            model.addAttribute("userPageDTO", userPageDTO);
//        }
//
//        if(member.getUsername().equals(principal.getName()))
//        {
//            SavedBoardDTO savedBoardDTO = new SavedBoardDTO(member, loginMember,
//                    memberService.getFollowMapService(), memberService.getStoryHighlightMapService(),
//                    boardDataMapService, boardSaveMapService);
//            model.addAttribute("savedBoardDTO", savedBoardDTO);
//        }
//
//        return "Member/UserSaved_form";
//    }

    @PostMapping("/profile/delete")
    public String ProfileImageDelete(Principal principal, @RequestParam(value = "account", defaultValue = "false") boolean account)
    {
        Member member = memberService.getMember(principal.getName());

        imageService.deleteImage(memberService.ProfileImageDelete(member));


        if(account)
        {
            return "redirect:/member/account/edit";
        }
        else
        {
            return "redirect:/member/page/"+member.getUsername();
        }
    }

    @GetMapping("/follow/{username}")
    public ResponseEntity<Map<String, Object>> follow(@PathVariable("username")String username, Principal principal)
    {
        Map<String, Object> result = new HashMap<>();
        Member member = memberService.getMember(principal.getName());
        Member target =  memberService.getMember(username);

        result.put("result", memberService.UserFollow(member, target));

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/follow/deleteByNotice/{id}")
    public ResponseEntity<Map<String, Object>> followDeleteByNotice(@PathVariable("id")Long id)
    {
        // 공개, 비공개에 상관없이 팔로잉 상태에서 버튼을 눌렀을때 무조건 팔로우를 취소하는 콘트롤러가 필요함 (알림창 기준, 알림 id를 이용)
        Map<String, Object> result = new HashMap<>();
        this.memberService.followDeleteByNotice(id);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/follow/delete/{username}")
    public ResponseEntity<Map<String, Object>> followDelete(@PathVariable("username")String username, Principal principal)
    {
        // 공개, 비공개에 상관없이 팔로잉 상태에서 버튼을 눌렀을때 무조건 팔로우를 취소하는 콘트롤러가 필요함 (마이페이지 기준, username을 이용)
        Map<String, Object> result = new HashMap<>();
        Member loginMember = this.memberService.getMemberByUsername(principal.getName());
        Member targetMember = this.memberService.getMemberByUsername(username);

        this.memberService.followDelete(loginMember, targetMember);

        return ResponseEntity.ok().body(result);
    }


    @GetMapping("/requestFollow/{username}")
    public ResponseEntity<Map<String, Object>> requestFollow(@PathVariable("username")String username, Principal principal)
    {
        Map<String, Object> result = new HashMap<>();
        Member member = memberService.getMember(principal.getName());
        Member target =  memberService.getMember(username);

        result.put("result", memberService.RequestFollowApply(member, target));

        result.put("followState", memberService.isFollow(member, target));

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/requestFollow/delete/{id}")
    public ResponseEntity<Map<String, Object>> requestFollowDelete(@PathVariable("id")Long id, Principal principal)
    {
        Map<String, Object> result = new HashMap<>();
        memberService. RequestFollowDelete(id);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/account/{menu}")
    public String accountEdit(@PathVariable("menu")String menu, Model model)
    {
        model.addAttribute("menu", menu);
        return "Member/AccountEdit_form";
    }

    @GetMapping("/changeProfile")
    public String changeProfile(@RequestParam(value = "now-sex")String sex, @RequestParam(value = "now-introduce")String introduce,
                                Principal principal)
    {
        if(sex.isEmpty())
        {
            sex = null;
        }

        if(introduce.isEmpty())
        {
            introduce = null;
        }

        memberService.changeProfile(principal.getName(), sex, introduce);
        return "redirect:/member/account/edit";
    }

    @GetMapping("/changeName")
    public String changeName(@RequestParam("new-name")String name, Principal principal)
    {
        memberService.changeName(principal.getName(), name);
        return "redirect:/member/account/edit";
    }

    @GetMapping("/changeUsername")
    public String changeUsername(@RequestParam("new-username")String username, Principal principal,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails){
        Member member = memberService.changeUsername(principal.getName(), username);

        principalDetails.setMember(member);
        return "redirect:/member/account/edit";
    }

    @GetMapping("/duplicUserName/{username}")
    public ResponseEntity<Map<String, Object>> duplicUserName(@PathVariable("username")String username)
    {
        Map<String, Object> result = new HashMap<>();

        result.put("result", memberService.duplicUserName(username));

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/privacySetting")
    public String privacySetting(@RequestParam(value = "privacySetting-input", defaultValue = "false")boolean checked, Principal principal)
    {
        memberService.changeScope(principal.getName(), !checked);
        return  "redirect:/member/account/privacy_setting";
    }

    @GetMapping("/search/{kw}")
    public ResponseEntity<Map<String, List<Member>>> memberSearch(@PathVariable("kw") String kw, Principal principal)
    {
        Map<String, List<Member>> result = new HashMap<>();

        result.put("result", memberService.searchMemberList(kw));

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/block/{target}")
    public ResponseEntity<Map<String, Object>> blockMember(@PathVariable("target")String target, Principal principal)
    {
        Map<String, Object> result = new HashMap<>();

        result.put("result", memberService.blockMember(principal.getName(), target));

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/block/cancel/{target}")
    public ResponseEntity<Map<String, Object>> blockCancelMember(@PathVariable("target")String target, Principal principal)
    {
        Map<String, Object> result = new HashMap<>();

        memberService.blockCancelMember(principal.getName(), target);

        result.put("result", "blockCancel");

        return ResponseEntity.ok().body(result);
    }

}
