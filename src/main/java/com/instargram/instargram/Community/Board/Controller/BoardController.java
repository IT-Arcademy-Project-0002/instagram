package com.instargram.instargram.Community.Board.Controller;

import com.instargram.instargram.Community.Board.Form.BoardCreateForm;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Service.BoardService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final MemberService memberService;
    private final BoardService boardService;

    @PostMapping("/create")
    public String create(BoardCreateForm boardCreateForm, BindingResult bindingResult, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());

        if (bindingResult.hasErrors()) {
            return "Board/board_create";
        }

        this.boardService.create(member, boardCreateForm.getContent());
        return "redirect:/";
    }

}
