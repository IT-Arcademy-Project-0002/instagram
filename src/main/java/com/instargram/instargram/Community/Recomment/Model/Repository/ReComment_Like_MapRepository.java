package com.instargram.instargram.Community.Recomment.Model.Repository;

import com.instargram.instargram.Community.Recomment.Model.Entity.ReComment_Like_Map;
import com.instargram.instargram.Community.Recomment.Model.Entity.Recomment;
import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReComment_Like_MapRepository extends JpaRepository<ReComment_Like_Map, Long> {
    ReComment_Like_Map findByRecommentAndMember(Recomment recomment, Member member);

}
