package com.instargram.instargram.Search.Model.Repository;

import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Search.Model.Entity.SearchHashTagMap;
import com.instargram.instargram.Search.Model.Entity.SearchLocationMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHashTagMapRepository extends JpaRepository<SearchHashTagMap, Long> {

    List<SearchHashTagMap> findByRequestMember(Member requestMember);

    SearchHashTagMap findByRequestMemberAndHashTag(Member requestMember, HashTag hashTag);
}
