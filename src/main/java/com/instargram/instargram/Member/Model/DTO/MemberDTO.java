package com.instargram.instargram.Member.Model.DTO;

import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private Long id;
    private String username;

    public MemberDTO (Member member){
        id = member.getId();
        username = member.getUsername();
    }
}
