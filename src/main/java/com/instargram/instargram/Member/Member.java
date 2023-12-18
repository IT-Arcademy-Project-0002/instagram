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
import com.instargram.instargram.DM.Room.Room_Member_Map;
import com.instargram.instargram.Notice.Notice;
import com.instargram.instargram.Story.Story_Data_Map;
import com.instargram.instargram.Story.Story_Highlight_Map;
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

    //Member
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private  List<Hate_Member_Map> hateMemberMaps;
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private  List<Love_Member_Map> loveMemberMaps;
    @OneToMany(mappedBy = "following_member", cascade = CascadeType.REMOVE)
    private List<Follow_Map> followingMemberList;
    @OneToMany(mappedBy = "follower_member", cascade = CascadeType.REMOVE)
    private List<Follow_Map> followerMemberList;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Follow_Request_Map> requestFollowMemberList;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Interest_Member_Map> interestMemberList;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Bookmark_Member_Map> bookmarkMemberList;

    @OneToMany(mappedBy = "member")
    private List<Room_Member_Map> roomMemberList;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Story_Data_Map> storyDataMaps;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Story_Highlight_Map> storyHighlightMaps;

}
