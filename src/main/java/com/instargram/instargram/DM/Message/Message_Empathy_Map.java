package com.instargram.instargram.DM.Message;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Message_Empathy_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    // Message_Member_Map
    @OneToOne(mappedBy = "messageEmpathyMap")
    private Message_Member_Map messageMemberMap;
}
