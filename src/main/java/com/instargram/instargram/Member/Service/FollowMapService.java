package com.instargram.instargram.Member.Service;

import com.instargram.instargram.Member.Model.Entity.Follow_Map;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Model.Repository.FollowMapRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Builder
public class FollowMapService {
    private final FollowMapRepository followMapRepository;

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
            Follow_Map map = getMap(member, target);
            deleteFollow(map);
            return false;
        }
    }

    public void deleteFollow(Follow_Map map)
    {
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
}
