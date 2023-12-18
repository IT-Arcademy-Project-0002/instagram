package com.instargram.instargram.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Bookmark_Member_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="owner_id")
    Member owner;

    @ManyToOne
    @JoinColumn(name="bookmark_member_id")
    Member bookmark_member;
}
