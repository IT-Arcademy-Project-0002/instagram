package com.instargram.instargram.Search.Model.Entity;

import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
public class SearchHashTagMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requestMember_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member requestMember;

    @ManyToOne
    @JoinColumn(name = "hashTag_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private HashTag hashTag;
}
