package com.instargram.instargram.Member.Model.Entity;


import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.BoardLikeMemberMap;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Save_Map;
import com.instargram.instargram.Community.Board.Model.Entity.Board_TagMember_Map;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.Entity.ReComment_Like_Map;
import com.instargram.instargram.DM.Model.Entity.Message.Message_Member_Map;
import com.instargram.instargram.DM.Model.Entity.Message.Message_Request_Map;
import com.instargram.instargram.DM.Model.Entity.Room.Room_Member_Map;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Notice.Model.Entity.Notice;
import com.instargram.instargram.Story.Model.Entity.Story_Data_Map;
import com.instargram.instargram.Story.Model.Entity.Story_Highlight_Map;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity

// 회원 테이블
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    // 회원 아이디
    @NotNull
    private String username;

    // 회원 비밀번호
    @NotNull
    private String password;

    // 회원 생성 날짜
    @NotNull
    private LocalDateTime createDate;

    // 회원 닉네임
    @NotNull
    private String nickname;

    // 회원 이메일
    @NotNull
    private String email;

    @OneToOne
    @JoinColumn(name="image_id")
    private Image image;

    // 회원 생년월일
    private LocalDate birthday;

    // 회원 핸드폰 번호
    private String phoneNumber;

    // 회원 소개 속 소개글
    private String introduction;

    // 회원 소개 속 url 정보
    private String url;

    // 회원 설명
    private String sex;

    // 계정 공개 범위(true : 공개 계정, false : 비공개 계정)
    private boolean scope;

    // 계정 현재 접속 상태 (true : 접속 중 : false : 접속하지 않음)
    private boolean connected;



    // sns 연동 로그인에 사용되는 provider 종류(GOOGLE, NAVER, KAKAO)
    private String provider;

    // sns 연동 로그인에 사용되는 provider ID
    private String providerId;
}
