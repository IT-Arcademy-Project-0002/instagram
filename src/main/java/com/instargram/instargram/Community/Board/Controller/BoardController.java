package com.instargram.instargram.Community.Board.Controller;

import com.instargram.instargram.Community.Board.Model.DTO.FeedDTO;
import com.instargram.instargram.Community.Board.Model.DTO.FeedListDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Like_Member_Map;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Save_Map;
import com.instargram.instargram.Community.Board.Model.Form.BoardCreateForm;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Service.BoardService;
import com.instargram.instargram.Community.Board.Service.Board_Data_MapService;
import com.instargram.instargram.Community.Board.Service.Board_Like_Member_MapService;
import com.instargram.instargram.Community.Board.Service.Board_Save_MapService;
import com.instargram.instargram.Community.Location.Model.DTO.LocationDTO;
import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.Community.Location.Service.LocationService;
import com.instargram.instargram.Community.SaveGroup.Model.Entity.SaveGroup;
import com.instargram.instargram.Community.SaveGroup.Service.SaveGroupService;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final MemberService memberService;
    private final BoardService boardService;
    private final ImageService imageService;
    private final LocationService locationService;
    private final Board_Data_MapService boardDataMapService;
    private final Board_Like_Member_MapService boardLikeMemberMapService;
    private final SaveGroupService saveGroupService;
    private final Board_Save_MapService boardSaveMapService;

    @PostMapping("/board/create")
    public String create(@RequestParam("image-upload") List<MultipartFile> multipartFiles,
                         LocationDTO locationDTO, BoardCreateForm boardCreateForm, BindingResult bindingResult,
                         Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return "redirect:/main";
        }
        Member member = this.memberService.getMember(principal.getName());

        Location location = this.locationService.create(locationDTO);
        Board board = this.boardService.create(member, boardCreateForm.getContent(), location, boardCreateForm.isLikeHide(), boardCreateForm.isCommentDisable());

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
        } else {
            this.boardLikeMemberMapService.delete(isBoardMemberLiked);
        }

        return "redirect:/main";
    }

    @GetMapping("/main")
    public String create(Model model, Principal principal, HttpSession httpSession) {
        Member member = this.memberService.getMember(principal.getName());

        List<Board> memberBoards = this.boardService.getBoardsByMember(member);
        List<Long> followerIds = this.memberService.getFollower(member);
        List<Board> followerBoards = this.boardService.getBoardsByFollowerIds(followerIds);

        List<Board> allBoards = new ArrayList<>();
        allBoards.addAll(memberBoards);
        allBoards.addAll(followerBoards);

        List<FeedListDTO> feedList = this.boardDataMapService.getFeed(allBoards);
        FeedDTO selectFeed = (FeedDTO) httpSession.getAttribute("selectFeed");

        if (selectFeed != null) {
            model.addAttribute("selectFeed", selectFeed);
            httpSession.removeAttribute("selectFeed");
        }
        System.out.println("===========================>" + selectFeed);
        model.addAttribute("feedList", feedList);
        return "Board/board_main";
    }


    @GetMapping("/board/detail/{id}")
    public String detail(@PathVariable("id") Long id, HttpSession httpSession) {
        Board board = this.boardService.getBoardById(id);

        FeedDTO selectFeed = this.boardDataMapService.getFeedWithComments(board);
        httpSession.setAttribute("selectFeed", selectFeed);

        return "redirect:/main";
    }

    @PostMapping("/board/pin")
    public String boardPin(@RequestParam("board") Long id, Principal principal) {
        boardService.PinStateChange(id);
        return "redirect:/member/page/" + principal.getName();
    }

    @PostMapping("/board/keep")
    public String boardKeep(@RequestParam("keep") Long id, Principal principal) {
        boardService.KeepStateChange(id);
        return "redirect:/member/page/" + principal.getName();
    }

    @GetMapping("/board/likehide/{id}")
    public String boardLikeHide(@PathVariable("id") Long id) {
        this.boardService.LikeStateChange(id);
        return "redirect:/main";
    }

    @GetMapping("/board/comment_disable/{id}")
    public String boardCommentDisable(@PathVariable("id") Long id) {
        this.boardService.CommentDisableStateChange(id);
        return "redirect:/main";
    }

    @GetMapping("/beard/delete/{id}")
    public String boardDelete(@PathVariable("id") Long id) {
        Board board = this.boardService.getBoardById(id);
        boardService.delete(board);
        return "redirect:/main";
    }

    @GetMapping("/board/feedMemberInfo/{id}")
    public String boardFeedMemberInfo(@PathVariable("id") Long id) {
        Board board = this.boardService.getBoardById(id);

        if (board != null) {
            Member member = board.getMember();
            return "redirect:/member/page/" + member.getUsername();
        } else {
            return "redirect:/main";
        }
    }

    @GetMapping("/board/saveGroup/{boardId}")
    public String boardSave(@PathVariable("boardId") Long boardId, @RequestParam(value = "saveGroupId", required = false) Long saveGroupId, Principal principal) {
        SaveGroup saveGroup = null;
        if (saveGroupId != null) {
            saveGroup = saveGroupService.getGroupName(saveGroupId);
        }

        Board board = boardService.getBoardById(boardId);
        Member member = memberService.getMember(principal.getName());

        Board_Save_Map isBoardSave = this.boardSaveMapService.exists(board, member, saveGroup);

        if (saveGroup == null || isBoardSave == null) {
            if (isBoardSave == null) {
                this.boardSaveMapService.create(board, member, saveGroup);
            } else {
                this.boardSaveMapService.delete(isBoardSave);

            }
        }
        return "redirect:/main";
    }
}
