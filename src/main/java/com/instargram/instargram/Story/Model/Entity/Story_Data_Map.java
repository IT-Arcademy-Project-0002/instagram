package com.instargram.instargram.Story.Model.Entity;

import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity

// 스토리 데이터 매핑 테이블
public class Story_Data_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 데이터 타입 (1: 텍스트, 2:이미지, 3: 비디오)
    private Integer data_type;

    // 데이터 아이디 : 데이터 타입에 따라 따로 처리 필요
    private Long data_id;

    // 계정주
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Member owner;

    // 스토리를 좋아요 한 사람들 목록
    @OneToMany(mappedBy = "storyData", cascade = CascadeType.REMOVE)
    private List<Story_LikeMember_Map> storyLikeMembers;
}
