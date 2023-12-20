package com.instargram.instargram.Member.Service;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Model.Form.MemberCreateForm;
import com.instargram.instargram.Member.Model.Repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Builder
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getMember(String id)
    {
        return memberRepository.findByUsername(id);
    }

    public Member getMemberByProvider(String provider, String provider_id)
    {
        return memberRepository.findByProviderAndProviderId(provider, provider_id);
    }


    @PostConstruct
    public void init() {
        saveDefaultUser();
    }

    public void saveDefaultUser() { // 테스트 유저 아이디 아이디: test1, 비밀 번호 test123!
        if (memberRepository.findByUsername("test1") == null) {
            Member member = new Member();
            member.setUsername("test1");
            member.setPassword(passwordEncoder.encode("test123!"));
            member.setEmail("test1@gmail.com");
            member.setNickname("테스트유저");

            memberRepository.save(member);
        }
    }


    public void create(MemberCreateForm memberCreateForm)
    {
        Member member = new Member();
        member.setUsername(memberCreateForm.getUsername());
        member.setPassword(passwordEncoder.encode(memberCreateForm.getPassword()));
        member.setNickname(memberCreateForm.getName());
        member.setEmail(memberCreateForm.getEmail());
        member.setProvider(memberCreateForm.getProvider());
        member.setProviderId(memberCreateForm.getProviderID());
        memberRepository.save(member);
    }
}
