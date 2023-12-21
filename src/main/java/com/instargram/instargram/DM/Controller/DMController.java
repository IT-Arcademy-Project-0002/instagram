package com.instargram.instargram.DM.Controller;

import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Builder
@RequestMapping("/dm")
public class DMController {
    @GetMapping("")
    public String dmUrl(){
        return "Dm/dm";
    }
}
