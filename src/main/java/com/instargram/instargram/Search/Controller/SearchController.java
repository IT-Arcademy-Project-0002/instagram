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
    public List<SearchDTO> keywordSearchJson(Model model, @RequestParam("keyword") String keyword) {

        List<SearchDTO> searchResult = this.searchService.searchResult(keyword);

        model.addAttribute("searchResult", searchResult);

        return searchResult;
    }
}
