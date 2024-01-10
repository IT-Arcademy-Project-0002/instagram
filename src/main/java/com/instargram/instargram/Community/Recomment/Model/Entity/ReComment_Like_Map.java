package com.instargram.instargram.Community.Recomment.Model.Entity;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Notice.Model.Entity.Notice_Recomment_Like_Map;
import com.instargram.instargram.Notice.Model.Entity.Notice_Recomment_Map;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@Entity

// 대댓글에 좋아요 한 사람 매핑 테이블
public class ReComment_Like_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 대댓글
    @ManyToOne
    @JoinColumn(name="recomment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Recomment recomment;

    // 회원
    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @OneToMany(mappedBy = "recommentLike", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Notice_Recomment_Like_Map> noticeRecommentLikeMap;
}
