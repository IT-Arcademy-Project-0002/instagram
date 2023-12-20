package com.instargram.instargram;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class mainController {

    @GetMapping("/test")
    public String test(){
        return "Member/Login_form";
    }

    @GetMapping("/")
    public String mainUrl(){
        return "Member/Login_form";
    }
    
    @GetMapping("/main")
    public String create(){
        return "Board/board_create";
    }
}
