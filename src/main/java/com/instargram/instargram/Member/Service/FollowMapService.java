package com.instargram.instargram.Member.Service;

import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Member.Model.Entity.Follow_Map;
import com.instargram.instargram.Member.Model.Entity.Follow_Request_Map;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Model.Repository.FollowMapRepository;
import com.instargram.instargram.Member.Model.Repository.FollowRequestMapRepository;
import com.instargram.instargram.Notice.NoticeService;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@Service
@Builder
public class FollowMapService {
    private final FollowMapRepository followMapRepository;
    private final FollowRequestMapRepository followRequestMapRepository;

    public List<Member> getFollowers(Member member)
    {
        return followMapRepository.findByFollowerMember(member).stream().map(Follow_Map::getFollowerMember).toList();
    }

    public List<Member> getFollowings(Member member)
    {
        return followMapRepository.findByFollowingMember(member).stream().map(Follow_Map::getFollowingMember).toList();
    }

    public Follow_Map getMap(Member member, Member target)
    {
        return followMapRepository.findByFollowerMemberAndFollowingMember(member, target);
    }

    public boolean UserFollow(Member member, Member target)
    {
        if(!isFollow(member, target))
        {
            Follow_Map followMap = new Follow_Map();
            followMap.setFollowingMember(target);
            followMap.setFollowerMember(member);
            followMapRepository.save(followMap);
            return true;
        }
        else{
            deleteFollow(member, target);
            return false;
        }
    }

    public boolean RequestFollowApply(Member requestUser, Member loginUser)
    {
        Follow_Map followMap = new Follow_Map();
        followMap.setFollowingMember(loginUser);
        followMap.setFollowerMember(requestUser);
        followMapRepository.save(followMap);
        
        deleteRequestFollow(requestUser, loginUser);
        return true;
    }

    public void deleteFollow(Member member, Member target)
    {
        Follow_Map map = getMap(member, target);
        followMapRepository.delete(map);
    }

    //내가 팔로우한 사람인지 확인하는 함수
    public boolean isFollow(Member member, Member target)
    {
        return followMapRepository.existsByFollowerMemberAndFollowingMember(member, target);
    }

    //내 팔로워인지 확인하는 함수
    public boolean isFollower(Member member, Member target)
    {
        return followMapRepository.existsByFollowerMemberAndFollowingMember(target, member);
    }

    public boolean followRequest(NoticeService noticeService, Member member, Member target)
    {
        if(!isRequestFollow(member, target))
        {
            Follow_Request_Map followRequestMap = new Follow_Request_Map();
            followRequestMap.setRequestMember(member);
            followRequestMap.setOwner(target);
            followRequestMapRepository.save(followRequestMap);
            noticeService.createNotice(Enum_Data.FOLLOW_REQUEST.getNumber(), member, target);
            return true;
        }
        else {
            deleteRequestFollow(member, target);
            return false;
        }
    }

    public Follow_Request_Map getRequestMap(Member requestUser, Member owner)
    {
        return followRequestMapRepository.findByRequestMemberAndOwner(requestUser, owner);
    }

    public boolean isRequestFollow(Member member, Member target)
    {
        return followRequestMapRepository.existsByRequestMemberAndOwner(member, target);
    }

    public void deleteRequestFollow(Member requestUser, Member owner)
    {
        Follow_Request_Map followRequestMap = getRequestMap(requestUser, owner);
        followRequestMapRepository.delete(followRequestMap);
    }
}
