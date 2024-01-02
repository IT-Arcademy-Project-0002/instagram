package com.instargram.instargram.Member.Model.Repository;

import com.instargram.instargram.Member.Model.Entity.Follow_Request_Map;
import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRequestMapRepository extends JpaRepository<Follow_Request_Map, Long> {

    Follow_Request_Map findByRequestMemberAndOwner(Member member, Member target);
    boolean existsByRequestMemberAndOwner(Member member, Member target);
}
