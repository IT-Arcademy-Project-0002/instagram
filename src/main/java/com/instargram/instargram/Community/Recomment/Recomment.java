package com.instargram.instargram.Community.Recomment;

import com.instargram.instargram.Community.Comment.Comment;
import com.instargram.instargram.Community.Comment.Comment_Like_Map;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Recomment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime create_date;
    private LocalDateTime update_date;

    private String content;

    @ManyToOne
    private Comment comment;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<ReComment_Like_Map> reCommentLikeMapList;
}
