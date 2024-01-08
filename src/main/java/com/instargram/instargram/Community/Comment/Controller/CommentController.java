package com.instargram.instargram.Community.Comment.Controller;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Service.BoardService;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Community.Comment.Model.Form.CommentCreateForm;
import com.instargram.instargram.Community.Comment.Service.CommentService;
import com.instargram.instargram.Community.Comment.Service.Comment_Like_MapService;
import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import com.instargram.instargram.Notice.Model.Entitiy.Notice;
import com.instargram.instargram.Notice.Service.NoticeCommentMapService;
import com.instargram.instargram.Notice.Service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final MemberService memberService;
    private final Comment_Like_MapService commentLikeMapService;
//    private final NoticeService noticeService;
//    private final NoticeCommentMapService noticeCommentMapService;

    //댓글 작성
    @PostMapping("/create/{id}")
    public String create(@PathVariable("id") Long id, CommentCreateForm commentCreateForm,
                         BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            return "redirect:/main";
        }
        Member member = this.memberService.getMember(principal.getName());
        Board board = this.boardService.getBoardById(id);
        if (member != null && board != null) {
            Comment comment = commentService.create(member, board, commentCreateForm.getContent());
//            Notice notice = noticeService.createNotice(Enum_Data.BOARD_COMMENT.getNumber(), member, board.getMember());
//            noticeCommentMapService.createNoticeComment(comment, notice);
        }
        return "redirect:/main";
    }
    
    // 댓글 좋아요
    @GetMapping("/like/{id}")
    public String like(@PathVariable("id") Long id, Principal principal, Model model) {
        Comment comment = this.commentService.getCommentById(id);
        Member member = this.memberService.getMember(principal.getName());

        Comment_Like_Map isCommentMemberLiked = this.commentLikeMapService.exists(comment, member);
        if (isCommentMemberLiked == null) {
            this.commentLikeMapService.create(comment, member);
//            this.noticeService.createNotice(Enum_Data.COMMENT_LIKE.getNumber(), member,comment.getMember());
        }else{
            this.commentLikeMapService.delete(isCommentMemberLiked);
        }
        return "redirect:/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        Comment comment = this.commentService.getCommentById(id);
        commentService.delete(comment);
        return "redirect:/main";
    }
}
