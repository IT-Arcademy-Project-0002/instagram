package com.instargram.instargram.DM.Model.Entity.Message;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CommentMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Message message;

    @ManyToOne
    private Message_Member_Map messageMap;
}
