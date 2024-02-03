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

        SearchMemberMap existingMap = searchMemberMapRepository.findByRequestMemberAndMember(requestMember, member);

        if (existingMap != null) {
            existingMap.setCreateDate(LocalDateTime.now());
            searchMemberMapRepository.save(existingMap);
        } else {
            SearchMemberMap newMap = new SearchMemberMap();
            newMap.setCreateDate(LocalDateTime.now());
            newMap.setRequestMember(requestMember);
            newMap.setMember(member);
            searchMemberMapRepository.save(newMap);
        }
    }

    public List<SearchMemberMap> getSearchMemberMapsByMember(Member member) {
        return this.searchMemberMapRepository.findByRequestMember(member);
    }

    public void deleteSearchMemberMap(SearchMemberMap searchMemberMap) {
        this.searchMemberMapRepository.delete(searchMemberMap);
    }
}
