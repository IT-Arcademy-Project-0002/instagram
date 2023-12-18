package com.instargram.instargram.Member;

import com.instargram.instargram.Community.Board.Board;
import com.instargram.instargram.Community.Board.Board_Like_Member_Map;
import com.instargram.instargram.Community.Board.Board_Save_Map;
import com.instargram.instargram.Community.Board.Board_TagMember_Map;
import com.instargram.instargram.Community.Comment.Comment;
import com.instargram.instargram.Community.Comment.Comment_Like_Map;
import com.instargram.instargram.Community.Recomment.ReComment_Like_Map;
import com.instargram.instargram.DM.Message.Message_Member_Map;
import com.instargram.instargram.DM.Message.Message_Request_Map;
import com.instargram.instargram.Notice.Notice;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String member_id;
    private String member_password;

    private LocalDateTime create_date;

    private String nickname;
    private String email;
    private LocalDate birthday;
    private String phone_number;
    private String url;
    private String sex;

    private boolean scope;
    private boolean connected;

    // Community board
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Board> boardList;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Notice> NoticeList;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Board_TagMember_Map> boardTagMemberMapList;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Board_Save_Map> boardSaveMaps;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Board_Like_Member_Map> boardLikeMemberMaps;

    // DM Message
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Message_Member_Map> messageMemberMaps;
    @OneToMany(mappedBy = "request_member_id", cascade = CascadeType.REMOVE)
    private List<Message_Request_Map> messageRequestMap_request;
    @OneToMany(mappedBy = "response_member_id", cascade = CascadeType.REMOVE)
    private List<Message_Request_Map> messageRequestMap_response;

    //Comment
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comment_Like_Map> commentLikeMapList;

    //Recomment
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<ReComment_Like_Map> reCommentLikeMapList;
}
