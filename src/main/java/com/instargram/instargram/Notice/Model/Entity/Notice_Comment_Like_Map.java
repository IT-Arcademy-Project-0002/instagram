package com.instargram.instargram.Notice.Model.Entity;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
public class Notice_Comment_Like_Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Notice notice;

    @ManyToOne
    @JoinColumn(name = "comment_like_map_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment_Like_Map commentLike;
}
