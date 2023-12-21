package com.instargram.instargram.Explorer.Controller;

import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Builder
@RequestMapping("/explore")
public class ExploreController {

    @GetMapping("")
    public String searchUrl(){
        return "Explore/explore";
    }

}
