package com.instargram.instargram.Member.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageService;
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
    private final FollowMapService followMapService;

    public Member getMemberByUsername(String id)
    {
        return memberRepository.findByUsername(id);
    }

    public Member getMemberByProvider(String provider, String provider_id)
    {
        return memberRepository.findByProviderAndProviderId(provider, provider_id);
    }


    public Member getMemberByEmail(String email)
    {
        return memberRepository.findByEmail(email);
    }

    public Member getMemberByPhoneNumber(String num)
    {
        return memberRepository.findByPhoneNumber(num);
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

    public Member getMember(String username) {
        return this.memberRepository.findByUsername(username);
    }

    public Image ProfileImageUpload(Member member, Image image)
    {
        Image oldImg = member.getImage();

        member.setImage(image);

        this.memberRepository.save(member);

        return oldImg;
    }

    public Image ProfileImageDelete(Member member)
    {
        Image oldImg = member.getImage();

        member.setImage(null);

        this.memberRepository.save(member);

        return oldImg;
    }

    public boolean UserFollow(Member loginUser, Member targetUser)
    {
        return followMapService.UserFollow(loginUser, targetUser);
    }

    public void changeProfile(String username, String sex, String introduce)
    {
        Member member = getMember(username);

        member.setSex(sex);
        member.setIntroduction(introduce);

        memberRepository.save(member);
    }

}
