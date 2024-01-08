package com.instargram.instargram.Community.Recomment.Model.DTO;

import com.instargram.instargram.Community.Comment.Model.DTO.CommentDTO;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.Community.Comment.Model.Entity.Comment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.Entity.ReComment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
import com.instargram.instargram.Member.Model.DTO.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RecommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private MemberDTO memberDTO;
    private List<Long> recommentLikeMemberIds;

    public RecommentDTO(Recomment recomment) {
        id = recomment.getId();
        content = recomment.getContent();
        createDate = recomment.getCreateDate();
        updateDate = recomment.getUpdateDate();
        if (recomment.getMember() != null){
            memberDTO = new MemberDTO(recomment.getMember());
        }
        // BoardLikeMemberMap에서 필요한 데이터만 가져와서 저장
        recommentLikeMemberIds = recomment.getReCommentLikeMembers().stream()
                .map(ReComment_Like_Map::getId)
                .collect(Collectors.toList());
    }
}
