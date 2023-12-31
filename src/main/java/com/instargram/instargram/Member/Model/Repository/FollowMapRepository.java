package com.instargram.instargram.Member.Model.Repository;

import com.instargram.instargram.Member.Model.Entity.Follow_Map;
import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowMapRepository extends JpaRepository<Follow_Map, Long> {
    List<Follow_Map> findByFollowerMember(Member member);
    List<Follow_Map> findByFollowingMember(Member member);

    Follow_Map findByFollowerMemberAndFollowingMember(Member follower, Member following);

    boolean existsByFollowerMemberAndFollowingMember(Member follower, Member following);

    List<Follow_Map> findByFollowingMember_Id(Long memberId);
}
