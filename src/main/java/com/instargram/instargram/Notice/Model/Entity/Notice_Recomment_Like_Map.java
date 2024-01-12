package com.instargram.instargram.Notice.Model.Entity;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.Entity.ReComment_Like_Map;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
public class Notice_Recomment_Like_Map {

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
    private ReComment_Like_Map recommentLike;

}
