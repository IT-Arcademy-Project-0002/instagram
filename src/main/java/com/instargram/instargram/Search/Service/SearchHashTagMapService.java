package com.instargram.instargram.Search.Service;

import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Search.Model.Entity.SearchHashTagMap;
import com.instargram.instargram.Search.Model.Entity.SearchLocationMap;
import com.instargram.instargram.Search.Model.Entity.SearchMemberMap;
import com.instargram.instargram.Search.Model.Repository.SearchHashTagMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchHashTagMapService {

    private final SearchHashTagMapRepository searchHashTagMapRepository;

    public void createSearchFavoriteHashTag(Member requestMember, HashTag hashTag) {
        SearchHashTagMap searchHashTagMap = new SearchHashTagMap();
        searchHashTagMap.setCreateDate(LocalDateTime.now());
        searchHashTagMap.setRequestMember(requestMember);
        searchHashTagMap.setHashTag(hashTag);
        this.searchHashTagMapRepository.save(searchHashTagMap);
    }

    public List<SearchHashTagMap> getSearchHashTagMapsByMember(Member member) {
        return searchHashTagMapRepository.findByRequestMember(member);
    }

    public void deleteSearchHashTagMap(SearchHashTagMap searchHashTagMap) {
        searchHashTagMapRepository.delete(searchHashTagMap);
    }
}
