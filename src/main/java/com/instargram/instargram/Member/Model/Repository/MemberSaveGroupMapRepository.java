package com.instargram.instargram.Member.Model.Repository;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Model.Entity.Member_SaveGroup_Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberSaveGroupMapRepository extends JpaRepository<Member_SaveGroup_Map, Long> {
    List<Member_SaveGroup_Map> findByMember(Member member);
}
