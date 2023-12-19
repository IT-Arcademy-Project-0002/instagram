package com.instargram.instargram.DM.Message;

import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Message_Member_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 데이터 타입 ( 1 : 텍스트, 2 : 이미지, 3 : 비디오)
    private Integer data_type;

    // 데이터 아이디 : 데이터 타입에 따라 따로 처리 필요
    private Long data_id;

    // 감정 (null 일 수 있음)
    private String Empathy;

    // 메세지를 보낸 회원
    @ManyToOne
    private Member member;
}
