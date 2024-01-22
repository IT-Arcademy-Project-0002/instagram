package com.instargram.instargram.DM.Model.Entity.Message;

import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id", "message_member_map_id"})})
public class Emoji {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Member member;

    @ManyToOne
    @JoinColumn(name = "message_member_map_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Message_Member_Map map;

    String content;
}
