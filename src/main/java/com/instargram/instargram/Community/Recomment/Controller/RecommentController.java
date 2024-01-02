package com.instargram.instargram.Community.Recomment.Controller;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Service.CommentService;
import com.instargram.instargram.Community.Recomment.Model.Entity.ReComment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
import com.instargram.instargram.Community.Recomment.Model.Form.RecommentCreateForm;
import com.instargram.instargram.Community.Recomment.Service.RecommentService;
import com.instargram.instargram.Community.Recomment.Service.Recomment_Like_MapService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recomment")
public class RecommentController {
    private final MemberService memberService;
    private final CommentService commentService;
    private final RecommentService recommentService;
    private final Recomment_Like_MapService recommentLikeMapService;
    @PostMapping("/create/{recommentId}")
    public String create(@PathVariable("recommentId") Long id, RecommentCreateForm recommentCreateForm, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            return "redirect:/main";
        }
        Member member = this.memberService.getMember(principal.getName());
        System.out.println("1. " + member);
        Comment comment = this.commentService.getCommentById(id);
        System.out.println("2. " + comment);
        if (member != null && comment != null) {
            recommentService.create(member, comment, recommentCreateForm.getContent());
        }
        return "redirect:/main";
    }

    @GetMapping("/like/{id}")
    public String like(@PathVariable("id") Long id, Principal principal, Model model) {
        Recomment recomment = this.recommentService.getRecommentById(id);
        Member member = this.memberService.getMember(principal.getName());

        ReComment_Like_Map isRecommentMemberLiked = this.recommentLikeMapService.exists(recomment, member);
        if (isRecommentMemberLiked == null) {
            this.recommentLikeMapService.create(recomment, member);
        }else{
            this.recommentLikeMapService.delete(isRecommentMemberLiked);
        }
        return "redirect:/main";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        Recomment recomment = this.recommentService.getRecommentById(id);
        recommentService.delete(recomment);
        return "redirect:/main";
    }
}
