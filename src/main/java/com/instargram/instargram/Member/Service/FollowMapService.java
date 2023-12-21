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
}
