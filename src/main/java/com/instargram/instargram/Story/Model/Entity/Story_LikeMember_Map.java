package com.instargram.instargram.Story.Model.Entity;

import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity

// 스토리와 스토리를 좋아요 한 회원을 매핑해주는 테이블
public class Story_LikeMember_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 스토리를 좋아요 한 회원
    @ManyToOne
    @JoinColumn(name="like_member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member likeMember;

    // 스토리 데이터
    @ManyToOne
    @JoinColumn(name="story_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Story_Data_Map storyData;
}
