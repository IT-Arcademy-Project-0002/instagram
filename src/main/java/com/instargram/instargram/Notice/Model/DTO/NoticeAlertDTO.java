package com.instargram.instargram.Notice.Model.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeAlertDTO {


    private Long id;

    private LocalDateTime createDate;

    // 알림 타입
    //    게시글 좋아요 : 1
    //    게시글 댓글 : 2
    //    댓글 좋아요 : 3
    //    댓글 대댓글 : 4
    //    댓글 대댓글 좋아요 : 5
    //    게시믈 멤버태그 : 6
    //    스토리 좋아요 : 7
    //    (계정주가 비공개 계정일 시) 팔로우 요청 : 8
    //    (공개 계정) 팔로우 : 9
    //    디엠 왔을 때 : 10
    //    디엠 좋아요 : 11
    private Integer type;

}
