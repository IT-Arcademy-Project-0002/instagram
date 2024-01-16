package com.instargram.instargram.Explorer.Controller;

import com.instargram.instargram.Community.Board.Model.Entity.Board_HashTag_Map;
import com.instargram.instargram.Community.Board.Service.BoardHashTagMapService;
import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Community.HashTag.Service.HashTagService;
import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.Community.Location.Service.LocationService;
import com.instargram.instargram.Explorer.Model.DTO.ExploreDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Service.BoardService;
import com.instargram.instargram.Explorer.Service.ExploreService;
import com.instargram.instargram.Notice.Service.NoticeSSEService;
import com.instargram.instargram.Search.Service.SearchService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@Builder
@RequestMapping("/explore")
public class ExploreController {

    private final BoardService boardService;
    private final ExploreService exploreService;
    private final SearchService searchService;
    private final NoticeSSEService noticeSseService;
    private final HashTagService hashTagService;
    private final BoardHashTagMapService boardHashTagMapService;
    private final LocationService locationService;

    @GetMapping("")
    public String searchMain(Model model){

        List<Board> boardList = this.boardService.getBoard();
        List<ExploreDTO> exploreList = this.exploreService.initExplore(boardList);

        model.addAttribute("ExploreList", exploreList);

        return "Explore/explore";
    }
    @GetMapping("/tags/{hashTagId}")
    public String searchHashTag(Model model, @PathVariable("hashTagId") Long hashTagId){

        HashTag hashtag =  this.hashTagService.getHashTagFindById(hashTagId);
        List<Board_HashTag_Map> boardHashTagMaps = this.boardHashTagMapService.getBoardFindByHashTag(hashtag);
        List<Board> boardList = this.boardService.findByBoardHashTagMaps(boardHashTagMaps);
        List<ExploreDTO> exploreHashTagList = this.exploreService.initExplore(boardList);

        model.addAttribute("exploreHashTagList", exploreHashTagList);

        return "Explore/explore_hashTag";
    }

    @GetMapping("/locations/{locationId}")
    public String searchLocation(Model model, @PathVariable("locationId") String locationId) {

        List<Location> location = this.locationService.getLocationFindById(locationId);
        List<Board> boardList = this.locationService.getBoardFindByLocation(location);
        List<ExploreDTO> exploreLocationList = this.exploreService.initExplore(boardList);

        model.addAttribute("location", location.get(0));
        model.addAttribute("exploreLocationList", exploreLocationList);

        return "Explore/explore_location";
    }

    @GetMapping(value = "/locations/locationMap", produces = "application/json")
    @ResponseBody
    public Location mapSearchLocation(@RequestParam("locationId") String locationId) {

        // 업로드 된 보드는 모두 동일한 장소를 가지기 때문에 1번 Index는 무조건 존재하게 된다. 따라서 get(0)
        return this.locationService.getLocationFindById(locationId).get(0);
    }

}

