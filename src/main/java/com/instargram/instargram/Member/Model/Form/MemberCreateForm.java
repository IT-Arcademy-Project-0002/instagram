package com.instargram.instargram.Member.Model.Form;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberCreateForm {
    // 회원 아이디
    @NotEmpty
    private String member_id;

    // 회원 비밀번호
    @NotEmpty
    private String member_password;

    // 회원 닉네임
    @NotEmpty
    private String nickname;

    // 회원 이메일
    @NotEmpty
    private String email;
}
