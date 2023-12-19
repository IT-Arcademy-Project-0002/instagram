package com.instargram.instargram.Story;

import com.instargram.instargram.Community.SaveGroup.SaveGroup;
import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
// 스토리 하이라이트 테이블
// 올린 스토리 중 하이라이트를 추출하여 그룹으로 게시할 수 있는 기능
public class Story_Highlight_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 계정주
    @ManyToOne
    @JoinColumn(name="owner_id")
    Member owner;

    // 스토리 데이터
    @ManyToOne
    @JoinColumn(name="story_id")
    Story_Data_Map storyData;

    // 저장된 그룹
    @ManyToOne
    @JoinColumn(name="group_id")
    SaveGroup saveGroup;

}
