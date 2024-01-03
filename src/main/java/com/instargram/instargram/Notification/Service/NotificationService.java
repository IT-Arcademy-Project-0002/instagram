package com.instargram.instargram.Notification.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationService {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final Map<Long, SseEmitter> userEmitters = new ConcurrentHashMap<>();

    // 새로운 emitter 객체를 생성
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        return emitter;
    }

    // 회원별 emitter 객체를 회원번호(PK)와 매핑하여 해시맵으로 저장

    public void addEmitter(Long userId, SseEmitter emitter) {
        userEmitters.put(userId, emitter);
        emitter.onCompletion(() -> userEmitters.remove(userId, emitter));
    }

    // 회원별 알림
    public void notifyUser(Long userId, String message) {
        SseEmitter emitter = userEmitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (IOException e) {
                userEmitters.remove(userId, emitter);
            }
        }
    }

    public void removeEmitter(Long userId, SseEmitter emitter) {
        // 주어진 SseEmitter가 현재 등록되어 있는 emitter인지 확인합니다.
        if (userEmitters.get(userId) == emitter) {
            // SseEmitter가 존재하면 완료 이벤트를 수신하는 콜백을 호출하여 제거합니다.
            emitter.complete();
            // 사용자 맵에서 해당 사용자의 SseEmitter를 제거합니다.
            userEmitters.remove(userId);
        }
    }
}
