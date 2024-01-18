package com.instargram.instargram.Search.Controller;

import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Community.HashTag.Service.HashTagService;
import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.Community.Location.Service.LocationService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import com.instargram.instargram.Search.Model.DTO.SearchDTO;
import com.instargram.instargram.Search.Service.SearchService;

import lombok.Builder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Builder
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;
    private final MemberService memberService;
    private final LocationService locationService;
    private final HashTagService hashTagService;

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

    @GetMapping("/favorite/list")
    public void favoriteList() {
        // ajax로 해야되나? 알림페이지와 동일한 메커니즘
    }

    @PostMapping("/favorite/create")
    @ResponseBody
    public void favoriteCreate(Principal principal,
                               @RequestBody Map<String, Object> data) {

        Member requestMember = this.memberService.getMemberByUsername(principal.getName());

        Integer searchType = (Integer) data.get("favoriteType");
        String specificName = (String) data.get("specificName");

        if (searchType != null) {
            if (searchType == 1) {
                Member member = this.memberService.getMemberByUsername(specificName);
                this.searchService.createSearchFavoriteMember(requestMember, member);
            } else if (searchType == 2) {
                Location location = this.locationService.getLocationByLocationId(specificName);
                this.searchService.createSearchFavoriteLocation(requestMember, location);
            } else if (searchType == 3) {
                HashTag hashTag = this.hashTagService.gethashTag(specificName);
                this.searchService.createSearchFavoriteHashTag(requestMember, hashTag);
            }
        }
    }
}
