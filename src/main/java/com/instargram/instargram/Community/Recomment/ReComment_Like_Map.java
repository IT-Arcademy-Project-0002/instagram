package com.instargram.instargram.Community.Recomment;

import com.instargram.instargram.Community.Comment.Comment;
import com.instargram.instargram.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ReComment_Like_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Recomment recomment;

    @ManyToOne
    private Member member;
}
