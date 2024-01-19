package com.instargram.instargram.Search.Service;

import com.instargram.instargram.Community.Board.Model.Repository.Board_HashTag_MapRepository;
import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Community.HashTag.Model.Repository.HashTagRepository;
import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.Community.Location.Model.Repository.LocationRepository;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Model.Repository.MemberRepository;
import com.instargram.instargram.Notice.Model.DTO.NoticeDTO;
import com.instargram.instargram.Search.Config.Enum.SearchType;
import com.instargram.instargram.Search.Model.DTO.SearchDTO;

import com.instargram.instargram.Search.Model.Entity.SearchHashTagMap;
import com.instargram.instargram.Search.Model.Entity.SearchLocationMap;
import com.instargram.instargram.Search.Model.Entity.SearchMemberMap;
import com.instargram.instargram.Search.Model.Repository.SearchHashTagMapRepository;
import com.instargram.instargram.Search.Model.Repository.SearchLocationMapRepository;
import com.instargram.instargram.Search.Model.Repository.SearchMemberMapRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class SearchService {

    //탐색범위 : 계정(회원명, 회원아이디, 자기소개, 자기소개 카테고리), 게시글(내용), 해시태그(해시태그), 위치(위도, 경도, 장소고유식별번호)
    //해당 서비스 또는 레포지토리의 검색메소드를 차용

    // data-order의 개념을 사용하여 분리해야 하는가?
    // 서버에서 받아온 데이터를 리스트 생성
    // 세 가지 분류
    // 1) 데이터형이 계정이라면 계정 프로필 링크 생성 (~/username, 유저아이디)
    // 2) 해시태그라면 explore 페이지 연결, (~/explore/tags/태그명)
    // 3) 장소라면 explore 페이지 연결 (~/explore/locations/장소분류고유번호) openstreetmap 사용
    //    (괄호안의 숫자는 위치 DB의 ID로 추정, 일반적인 게시물의 의미를 생각하였을 때)

    private final SearchMemberMapRepository searchMemberMapRepository;
    private final SearchLocationMapRepository searchLocationMapRepository;
    private final SearchHashTagMapRepository searchHashTagMapRepository;

    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;
    private final HashTagRepository hashTagRepository;
    private final Board_HashTag_MapRepository boardHashTagMapRepository;


    public void createSearchFavoriteMember(Member requestMember, Member member) {

        SearchMemberMap searchMemberMap = new SearchMemberMap();
        searchMemberMap.setCreateDate(LocalDateTime.now());
        searchMemberMap.setRequestMember(requestMember);
        searchMemberMap.setMember(member);
        this.searchMemberMapRepository.save(searchMemberMap);

    }

    public void createSearchFavoriteLocation(Member requestMember, Location location) {

        SearchLocationMap searchLocationMap = new SearchLocationMap();
        searchLocationMap.setCreateDate(LocalDateTime.now());
        searchLocationMap.setRequestMember(requestMember);
        searchLocationMap.setLocation(location);
        this.searchLocationMapRepository.save(searchLocationMap);

    }

    public void createSearchFavoriteHashTag(Member requestMember, HashTag hashTag) {

        SearchHashTagMap searchHashTagMap = new SearchHashTagMap();
        searchHashTagMap.setCreateDate(LocalDateTime.now());
        searchHashTagMap.setRequestMember(requestMember);
        searchHashTagMap.setHashTag(hashTag);
        this.searchHashTagMapRepository.save(searchHashTagMap);

    }

    public List<SearchDTO> createSearchFavoriteList(Member loginMember) {

        List<SearchDTO> searches = new ArrayList<>();

        List<SearchDTO> searchesByMember = getSearchDTOsByMember(loginMember);
        List<SearchDTO> searchesByLocation = getSearchDTOsByLocation(loginMember);
        List<SearchDTO> searchesByHashTag = getSearchDTOsByHashTag(loginMember);

        searches.addAll(searchesByMember);
        searches.addAll(searchesByLocation);
        searches.addAll(searchesByHashTag);

        return searches;
    }


    // 지난 검색 결과를 보여주기 위한 DTO

    private List<SearchDTO> getSearchDTOsByMember(Member requestMember) {

        List<SearchMemberMap> searchMemberMapList = this.searchMemberMapRepository.findByRequestMember(requestMember);
        List<SearchDTO> searchDTOList = new ArrayList<>();

        for (SearchMemberMap searchMemberMap : searchMemberMapList) {
            Member member = searchMemberMap.getMember();
            SearchDTO sd = new SearchDTO();
            sd.setListName(member.getUsername());
            sd.setSearchType(SearchType.USER.getNumber());
            sd.setListName(member.getUsername());
            sd.setListImage(member.getImage() != null ? member.getImage().getName() : "");
            sd.setListIntroduction(member.getIntroduction());
            searchDTOList.add(sd);
        }
        return searchDTOList;
    }

    private List<SearchDTO> getSearchDTOsByLocation(Member requestMember) {

        List<SearchLocationMap> searchLocationMapList = this.searchLocationMapRepository.findByRequestMember(requestMember);
        List<SearchDTO> searchDTOList = new ArrayList<>();

        for (SearchLocationMap searchLocationMap : searchLocationMapList) {
            Location location = searchLocationMap.getLocation();
            SearchDTO sd = new SearchDTO();
            sd.setSearchType(SearchType.LOCATION.getNumber());
            sd.setListLocationId(location.getLocationId());
            sd.setListName(location.getPlaceName());
            sd.setListImage("");
            sd.setListIntroduction(location.getAddress());
            searchDTOList.add(sd);
        }
        return searchDTOList;
    }

    private List<SearchDTO> getSearchDTOsByHashTag(Member requestMember) {

        List<SearchHashTagMap> searchHashTagMapList = this.searchHashTagMapRepository.findByRequestMember(requestMember);
        List<SearchDTO> searchDTOList = new ArrayList<>();

        for (SearchHashTagMap searchHashTagMap : searchHashTagMapList) {
            HashTag hashTag = searchHashTagMap.getHashTag();
            SearchDTO sd = new SearchDTO();
            sd.setSearchType(SearchType.HASHTAG.getNumber());
            sd.setListHashTagId(String.valueOf(hashTag.getId()));
            sd.setListName(hashTag.getName());
            sd.setListImage("");
            searchDTOList.add(sd);
        }
        return searchDTOList;
    }

    // ajax 기반 실시간 검색결과 출력을 위한 DTO

    public List<SearchDTO> searchByMember(String keyword) {

        List<Member> memberForNickname = this.memberRepository.findByNicknameContaining(keyword);
        List<Member> memberForUsername = this.memberRepository.findByUsernameContaining(keyword);
        List<Member> memberForIntroduction = this.memberRepository.findByIntroductionContaining(keyword);

        List<SearchDTO> searchDTOList = new ArrayList<>();

        // 중복 체크를 위한 Set 생성
        Set<Long> addedMemberIds = new HashSet<>();

        for (Member member : memberForNickname) {
            if (addedMemberIds.add(member.getId())) { // Set에 추가하고, 이미 존재하면 추가하지 않음
                SearchDTO sd = new SearchDTO();
                sd.setSearchType(SearchType.USER.getNumber());
                sd.setListName(member.getUsername());
                sd.setListImage(member.getImage() != null ? member.getImage().getName() : "");
                sd.setListIntroduction(member.getIntroduction());
                searchDTOList.add(sd);
            }
        }

        for (Member member : memberForUsername) {
            if (addedMemberIds.add(member.getId())) {
                SearchDTO sd = new SearchDTO();
                sd.setSearchType(SearchType.USER.getNumber());
                sd.setListName(member.getUsername());
                sd.setListImage(member.getImage() != null ? member.getImage().getName() : "");
                sd.setListIntroduction(member.getIntroduction());
                searchDTOList.add(sd);
            }
        }

        for (Member member : memberForIntroduction) {
            if (addedMemberIds.add(member.getId())) {
                SearchDTO sd = new SearchDTO();
                sd.setSearchType(SearchType.USER.getNumber());
                sd.setListName(member.getUsername());
                sd.setListImage(member.getImage() != null ? member.getImage().getName() : "");
                sd.setListIntroduction(member.getIntroduction());
                searchDTOList.add(sd);
            }
        }

        return searchDTOList;
    }

    public List<SearchDTO> searchByLocation(String keyword) {

        List<SearchDTO> searchDTOList = new ArrayList<>();

        List<Location> locationList = this.locationRepository.findByPlaceNameContaining(keyword);

        List<Location> matchingLocations = new ArrayList<>();

        // 특정 키워드를 가진 장소를 찾아서 location에 추가
        for (Location location : locationList) {
            if (location.getPlaceName().contains(keyword)) {
                matchingLocations.add(location);
            }
        }

        if (!matchingLocations.isEmpty()) {
            SearchDTO sd = new SearchDTO();
            sd.setSearchType(SearchType.LOCATION.getNumber());
            sd.setListLocationId(matchingLocations.get(0).getLocationId());
            sd.setListName(matchingLocations.get(0).getPlaceName());
            sd.setListImage("");
            sd.setListIntroduction(matchingLocations.get(0).getAddress());
            searchDTOList.add(sd);
        }

        return searchDTOList;
    }

    public List<SearchDTO> searchByHashTags(String keyword) {

        List<SearchDTO> searchDTOList = new ArrayList<>();

        List<HashTag> hashTagList = this.hashTagRepository.findByNameContaining(keyword);

        // Map을 사용하여 해시태그를 이름으로 그룹화
        Map<String, List<HashTag>> hashTagGroups = new HashMap<>();

        for (HashTag hashTag : hashTagList) {
            // 키로 사용할 해시태그 이름 추출
            String key = hashTag.getName();

            // 동일한 이름의 해시태그를 그룹에 추가
            hashTagGroups
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(hashTag);
        }

        // 그룹화된 해시태그에 대한 정보를 SearchDTO에 매핑하고 리스트에 추가
        for (Map.Entry<String, List<HashTag>> entry : hashTagGroups.entrySet()) {
            String hashTagName = entry.getKey();
            List<HashTag> hashTagGroup = entry.getValue();

            SearchDTO sd = new SearchDTO();
            sd.setSearchType(SearchType.HASHTAG.getNumber());
            sd.setListHashTagId(String.valueOf(hashTagGroup.get(0).getId()));
            sd.setListName(hashTagName);
            sd.setListImage("");
            sd.setListIntroduction("게시물 " + formatBoardCount(getBoardCountByHashTag(hashTagGroup.get(0).getId())));

            searchDTOList.add(sd);
        }

        return searchDTOList;
    }

    private long getBoardCountByHashTag(Long id) {
        // 해당 해시태그를 참조하는 게시글 수 조회
        return this.boardHashTagMapRepository.countByTag_Id(id);
    }

    // 해시태그 게시글 표기단위 적용함수 (1만 전후로 나뉨)
    private String formatBoardCount(long count) {
        if (count < 10000) {
            // 1만 미만은 그냥 숫자만 반환
            return String.valueOf(count);
        } else {
            // 1만 이상은 "만"을 붙이고, 천의 단위로 표현
            double countInThousands = count / 10000.0;
            return String.format("%.1f만", countInThousands);
        }
    }
}
