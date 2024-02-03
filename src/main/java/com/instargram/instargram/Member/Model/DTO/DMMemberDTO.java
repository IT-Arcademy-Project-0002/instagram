package com.instargram.instargram.Member.Model.DTO;

import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DMMemberDTO {

    private Member member;

    private boolean block;

    public DMMemberDTO(Member member, boolean block)
    {
        this.member = member;
        this.block = block;
    }
}
