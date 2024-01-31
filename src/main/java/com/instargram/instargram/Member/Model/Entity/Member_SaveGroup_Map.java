package com.instargram.instargram.Member.Model.Entity;

import com.instargram.instargram.Community.SaveGroup.Model.Entity.SaveGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
public class Member_SaveGroup_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회원
    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    // 저장 그룹
    @ManyToOne
    @JoinColumn(name = "save_group_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SaveGroup saveGroup;
}
