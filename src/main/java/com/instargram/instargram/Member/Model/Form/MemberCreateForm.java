package com.instargram.instargram.Member.Model.Form;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateForm {
    // 회원 아이디
    @NotBlank
    private String username;

    // 회원 비밀번호
    @NotBlank
    private String password;

    // 회원 성명
    @NotBlank
    private String name;

    // 회원 이메일
    @NotBlank
    private String email;

    // sns 연동 계정만 사용
    private String provider;

    private String providerID;
}
