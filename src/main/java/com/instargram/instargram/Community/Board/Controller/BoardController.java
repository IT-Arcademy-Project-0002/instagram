package com.instargram.instargram.Community.Board.Controller;

import com.instargram.instargram.Community.Board.Model.DTO.FeedListDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Like_Member_Map;
import com.instargram.instargram.Community.Board.Model.Form.BoardCreateForm;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Service.BoardService;
import com.instargram.instargram.Community.Board.Service.Board_Data_MapService;
import com.instargram.instargram.Community.Board.Service.Board_Like_Member_MapService;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final MemberService memberService;
    private final BoardService boardService;
    private final ImageService imageService;
    private final Board_Data_MapService boardDataMapService;
    private final Board_Like_Member_MapService boardLikeMemberMapService;

    @PostMapping("/board/create")
    public String create(@RequestParam("image-upload") List<MultipartFile> multipartFiles,
                         BoardCreateForm boardCreateForm, BindingResult bindingResult,
                         Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return "redirect:/main";
        }
        Member member = this.memberService.getMember(principal.getName());

        Board board = this.boardService.create(member, boardCreateForm.getContent());

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                String currName = multipartFile.getOriginalFilename();
                assert currName != null;

                int lastDotIndex = currName.lastIndexOf('.');
                String nameWithoutExtension = currName;

                if (lastDotIndex != -1) {
                    nameWithoutExtension = currName.substring(0, lastDotIndex);
                }

                String[] type = Objects.requireNonNull(multipartFile.getContentType()).split("/");
                if (!type[type.length - 1].equals("octet-stream")) {
                    String fileExtension = type[type.length - 1];
                    Image image = this.imageService.saveImage(multipartFile, nameWithoutExtension, fileExtension);
                    this.boardDataMapService.create(board, image, Enum_Data.IMAGE.getNumber());
                }
            }
        }
        return "redirect:/main";
    }

    // 좋아요
    @GetMapping("/board/like/{id}")
    public String like(@PathVariable("id") Long id, Principal principal) {
        Board board = this.boardService.getBoardById(id);
        Member member = this.memberService.getMember(principal.getName());

        Board_Like_Member_Map isBoardMemberLiked = this.boardLikeMemberMapService.exists(board, member);

        if (isBoardMemberLiked == null) {
            this.boardLikeMemberMapService.create(board, member);
        }else{
            this.boardLikeMemberMapService.delete(isBoardMemberLiked);
        }
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String create(Model model){
        List<Board> boardList = this.boardService.getBoard();
        List<FeedListDTO> feedList = this.boardDataMapService.getFeed(boardList);
        model.addAttribute("feedList",  feedList);
        return "Board/board_main";
    }
}
