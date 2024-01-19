package com.instargram.instargram.Search.Service;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Search.Model.Entity.SearchMemberMap;
import com.instargram.instargram.Search.Model.Repository.SearchMemberMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchMemberMapService {

    private final SearchMemberMapRepository searchMemberMapRepository;

    public void createSearchFavoriteMember(Member requestMember, Member member) {

        SearchMemberMap searchMemberMap = new SearchMemberMap();
        searchMemberMap.setCreateDate(LocalDateTime.now());
        searchMemberMap.setRequestMember(requestMember);
        searchMemberMap.setMember(member);
        this.searchMemberMapRepository.save(searchMemberMap);

    }

    public List<SearchMemberMap> getSearchMemberMapsByMember(Member member) {
        return this.searchMemberMapRepository.findByRequestMember(member);
    }

    public void deleteSearchMemberMap(SearchMemberMap searchMemberMap) {
        this.searchMemberMapRepository.delete(searchMemberMap);
    }
}
