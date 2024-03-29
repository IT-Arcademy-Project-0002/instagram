package com.instargram.instargram.Search.Model.Repository;

import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Search.Model.Entity.SearchLocationMap;
import com.instargram.instargram.Search.Model.Entity.SearchMemberMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchLocationMapRepository extends JpaRepository<SearchLocationMap, Long> {

    List<SearchLocationMap> findByRequestMember(Member requestMember);

    SearchLocationMap findByRequestMemberAndLocation(Member requestMember, Location location);

}
