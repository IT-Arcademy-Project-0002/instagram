package com.instargram.instargram.Community.Comment.Controller;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.BoardLikeMemberMap;
import com.instargram.instargram.Community.Board.Service.BoardService;
import com.instargram.instargram.Community.Comment.Model.DTO.CommentDTO;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Community.Comment.Model.Form.CommentCreateForm;
import com.instargram.instargram.Community.Comment.Service.CommentService;
import com.instargram.instargram.Community.Comment.Service.Comment_Like_MapService;
import com.instargram.instargram.Community.Recomment.Service.RecommentService;
import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;

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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final MemberService memberService;
    private final Comment_Like_MapService commentLikeMapService;
    private final NoticeService noticeService;
    private final NoticeCommentMapService noticeCommentMapService;
    private final RecommentService recommentService;

    //댓글 작성
    @PostMapping("/create/{id}")
    public String create(@PathVariable("id") Long id, CommentCreateForm commentCreateForm,
                         BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/main";
        }
        Member member = this.memberService.getMember(principal.getName());
        Board board = this.boardService.getBoardById(id);
        if (member != null && board != null) {
            Comment comment = commentService.create(member, board, commentCreateForm.getContent());
            if (member != board.getMember()) {
                Notice notice = noticeService.createNotice(Enum_Data.BOARD_COMMENT.getNumber(), member, board.getMember());
                noticeCommentMapService.createNoticeComment(comment, notice);
            }
        }
        return "redirect:/main";
    }

    @PostMapping("/modalCreate/{id}")
    public ResponseEntity<Map<String, Object>> modalCreate(@PathVariable("id") Long id, @RequestBody CommentCreateForm commentCreateForm,
                                                           BindingResult bindingResult, Principal principal) {
        Map<String, Object> result = new HashMap<>();

        if (bindingResult.hasErrors()) {
            result.put("result", false);
            return ResponseEntity.badRequest().body(result);
        }

        Member member = this.memberService.getMember(principal.getName());
        Board board = this.boardService.getBoardById(id);

        if (member != null && board != null) {
            Comment comment = commentService.create(member, board, commentCreateForm.getContent());
            Notice notice = noticeService.createNotice(Enum_Data.BOARD_COMMENT.getNumber(), member, board.getMember());
            noticeCommentMapService.createNoticeComment(comment, notice);

            // 서버에서 날짜를 원하는 형식으로 포맷
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월dd일 HH:mm");
            String formattedDate = comment.getCreateDate().format(formatter);

            // 포맷된 날짜를 클라이언트에 전달
            result.put("formattedDate", formattedDate);
            result.put("comment", new CommentDTO(comment));
        } else {
            result.put("result", false);
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok().body(result);
    }


    // 댓글 좋아요
    @GetMapping("/like/{id}")
    public ResponseEntity<Map<String, Object>> like(@PathVariable("id") Long id, Principal principal) {
        Map<String, Object> result = new HashMap<>();

        Comment comment = this.commentService.getCommentById(id);
        Member member = this.memberService.getMember(principal.getName());

        Comment_Like_Map isCommentMemberLiked = this.commentLikeMapService.exists(comment, member);

        if (isCommentMemberLiked == null) {
            Comment_Like_Map commentLikeMap = this.commentLikeMapService.create(comment, member);
            if (member != comment.getMember()) {
                Notice notice = this.noticeService.createNotice(Enum_Data.COMMENT_LIKE.getNumber(), member, comment.getMember());
                noticeCommentMapService.createNoticeCommentLike(commentLikeMap, notice);
            }

            int commentLikeCount = this.commentLikeMapService.countLikesForComment(comment);
            result.put("result", true);
            result.put("commentLikeCount", commentLikeCount);
        } else {
            this.commentLikeMapService.delete(isCommentMemberLiked);

            int commentLikeCount = this.commentLikeMapService.countLikesForComment(comment);
            result.put("result", false);
            result.put("commentLikeCount", commentLikeCount);
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        Comment comment = this.commentService.getCommentById(id);
        commentService.delete(comment);
        return "redirect:/main";
    }

    @GetMapping("/pin/{id}")
    public String commentPin(@PathVariable("id") Long id) {
        commentService.PinStateChange(id);
        return "redirect:/main";
    }

}
