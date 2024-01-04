package com.instargram.instargram.Notice.Model.DTO;

import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeDTO {

    private Long id;
    private Integer type;
    private Member requestMember;
    private LocalDateTime createDate;

    private boolean follow;
    private boolean follower;
}
