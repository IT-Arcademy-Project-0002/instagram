package com.instargram.instargram.Community.Board.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {
    @GetMapping("/create")
    public String create(){
        return "Board/board_create";
    }

    @PostMapping("/create")
    public String create(String content) {
        return "";
    }

}
