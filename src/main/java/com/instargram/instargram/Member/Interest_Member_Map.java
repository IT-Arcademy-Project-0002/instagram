package com.instargram.instargram.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Interest_Member_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne()
    @JoinColumn(name="owner")
    Member owner;

    @ManyToOne
    @JoinColumn(name="interest_member_id")
    Member interest_member_id;
}
