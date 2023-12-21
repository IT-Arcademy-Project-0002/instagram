package com.instargram.instargram.Search.Controller;

import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Builder
@RequestMapping("/search")
public class SearchController {

    @GetMapping("")
    public String searchUrl(){
        return "Search/search";
    }

}
