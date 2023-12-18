package com.instargram.instargram.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Follow_Request_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "owner")
    Member owner;

    @ManyToOne
    @JoinColumn(name="request_member")
    Member request_member;
}
