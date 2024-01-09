package com.instargram.instargram.DM.Controller;

import com.instargram.instargram.DM.Service.MessageService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@Builder
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(
            @RequestBody Map<String, Object> talkMsg)
    {
        Map<String, Object> result = new HashMap<>();

        messageService.create(talkMsg);
        return ResponseEntity.ok(result);
    }
}
