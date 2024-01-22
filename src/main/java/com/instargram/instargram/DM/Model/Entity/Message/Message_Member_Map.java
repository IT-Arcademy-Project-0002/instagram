package com.instargram.instargram.DM.Model.Entity.Message;

import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Message_Member_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 메세지 생성날짜
    private LocalDateTime createDate;

    // 데이터 타입 ( 1 : 텍스트, 2 : 이미지, 3 : 비디오)
    private Integer dataType;

    // 데이터 아이디 : 데이터 타입에 따라 따로 처리 필요
    private Long dataId;

    // 감정 (null 일 수 있음)
    @OneToMany(mappedBy = "map")
    private List<Emoji> emojiList;

    // 댓글 여부 (데이터 타입이 1이면서 댓글 여부가 참일 때 코멘트)
    private boolean comment;

    @ManyToOne
    @JoinColumn(name="room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;

    // 메세지를 보낸 회원
    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    //메세지를 읽은 멤버
    private String seeMember;
}
