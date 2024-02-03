package com.instargram.instargram.Notice.Controller;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import com.instargram.instargram.Notice.Model.DTO.NoticeAlertDTO;
import com.instargram.instargram.Notice.Model.Entity.Notice;
import com.instargram.instargram.Notice.Service.NoticeSSEService;

import com.instargram.instargram.Notice.Service.NoticeService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final NoticeSSEService noticeSSEService;
    private final MemberService memberService;

    @PostMapping("/checking")
    @ResponseBody
    public void noticeChecking(Principal principal) {

        Member member = this.memberService.getMemberByUsername(principal.getName());
        this.noticeService.noticeChecking(member, Arrays.asList(10, 11));

    }

    @PostMapping("/checkingDM")
    @ResponseBody
    public void noticeDMChecking(Principal principal) {

        Member member = this.memberService.getMemberByUsername(principal.getName());
        this.noticeService.noticeDMChecking(member, Arrays.asList(10, 11));

    }

    @GetMapping("/alert")
    public ResponseEntity<List<NoticeAlertDTO>> noticeAlert(Principal principal) {

        List<NoticeAlertDTO> noticeAlertDTOList = new ArrayList<>();

        Member member = this.memberService.getMemberByUsername(principal.getName());
        List<Notice> noticeList =  this.noticeService.checkNoticeList(member);

        for (Notice notice : noticeList) {
            NoticeAlertDTO noticeAlertDTO = new NoticeAlertDTO();

            noticeAlertDTO.setId(notice.getId());
            noticeAlertDTO.setCreateDate(notice.getCreateDate());
            noticeAlertDTO.setType(notice.getType());

            noticeAlertDTOList.add(noticeAlertDTO);
        }

        return ResponseEntity.ok().body(noticeAlertDTOList);
    }

    @GetMapping("/alertSSE")
    public SseEmitter initSSE(Authentication authentication) {

        // principal이 null이 아니고, MemberService에서 사용자 정보를 가져올 수 있는 경우에만 처리합니다.
        if (authentication != null && authentication.getPrincipal() != null) {
            String username = authentication.getName();
            Member member = this.memberService.getMember(username);

            // 사용자 정보를 통해 새로운 SseEmitter를 생성하여 추가합니다.
            SseEmitter emitter = this.noticeSSEService.subscribe();
            this.noticeSSEService.addEmitter(member.getId(), emitter);

            // SSE 연결이 종료될 때 해당 emitter를 제거합니다.
            emitter.onCompletion(() -> this.noticeSSEService.removeEmitter(member.getId(), emitter));

            // SSE 연결이 에러가 발생할 때 해당 emitter를 제거합니다.
            emitter.onError((ex) -> this.noticeSSEService.removeEmitter(member.getId(), emitter));

            // emitter를 반환하여 클라이언트에게 연결을 제공합니다.
            return emitter;
        }
        return null;
    }
}
