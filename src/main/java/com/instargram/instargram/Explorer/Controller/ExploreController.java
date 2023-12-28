package com.instargram.instargram.Explorer.Controller;

import com.instargram.instargram.Explorer.Model.DTO.ExploreDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Service.BoardService;
import com.instargram.instargram.Explorer.Service.ExploreService;
import com.instargram.instargram.Search.Model.DTO.CoordinatesDTO;
import com.instargram.instargram.Search.Service.SearchService;
import lombok.Builder;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("")
    public String searchUrl(Model model){

        List<Board> boardList = this.boardService.getBoard();
        List<ExploreDTO> exploreList = this.exploreService.initExplore(boardList);

        model.addAttribute("ExploreList", exploreList);

        return "Explore/explore";
    }
    @GetMapping("/location")
    public String location(Model model) throws JSONException {

        // 향후 ajax를 이용하여 실시간으로 단어를 입력하면
        // REST API로 검색된 장소의 5~10개 수준을 검색리스트에 포함시키는 로직 필요 (해당 작업은 Search 클래스에서 진행)

        CoordinatesDTO Bykeyword = this.searchService.getCoordinateByKeyword();

        model.addAttribute("ByKeyword", Bykeyword);

        return "Search/location";
    }

}
