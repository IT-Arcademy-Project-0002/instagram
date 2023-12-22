package com.instargram.instargram.Explorer.Controller;

import com.instargram.instargram.Explorer.Model.DTO.ExploreDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Service.BoardService;
import com.instargram.instargram.Explorer.Service.ExploreService;
import lombok.Builder;
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

    @GetMapping("")
    public String searchUrl(Model model){

        List<Board> boardList = this.boardService.getBoard();
        List<ExploreDTO> exploreList = this.exploreService.initExplore(boardList);

        System.out.println(exploreList);
        model.addAttribute("ExploreList", exploreList);

        return "Explore/explore";
    }
}
