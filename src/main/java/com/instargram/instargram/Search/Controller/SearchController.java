package com.instargram.instargram.Search.Controller;

import com.instargram.instargram.Search.Model.DTO.SearchDTO;
import com.instargram.instargram.Search.Service.SearchService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@Builder
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

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
}
