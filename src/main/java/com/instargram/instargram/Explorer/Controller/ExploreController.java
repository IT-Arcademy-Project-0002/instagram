package com.instargram.instargram.Explorer.Controller;

import com.instargram.instargram.Explorer.Model.DTO.ExploreDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Service.BoardService;
import com.instargram.instargram.Explorer.Service.ExploreService;
import com.instargram.instargram.Notice.Service.NoticeSSEService;
import com.instargram.instargram.Search.Service.SearchService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("")
    public String searchMain(Model model){

        List<Board> boardList = this.boardService.getBoard();
        List<ExploreDTO> exploreList = this.exploreService.initExplore(boardList);

        model.addAttribute("ExploreList", exploreList);

        this.noticeSseService.notifyUser(2L, "test message");

        return "Explore/explore";
    }

    @GetMapping("/location/{locationid}")
    public String searchLocation(@PathVariable Long locationid, Model model) {

        // 예시: model에 데이터 추가
        model.addAttribute("locationId", locationid);

        return "Explore/explore_location";
    }
}
