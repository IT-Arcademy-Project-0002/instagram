package com.instargram.instargram.Search.Controller;

import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Community.HashTag.Service.HashTagService;
import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.Community.Location.Service.LocationService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import com.instargram.instargram.Search.Model.DTO.SearchDTO;
import com.instargram.instargram.Search.Model.Entity.SearchHashTagMap;
import com.instargram.instargram.Search.Model.Entity.SearchLocationMap;
import com.instargram.instargram.Search.Model.Entity.SearchMemberMap;
import com.instargram.instargram.Search.Model.Repository.SearchHashTagMapRepository;
import com.instargram.instargram.Search.Model.Repository.SearchLocationMapRepository;
import com.instargram.instargram.Search.Service.SearchHashTagMapService;
import com.instargram.instargram.Search.Service.SearchLocationMapService;
import com.instargram.instargram.Search.Service.SearchMemberMapService;
import com.instargram.instargram.Search.Service.SearchService;

import lombok.Builder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@Builder
@RequestMapping("/search")
public class SearchController {

    private final MemberService memberService;
    private final LocationService locationService;
    private final HashTagService hashTagService;

    private final SearchService searchService;
    private final SearchHashTagMapService searchHashTagMapService;
    private final SearchLocationMapService searchLocationMapService;
    private final SearchMemberMapService searchMemberMapService;

    @GetMapping("")
    public String searchUrl(){
        return "Search/search";
    }

    @GetMapping(value = "/keyword", produces = "application/json")
    @ResponseBody
    public List<SearchDTO> keywordSearchJson(@RequestParam("keyword") String keyword) {

        List<SearchDTO> searchResult = new ArrayList<>();

        // 각 메서드의 결과를 하나의 리스트에 추가
        searchResult.addAll(this.searchService.searchByMember(keyword));
        searchResult.addAll(this.searchService.searchByLocation(keyword));
        searchResult.addAll(this.searchService.searchByHashTags(keyword));

        return searchResult;
    }

    @PostMapping("/favorite/create")
    @ResponseBody
    public void searchFavoriteCreate(Principal principal,
                               @RequestBody Map<String, Object> data) {

        Member requestMember = this.memberService.getMemberByUsername(principal.getName());

        Integer searchType = (Integer) data.get("favoriteType");
        String specificName = (String) data.get("specificName");


        if (searchType != null) {
            if (searchType == 1) {
                Member member = this.memberService.getMemberByUsername(specificName);
                this.searchMemberMapService.createSearchFavoriteMember(requestMember, member);
            } else if (searchType == 2) {
                Location location = this.locationService.getLocationByLocationId(specificName);
                this.searchLocationMapService.createSearchFavoriteLocation(requestMember, location);
            } else if (searchType == 3) {
                HashTag hashTag = this.hashTagService.gethashTag(specificName);
                this.searchHashTagMapService.createSearchFavoriteHashTag(requestMember, hashTag);
            }
        }
    }

    @PostMapping("/favorite/deleteAll")
    @ResponseBody
    public void searchFavoriteDeleteByMember(Principal principal) {

        Member requestMember = this.memberService.getMemberByUsername(principal.getName());

        List<SearchMemberMap> memberMaps = this.searchMemberMapService.getSearchMemberMapsByMember(requestMember);
        for (SearchMemberMap memberMap : memberMaps) {
            this.searchMemberMapService.deleteSearchMemberMap(memberMap);
        }

        List<SearchLocationMap> locationMaps = this.searchLocationMapService.getSearchLocationMapsByMember(requestMember);
        for (SearchLocationMap locationMap : locationMaps) {
            this.searchLocationMapService.deleteSearchLocationMap(locationMap);
        }

        List<SearchHashTagMap> hashTagMaps = this.searchHashTagMapService.getSearchHashTagMapsByMember(requestMember);
        for (SearchHashTagMap hashTagMap : hashTagMaps) {
            this.searchHashTagMapService.deleteSearchHashTagMap(hashTagMap);
        }

    }

    @PostMapping("/favorite/delete/{searchDataType}")
    @ResponseBody
    public void searchFavoriteDelete(@PathVariable("searchDataType") String searchDataType) {

        String regex = "^(searchmember|searchlocation|searchhashtag)-([1-9][0-9]*)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(searchDataType);

        if (matcher.matches()) {
            String type = matcher.group(1); // "searchmember", "searchlocation", "searchhashtag"
            Long number = Long.parseLong(matcher.group(2)); // 1, 2, 3, 4... (인덱스를 의미)
            if ("searchmember".equals(type)) {
                this.searchService.deleteSearchFavoriteList(type, number);
            } else if ("searchlocation".equals(type)) {
                this.searchService.deleteSearchFavoriteList(type, number);
            } else if ("searchhashtag".equals(type)) {
                this.searchService.deleteSearchFavoriteList(type, number);
            }
        }
    }
}
