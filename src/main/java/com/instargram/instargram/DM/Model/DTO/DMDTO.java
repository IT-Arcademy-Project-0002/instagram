package com.instargram.instargram.DM.Model.DTO;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DMDTO {

    // 메시지 타입 : 입장, 채팅, 나감
    public enum MessageType {
        ENTER, TALK, QUIT
    }
    private MessageType type; // 메시지 타입
    private Long roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지

}