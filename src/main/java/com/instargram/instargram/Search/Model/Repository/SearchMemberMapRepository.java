package com.instargram.instargram.Search.Model.Repository;


import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Search.Model.Entity.SearchMemberMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchMemberMapRepository extends JpaRepository<SearchMemberMap, Long> {

    List<SearchMemberMap> findByRequestMember(Member requestMember);

}
