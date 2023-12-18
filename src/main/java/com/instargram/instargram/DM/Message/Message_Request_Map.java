package com.instargram.instargram.DM.Message;

import com.instargram.instargram.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Message_Request_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 디엠을 요청한 회원
    @ManyToOne
    @JoinColumn(name="request_member_id")
    private Member request_member_id;

    // 디엠을 요청 받은 회원
    @ManyToOne
    @JoinColumn(name="response_member_id")
    private Member response_member_id;
}
