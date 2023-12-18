package com.instargram.instargram.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Follow_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name="following_member_id")
    Member following_member;

    @ManyToOne
    @JoinColumn(name="follower_member_id")
    Member follower_member;
}
