package com.instargram.instargram.Community.Board;

import com.instargram.instargram.Community.Comment.Comment;
import com.instargram.instargram.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Text")
    private String content;

    private LocalDateTime create_date;
    private LocalDateTime update_date;

    private boolean scope;
    private boolean pin;
    private boolean keep;
    private boolean like_hide;
    private boolean comment_disable;

    @ManyToOne
    private Member member;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comment> commentList;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Board_TagMember_Map> boardTagMemberMaps;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Board_Save_Map> boardSaveMaps;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Board_HashTag_Map> boardHashTagMaps;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Board_Like_Member_Map> boardLikeMemberMaps;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Board_Data_Map> boardDataMaps;
}
