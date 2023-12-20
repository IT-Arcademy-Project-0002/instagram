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
    private String username;

    // 회원 비밀번호
    @NotEmpty
    private String password;

    // 회원 성명
    @NotEmpty
    private String name;

    // 회원 이메일
    @NotEmpty
    private String email;


    // sns 연동 계정만 사용
    private String provider;

    private String providerID;
}
