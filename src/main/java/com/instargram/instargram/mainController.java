package com.instargram.instargram;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {

    @GetMapping("/")
    public String mainUrl(){
        return "redirect:/main";
    }

    @GetMapping("/search")
    public String searchUrl(){
        return "Search/search";
    }

    @GetMapping("/dm")
    public String dmUrl(){
        return "Dm/dm";
    }
}
