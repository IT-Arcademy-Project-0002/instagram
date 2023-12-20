package com.instargram.instargram.Community.Board.Controller;

import com.instargram.instargram.Community.Board.Model.Form.BoardCreateForm;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Service.BoardService;
import com.instargram.instargram.Community.Board.Service.Board_Data_MapService;
import com.instargram.instargram.Config.AppConfig;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final MemberService memberService;
    private final BoardService boardService;
    private final ImageService imageService;
    private final Board_Data_MapService boardDataMapService;

    @PostMapping("/board/create")
    public String create(@RequestParam("image-upload") MultipartFile multipartFile, BoardCreateForm boardCreateForm, BindingResult bindingResult,
                         Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return "Board/board_main";
        }
        Member member = this.memberService.getMemberByUsername(principal.getName());

        Board board =  this.boardService.create(member, boardCreateForm.getContent());

        String currName = multipartFile.getOriginalFilename();
        assert currName != null;

        int lastDotIndex = currName.lastIndexOf('.');
        String nameWithoutExtension = currName;

        if (lastDotIndex != -1) {
            nameWithoutExtension = currName.substring(0, lastDotIndex);
        }
        Image image = new Image();
        String[] type = Objects.requireNonNull(multipartFile.getContentType()).split("/");
        if (!type[type.length - 1].equals("octet-stream")) {
            String fileExtension = type[type.length - 1];
            image = this.imageService.saveImage(multipartFile, nameWithoutExtension, fileExtension);
        }
        this.boardDataMapService.create(board, image, 2);
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String create(Model model){
        List<Board> boardList = this.boardService.getBoard();
        model.addAttribute("boardList", boardList);
        return "Board/board_main";
    }
}
