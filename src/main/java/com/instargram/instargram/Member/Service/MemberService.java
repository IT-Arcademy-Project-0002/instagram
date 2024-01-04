package com.instargram.instargram.Member.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Config.AppConfig;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Member.Config.SpringSecurity.MemberSecurityService;
import com.instargram.instargram.Member.Model.Entity.Follow_Map;
import com.instargram.instargram.Member.Model.Entity.Follow_Request_Map;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Model.Form.MemberCreateForm;
import com.instargram.instargram.Member.Model.Repository.FollowMapRepository;
import com.instargram.instargram.Member.Model.Repository.MemberRepository;
import com.instargram.instargram.Notice.Notice;
import com.instargram.instargram.Notice.NoticeService;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Builder
@Lazy
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Getter
    private final FollowMapService followMapService;
    private final NoticeService noticeService;
    private final FollowMapRepository followMapRepository;

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
            member.setScope(true);

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
        member.setScope(true);
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
        if(targetUser.isScope())
        {
            return followMapService.UserFollow(loginUser, targetUser);
        }
        else{
            return followMapService.followRequest(noticeService, loginUser, targetUser);
        }
    }

    public boolean RequestFollowApply(Member loginUser, Member requestUser)
    {
        return followMapService.RequestFollowApply(requestUser, loginUser);
    }

    public void RequestFollowDelete(Long id)
    {
        Notice notice = noticeService.getNotice(id);
        followMapService.deleteRequestFollow(notice.getRequestMember(), notice.getMember());
        noticeService.deleteById(id);
    }

    public boolean isFollow(Member loginUser, Member targetUser)
    {
        return followMapService.isFollow(loginUser, targetUser);
    }

    public boolean isFollower(Member loginUser, Member targetUser)
    {
        return followMapService.isFollower(targetUser, loginUser);
    }
    public void changeProfile(String username, String sex, String introduce)
    {
        Member member = getMember(username);

        member.setSex(sex);
        member.setIntroduction(introduce);

        memberRepository.save(member);
    }

    public void changeName(String username, String name)
    {
        Member member = getMember(username);

        member.setNickname(name);

        memberRepository.save(member);
    }

    public Member changeUsername(String username, String newUsername)
    {
        Member member = getMember(username);

        member.setUsername(newUsername);

        return memberRepository.save(member);
    }

    public boolean duplicUserName(String username)
    {
        return !memberRepository.existsByUsername(username);
    }

    public void changeScope(String username, boolean scope)
    {
        Member member = getMember(username);

        member.setScope(scope);

        memberRepository.save(member);
    }

    public Map<String, Object> requestFollowState(Long id, String loginUser)
    {
        Map<String, Object> result = new HashMap<>();

        Notice notice = noticeService.getNotice(id);
        Member member = getMember(loginUser);

        result.put("isFollow", isFollow(member, notice.getRequestMember()));
        result.put("isFollower", isFollower(member, notice.getRequestMember()));

        return result;
    }

    public List<Long> getFollower(Member member) {
        List<Follow_Map> followerMappings = this.followMapRepository.findByFollowingMember(member);
        List<Long> followerIdList = new ArrayList<>();

        for (Follow_Map map : followerMappings) {
            followerIdList.add(map.getFollowerMember().getId());
        }

        return followerIdList;
    }

    public List<Long> getFollowing(Member member) {
        List<Follow_Map> followingMappings = this.followMapRepository.findByFollowerMember(member);
        List<Long> followingIdList = new ArrayList<>();

        for(Follow_Map map : followingMappings){
            followingIdList.add(map.getFollowingMember().getId());
        }
        return followingIdList;
    }
}
