package com.instargram.instargram.Community.Board.Controller;

import com.instargram.instargram.Community.Board.Model.DTO.FeedDTO;
import com.instargram.instargram.Community.Board.Model.DTO.FeedListDTO;
import com.instargram.instargram.Community.Board.Model.Entity.*;
import com.instargram.instargram.Community.Board.Model.Form.BoardCreateForm;
import com.instargram.instargram.Community.Board.Model.Form.BoardUpdateForm;
import com.instargram.instargram.Community.Board.Service.*;
import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Community.HashTag.Service.HashTagService;
import com.instargram.instargram.Community.Location.Model.DTO.LocationDTO;
import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.Community.Location.Model.Form.LocationForm;
import com.instargram.instargram.Community.Location.Service.LocationService;
import com.instargram.instargram.Community.SaveGroup.Model.Entity.SaveGroup;
import com.instargram.instargram.Community.SaveGroup.Service.SaveGroupService;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Data.Video.Video;
import com.instargram.instargram.Data.Video.VideoService;
import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import com.instargram.instargram.Notice.Model.Entity.Notice;
import com.instargram.instargram.Notice.Service.NoticeBoardMapService;
import com.instargram.instargram.Notice.Service.NoticeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final MemberService memberService;
    private final BoardService boardService;
    private final ImageService imageService;
    private final VideoService videoService;
    private final LocationService locationService;
    private final Board_Data_MapService boardDataMapService;
    private final BoardLikeMemberMapService boardLikeMemberMapService;
    private final SaveGroupService saveGroupService;
    private final Board_Save_MapService boardSaveMapService;
    private final NoticeService noticeService;
    private final Board_TagMember_MapService boardTagMemberMapService;
    private final NoticeBoardMapService noticeBoardMapService;
    private final HashTagService hashTagService;
    private final BoardHashTagMapService boardHashTagMapService;

    // main
    @GetMapping("/main")
    public String boardMain(Model model, Principal principal, HttpSession httpSession) {
        Member member = this.memberService.getMember(principal.getName());

        List<Board> memberBoards = this.boardService.getBoardsByMember(member);
        List<Long> followerIdList = this.memberService.getFollowing(member);
        List<Board> followerBoards = this.boardService.getBoardsByFollowerIds(followerIdList);

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


    // create board
    @PostMapping("/board/create")
    public String create(@RequestParam("file-upload") List<MultipartFile> multipartFiles,
                         LocationDTO locationDTO, BoardCreateForm boardCreateForm, BindingResult bindingResult,
                         Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return "redirect:/main";
        }
        Member member = this.memberService.getMember(principal.getName());

        Location location = this.locationService.create(locationDTO);

        Board board = this.boardService.create(member, boardCreateForm.getContent(), location, boardCreateForm.isLikeHide(), boardCreateForm.isCommentDisable());

        // # HashTag
        List<String> hashTagsList = this.hashTagService.extractHashTagWords(boardCreateForm.getHashTag());
        for (String hashTag_name : hashTagsList) {
            HashTag ishashTag = this.hashTagService.exists(hashTag_name);
            HashTag hashTag;
            if (ishashTag == null) {
                hashTag = this.hashTagService.create(hashTag_name);
            } else {
                hashTag = this.hashTagService.gethashTag(hashTag_name);
            }
            this.boardHashTagMapService.createBoardHashTag(board, hashTag);
        }

        // @ Mention
        List<String> tagMemberList = this.boardTagMemberMapService.extractMentionedWords(boardCreateForm.getTagMember());
        for (String memberMap : tagMemberList) {
            Member tagMember = this.memberService.getMember(memberMap);
            Board_TagMember_Map boardTagMemberMap = this.boardTagMemberMapService.create(board, tagMember);
            if (member != tagMember) {
                Notice notice = this.noticeService.createNotice(Enum_Data.BOARD_TAGMEMBER.getNumber(), member, tagMember);
                noticeBoardMapService.createNoticeBoardTagMember(boardTagMemberMap, notice);
            }
        }

        // file upload
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
                    if (fileExtension.equals("jpeg")) {
                        Image image = this.imageService.saveImage(multipartFile, nameWithoutExtension, fileExtension);
                        this.boardDataMapService.create(board, image, Enum_Data.IMAGE.getNumber());
                    } else if (fileExtension.equals("png")) {
                        Image image = this.imageService.saveImage(multipartFile, nameWithoutExtension, fileExtension);
                        this.boardDataMapService.create(board, image, Enum_Data.IMAGE.getNumber());
                    } else if (fileExtension.equals("mp4")) {
                        Video video = this.videoService.saveVideo(multipartFile, nameWithoutExtension, fileExtension);
                        this.boardDataMapService.create(board, video, Enum_Data.VIDEO.getNumber());
                    }
                }
            }
        }
        return "redirect:/main";
    }

    @GetMapping("/board/detail/{id}")
    public String detail(@PathVariable("id") Long id, HttpSession httpSession) {
        Board board = this.boardService.getBoardById(id);

        FeedDTO selectFeed = this.boardDataMapService.getFeedWithComments(board);
        httpSession.setAttribute("selectFeed", selectFeed);

        return "redirect:/main";
    }

    // board pin
    @PostMapping("/board/pin")
    public String boardPin(@RequestParam("board") Long id, Principal principal) {
        boardService.PinStateChange(id);
        return "redirect:/member/page/" + principal.getName();
    }

    // board keep
    @PostMapping("/board/keep")
    public String boardKeep(@RequestParam("keep") Long id, Principal principal) {
        boardService.KeepStateChange(id);
        return "redirect:/member/page/" + principal.getName();
    }

    // board like Hidden
