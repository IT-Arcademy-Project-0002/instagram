package com.instargram.instargram.Community.Recomment.Controller;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.BoardLikeMemberMap;
import com.instargram.instargram.Community.Comment.Model.DTO.CommentDTO;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Service.CommentService;
import com.instargram.instargram.Community.Recomment.Model.Entity.ReComment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
import com.instargram.instargram.Community.Recomment.Model.Form.RecommentCreateForm;
import com.instargram.instargram.Community.Recomment.Service.RecommentService;
import com.instargram.instargram.Community.Recomment.Service.Recomment_Like_MapService;
import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
//import com.instargram.instargram.Notice.Service.NoticeService;
import com.instargram.instargram.Notice.Model.Entity.Notice;
import com.instargram.instargram.Notice.Service.NoticeCommentMapService;
import com.instargram.instargram.Notice.Service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recomment")
public class RecommentController {
    private final MemberService memberService;
    private final CommentService commentService;
    private final RecommentService recommentService;
    private final Recomment_Like_MapService recommentLikeMapService;
    private final NoticeService noticeService;
    private final NoticeCommentMapService noticeCommentMapService;

//    @PostMapping("/create/{id}")
//    public String create(@PathVariable("id") Long id, RecommentCreateForm recommentCreateForm, BindingResult bindingResult, Principal principal){
//        if (bindingResult.hasErrors()) {
//            return "redirect:/main";
//        }
//        Member member = this.memberService.getMember(principal.getName());
//        Comment comment = this.commentService.getCommentById(id);
//        if (member != null && comment != null) {
//            Recomment recomment = recommentService.create(member, comment, recommentCreateForm.getContent());
//            Notice notice = this.noticeService.createNotice(Enum_Data.COMMENT_RECOMMENT.getNumber(), member, comment.getMember());
//            noticeCommentMapService.createNoticeRecomment(recomment, notice);
//            // 대댓글에서 TagMember를 언급할때 실제 회원이 존재한다면(tagMember != null) 알림을 보내는 비즈니스 로직임
//            Member tagMember = noticeCommentMapService.createTagMember(recommentCreateForm.getContent());
//            if (tagMember != null) {
//                Notice noticeForTabMember = this.noticeService.createRecommentTagMemberNotice(Enum_Data.COMMENT_RECOMMENT.getNumber(), member, tagMember);
//                noticeCommentMapService.createNoticeRecomment(recomment, noticeForTabMember);
//            }
//        }
//        return "redirect:/main";
//    }

    @PostMapping("/recomment/create/{id}")
    public ResponseEntity<Map<String, Object>> create(@PathVariable("id") Long id, RecommentCreateForm recommentCreateForm,  BindingResult bindingResult, Principal principal){
        Map<String, Object> result = new HashMap<>();
        if (bindingResult.hasErrors()) {
            result.put("result", false);
        }
        Member member = this.memberService.getMember(principal.getName());
        Comment comment = this.commentService.getCommentById(id);
        if (member != null && comment != null) {
            Recomment recomment = recommentService.create(member, comment, recommentCreateForm.getContent());
            Notice notice = this.noticeService.createNotice(Enum_Data.COMMENT_RECOMMENT.getNumber(), member, comment.getMember());
            noticeCommentMapService.createNoticeRecomment(recomment, notice);
            // 대댓글에서 TagMember를 언급할때 실제 회원이 존재한다면(tagMember != null) 알림을 보내는 비즈니스 로직임
            Member tagMember = noticeCommentMapService.createTagMember(recommentCreateForm.getContent());
            if (tagMember != null) {
                Notice noticeForTabMember = this.noticeService.createRecommentTagMemberNotice(Enum_Data.COMMENT_RECOMMENT.getNumber(), member, tagMember);
                noticeCommentMapService.createNoticeRecomment(recomment, noticeForTabMember);
            }

            result.put("recomment",new CommentDTO(comment));
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<Map<String, Object>> like(@PathVariable("id") Long id, Principal principal) {
        Map<String, Object> result = new HashMap<>();

        Recomment recomment = this.recommentService.getRecommentById(id);
        Member member = this.memberService.getMember(principal.getName());

        ReComment_Like_Map isRecommentMemberLiked = this.recommentLikeMapService.exists(recomment, member);

        if (isRecommentMemberLiked == null) {
            ReComment_Like_Map reCommentLikeMap = this.recommentLikeMapService.create(recomment, member);
            Notice notice = this.noticeService.createNotice(Enum_Data.RECOMMENT_LIKE.getNumber(), member,recomment.getMember());
            noticeCommentMapService.createNoticeRecommentLike(reCommentLikeMap, notice);

            int recommentLikeCount = this.recommentLikeMapService.countLikesForReComment(recomment);
            result.put("result", true);
            result.put("recommentLikeCount", recommentLikeCount);
        } else {
            this.recommentLikeMapService.delete(isRecommentMemberLiked);

            int recommentLikeCount = this.recommentLikeMapService.countLikesForReComment(recomment);
            result.put("result", false);
            result.put("recommentLikeCount", recommentLikeCount);
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        Recomment recomment = this.recommentService.getRecommentById(id);
        recommentService.delete(recomment);
        System.out.println("gggggggggggggggggggggggggggggggg");
        return "redirect:/main";
    }
}
