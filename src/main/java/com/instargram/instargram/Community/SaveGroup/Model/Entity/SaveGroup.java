package com.instargram.instargram.Community.SaveGroup.Model.Entity;

import com.instargram.instargram.Community.Board.Model.Entity.Board_Save_Map;
import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@Entity

// 게시글 저장 그룹 혹은 스토리 하이라이트 그룹
public class SaveGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 그룹 명
    private String name;

    // 게시글 작성자
    @ManyToOne
    @JoinColumn(name="member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    // 회원이 저장한 게시글 매핑 테이블 목록
    @OneToMany(mappedBy = "saveGroup", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Board_Save_Map> boardSaveMaps;
}