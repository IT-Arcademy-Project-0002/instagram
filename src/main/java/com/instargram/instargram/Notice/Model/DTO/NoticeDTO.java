package com.instargram.instargram.Notice.Model.DTO;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.FollowMapService;
import com.instargram.instargram.Notice.Model.Entitiy.Notice;
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


    public NoticeDTO(Member loginMember, Notice notice, FollowMapService followMapService)
    {
        this.id = notice.getId();
        this.type = notice.getType();
        this.requestMember = notice.getRequestMember();
        this.createDate = notice.getCreateDate();

        follower = followMapService.isFollower(loginMember, this.requestMember);
        follow = followMapService.isFollow(loginMember, this.requestMember);
    }
}
