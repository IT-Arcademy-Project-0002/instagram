package com.instargram.instargram.Member;

<<<<<<< Updated upstream:src/main/java/com/instargram/instargram/Member/Member.java
import com.instargram.instargram.Community.Board.Board;
import com.instargram.instargram.Community.Board.Board_Like_Member_Map;
import com.instargram.instargram.Community.Board.Board_Save_Map;
import com.instargram.instargram.Community.Board.Board_TagMember_Map;
import com.instargram.instargram.Community.Comment.Comment;
=======
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Like_Member_Map;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Save_Map;
import com.instargram.instargram.Community.Board.Model.Entity.Board_TagMember_Map;
>>>>>>> Stashed changes:src/main/java/com/instargram/instargram/Member/Model/Entity/Member.java
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

// 회원 테이블
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회원 아이디
    private String member_id;

    // 회원 비밀번호
    private String member_password;

    // 회원 생성 날짜
    private LocalDateTime create_date;

    // 회원 닉네임
    private String nickname;

    // 회원 이메일
    private String email;

    // 회원 생년월일
    private LocalDate birthday;

    // 회원 핸드폰 번호
    private String phone_number;

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






    // Community board
    // 알림 목록
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Notice> NoticeList;

    // 게시글 목록
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Board> boardList;

    // 게시글에 언급된 회원 목록
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Board_TagMember_Map> boardTagMembers;

    // 회원이 저장한 게시글 목록
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Board_Save_Map> boardSaveMaps;

    // 회원이 좋아요를 한 게시글 매핑 목록
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Board_Like_Member_Map> boardLikeMembers;





    // DM Message
    // 회원이 보낸 디엠 목록
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Message_Member_Map> messageMemberMaps;

    // 회원이 요청한 디엠 목록
    @OneToMany(mappedBy = "request_member_id", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Message_Request_Map> messageRequests;

    // 회원에게 요청된 디엠 목록
    @OneToMany(mappedBy = "response_member_id", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Message_Request_Map> messageResponses;

    // 회원이 참여 중인 디엠 방 목록
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Room_Member_Map> roomMemberList;

    //Comment
    // 회원이 좋아요를 단 댓글 목록
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment_Like_Map> commentLikeMapList;

    //Recomment
    // 회원이 좋아요를 단 대댓글 목록
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ReComment_Like_Map> reCommentLikeMapList;

    //Member
    // 회원이 차단한 회원 목록
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private  List<Hate_Member_Map> hateMemberMaps;

    // 회원이 설정한 친한 친구 목록
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private  List<Love_Member_Map> loveMemberMaps;

    // 회원의 팔로워 목록
    @OneToMany(mappedBy = "following_member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Follow_Map> followerMemberList;

    // 회원이 팔로잉한 회원 목록
    @OneToMany(mappedBy = "follower_member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Follow_Map> followingMemberList;

    // 계정주가 팔로우하길 원해서 요청한 회원 목록
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Follow_Request_Map> requestFollowMembers;

    // 계정주에게 팔로우를 요청한 회원 목록
    @OneToMany(mappedBy = "request_member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Follow_Request_Map> responseFollowMembers;

    // 계정주가 관심계정으로 등록한 회원 목록
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Interest_Member_Map> interestMemberList;

    // 계정주가 즐겨찾기 계정으로 등록한 회원 목록
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Bookmark_Member_Map> bookmarkMemberList;

    // 계정주가 작성한 스토리 목록
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Story_Data_Map> storyDataMaps;

    // 계정주가 작성한 스토리 하이라이트 목록
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Story_Highlight_Map> storyHighlightMaps;

}
