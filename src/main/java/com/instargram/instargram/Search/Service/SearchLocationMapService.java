package com.instargram.instargram.Search.Service;

import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Search.Model.Entity.SearchLocationMap;
import com.instargram.instargram.Search.Model.Entity.SearchMemberMap;
import com.instargram.instargram.Search.Model.Repository.SearchLocationMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchLocationMapService {


    private final SearchLocationMapRepository searchLocationMapRepository;

    public void createSearchFavoriteLocation(Member requestMember, Location location) {

        SearchLocationMap existingMap = searchLocationMapRepository.findByRequestMemberAndLocation(requestMember, location);

        if (existingMap != null) {
            existingMap.setCreateDate(LocalDateTime.now());
            searchLocationMapRepository.save(existingMap);
        } else {
            SearchLocationMap searchLocationMap = new SearchLocationMap();
            searchLocationMap.setCreateDate(LocalDateTime.now());
            searchLocationMap.setRequestMember(requestMember);
            searchLocationMap.setLocation(location);
            this.searchLocationMapRepository.save(searchLocationMap);
        }

    }

    public List<SearchLocationMap> getSearchLocationMapsByMember(Member member) {
        return this.searchLocationMapRepository.findByRequestMember(member);
    }

    public void deleteSearchLocationMap(SearchLocationMap searchLocationMap) {
        this.searchLocationMapRepository.delete(searchLocationMap);
    }
}
