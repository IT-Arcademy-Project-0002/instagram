package com.instargram.instargram.Notification.Controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter handleSSE() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        // 클라이언트 연결 종료 시 emitters에서 제거 (로그아웃, 또는 페이지를 떠날 때)
        // SSE의 연결은 기본적으로 무기한으로 설정되어 있음
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    // 새로운 이벤트 발생시 알림
    public void alerttest() {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().data("New member has joined!"));
            } catch (IOException e) {
                // 처리 중 오류 발생 시 해당 연결을 삭제
                emitters.remove(emitter);
            }
        }
    }
}
