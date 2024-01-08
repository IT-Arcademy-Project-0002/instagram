package com.instargram.instargram.DM.Model.Entity.Message;

import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member requestMemberId;

    // 디엠을 요청 받은 회원
    @ManyToOne
    @JoinColumn(name="response_member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member responseMemberId;
}
