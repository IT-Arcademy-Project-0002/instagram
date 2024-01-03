package com.instargram.instargram.Notification.Controller;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import com.instargram.instargram.Notification.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NotificationController {

    private final NotificationService notificationService;
    private final MemberService memberService;

    @GetMapping("/alert")
    public SseEmitter initSSE(Authentication authentication) {

        // principal이 null이 아니고, MemberService에서 사용자 정보를 가져올 수 있는 경우에만 처리합니다.
        if (authentication != null && authentication.getPrincipal() != null) {
            String username = authentication.getName();
            Member member = this.memberService.getMember(username);

            // 사용자 정보를 통해 새로운 SseEmitter를 생성하여 추가합니다.
            SseEmitter emitter = this.notificationService.subscribe();
            this.notificationService.addEmitter(member.getId(), emitter);

            // SSE 연결이 종료될 때 해당 emitter를 제거합니다.
            emitter.onCompletion(() -> this.notificationService.removeEmitter(member.getId(), emitter));

            // SSE 연결이 에러가 발생할 때 해당 emitter를 제거합니다.
            emitter.onError((ex) -> this.notificationService.removeEmitter(member.getId(), emitter));

            // emitter를 반환하여 클라이언트에게 연결을 제공합니다.
            return emitter;
        }
        return null;
    }
}