//    @GetMapping("/board/likehide/{id}")
//    public String boardLikeHide(@PathVariable("id") Long id) {
//        this.boardService.LikeStateChange(id);
//        return "redirect:/main";
//    }

    @GetMapping("/board/likehide/{id}")
    public ResponseEntity<Map<String, Object>> boardLikeHide(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        this.boardService.LikeStateChange(id);

        Board board = this.boardService.getBoardById(id);
        if(!board.isLikeHide()){
            int likeCount = this.boardLikeMemberMapService.countLikesForBoard(board);

            result.put("likehide", false);
            result.put("likeCount", likeCount);
        }else{
            result.put("likehide", true);
        }
        return ResponseEntity.ok().body(result);
    }

    // board Comment disable
    @GetMapping("/board/comment_disable/{id}")
    public String boardCommentDisable(@PathVariable("id") Long id) {
        this.boardService.CommentDisableStateChange(id);
        return "redirect:/main";
    }

    // board Delete
    @GetMapping("/beard/delete/{id}")
    public String boardDelete(@PathVariable("id") Long id) {
        Board board = this.boardService.getBoardById(id);
        boardService.delete(board);
        return "redirect:/main";
    }

    // board Update
    @GetMapping("/board/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, HttpSession httpSession) {
        Map<String, Object> result = new HashMap<>();

        Board board = this.boardService.getBoardById(id);
        FeedDTO updateFeed = this.boardDataMapService.getFeedWithComments(board);
        result.put("updateFeed", updateFeed);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/board/update/{id}")
    public String update(@PathVariable("id") Long id, LocationForm locationForm, BindingResult bindingResult, BoardUpdateForm boardUpdateForm) {
        if (bindingResult.hasErrors()) {
            return "redirect:/main";
        }

        Board board = this.boardService.getBoardById(id);
        Location tagetLocation = (board.getLocation() != null) ? board.getLocation() : new Location();

        Location location = this.locationService.modify(tagetLocation, locationForm, locationForm.getModifyLocationId(), locationForm.getModifyPlaceName(), locationForm.getModifyAddressName(),
                locationForm.getModifyRoadAddressName(), locationForm.getModify_x(), locationForm.getModify_y());

        // Location이 필요한 경우 명시적으로 저장 또는 병합
        if (location.getId() == null) {
            this.boardService.modify(board, null, boardUpdateForm.getModifyContent(), boardUpdateForm.isModifyLikeHide(), boardUpdateForm.isModifyCommentDisable());
        } else {
            this.boardService.modify(board, location, boardUpdateForm.getModifyContent(), boardUpdateForm.isModifyLikeHide(), boardUpdateForm.isModifyCommentDisable());
        }


        // # HashTag
        List<String> hashTagsList = Collections.emptyList();  // 기본적으로 빈 리스트로 초기화
        this.boardHashTagMapService.delete(board);

        if(boardUpdateForm.getModifyHashTag() != null){
            hashTagsList = this.hashTagService.extractHashTagWords(boardUpdateForm.getModifyHashTag());
        }
        for (String hashTag_name : hashTagsList) {
            HashTag ishashTag = this.hashTagService.exists(hashTag_name);
            HashTag hashTag;
            if (ishashTag == null) {
                hashTag = this.hashTagService.create(hashTag_name);
            } else {
                hashTag = this.hashTagService.gethashTag(hashTag_name);
            }
            this.boardHashTagMapService.createBoardHashTag(board, hashTag);
        }
       
       // @ mention
        List<String> tagMemberList = Collections.emptyList();
        this.boardTagMemberMapService.delete(board);

        if (boardUpdateForm.getModifyTagMember() != null) {
            tagMemberList = this.boardTagMemberMapService.extractMentionedWords(boardUpdateForm.getModifyTagMember());
        }
        // @ Mention  언급 재설정
        for (String memberMap : tagMemberList) {
            Member tagMember = this.memberService.getMember(memberMap);
            this.boardTagMemberMapService.create(board, tagMember);
        }
        return "redirect:/main";
    }

    // board UserInfo
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

    // board like
    @GetMapping("/board/like/{id}")
    public ResponseEntity<Map<String, Object>> like(@PathVariable("id") Long id, Principal principal) {
        Map<String, Object> result = new HashMap<>();

        Board board = this.boardService.getBoardById(id);
        Member member = this.memberService.getMember(principal.getName());

        BoardLikeMemberMap isBoardMemberLiked = this.boardLikeMemberMapService.exists(board, member);

        if (isBoardMemberLiked == null) {
            BoardLikeMemberMap boardLikeMemberMap = this.boardLikeMemberMapService.create(board, member);

            if (member != board.getMember()) {
                Notice notice = this.noticeService.createNotice(Enum_Data.BOARD_LIKE.getNumber(), member, board.getMember());
                this.noticeBoardMapService.createNoticeBoardLikeMember(boardLikeMemberMap, notice);
            }


            int likeCount = this.boardLikeMemberMapService.countLikesForBoard(board);

            result.put("result", true);
            result.put("likeCount", likeCount);
        } else {
            this.boardLikeMemberMapService.delete(isBoardMemberLiked);

            int likeCount = this.boardLikeMemberMapService.countLikesForBoard(board);
            result.put("result", false);
            result.put("likeCount", likeCount);
        }
        result.put("like_hide", board.isLikeHide());
        return ResponseEntity.ok().body(result);
    }


    // board SaveGroup
    @GetMapping("/board/saveGroup/{boardId}")
    public ResponseEntity<Map<String, Object>> boardSave(@PathVariable("boardId") Long boardId, @RequestParam(value = "saveGroupId", required = false) Long saveGroupId, Principal principal) {
        Map<String, Object> result = new HashMap<>();

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
                result.put("result", true);
            } else {
                this.boardSaveMapService.delete(isBoardSave);
                result.put("result", false);
            }
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/board/search/{kw}")
    public ResponseEntity<Map<String, List<Member>>> memberSearch(@PathVariable("kw") String kw) {
        Map<String, List<Member>> result = new HashMap<>();

        result.put("result", memberService.searchMemberList(kw));

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/board/search/HashTag/{kw}")
    public ResponseEntity<Map<String, List<HashTag>>> hashTagSearch(@PathVariable("kw") String kw) {
        Map<String, List<HashTag>> result = new HashMap<>();

        result.put("result", hashTagService.searchHashTagList(kw));

        return ResponseEntity.ok().body(result);
    }
}
