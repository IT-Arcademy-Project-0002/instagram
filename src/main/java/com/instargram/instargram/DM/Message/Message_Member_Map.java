package com.instargram.instargram.DM.Message;

import com.instargram.instargram.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Message_Member_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer data_type;
    private Long data_id;

    @ManyToOne
    private Member member;
    @OneToOne
    private Message_Empathy_Map messageEmpathyMap;
}
