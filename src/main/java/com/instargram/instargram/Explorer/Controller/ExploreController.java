package com.instargram.instargram.Explorer.Controller;

import com.instargram.instargram.Community.Board.Model.DTO.FeedDTO;
import com.instargram.instargram.Community.Board.Model.DTO.FeedListDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board_HashTag_Map;
import com.instargram.instargram.Community.Board.Model.Entity.Board_Save_Map;
import com.instargram.instargram.Community.Board.Model.Entity.Board_TagMember_Map;
import com.instargram.instargram.Community.Board.Model.Form.BoardUpdateForm;
import com.instargram.instargram.Community.Board.Service.*;
import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Community.HashTag.Service.HashTagService;
import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.Community.Location.Model.Form.LocationForm;
import com.instargram.instargram.Community.Location.Service.LocationService;
import com.instargram.instargram.Explorer.Model.DTO.ExploreDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Explorer.Service.ExploreService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import com.instargram.instargram.Notice.Service.NoticeSSEService;
import com.instargram.instargram.Search.Service.SearchService;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.*;

import static java.lang.Integer.parseInt;

@Controller
@Builder
@RequestMapping("/explore")
public class ExploreController {

    private final BoardService boardService;
    private final Board_Save_MapService boardSaveMapService;
    private final Board_Data_MapService boardDataMapService;
    private final MemberService memberService;
    private final ExploreService exploreService;
    private final SearchService searchService;
    private final NoticeSSEService noticeSseService;
    private final HashTagService hashTagService;
    private final BoardHashTagMapService boardHashTagMapService;
    private final Board_TagMember_MapService boardTagMemberMapService;
    private final LocationService locationService;

    @GetMapping("")
    public String exploreMain(Model model, Principal principal, HttpSession httpSession){

        Member member = this.memberService.getMember(principal.getName());

        List<Board> boardList = this.boardService.getBoard();
        List<Board> allBoards = new ArrayList<>(boardList);

        List<Board_Save_Map> FeedGroupName = this.boardSaveMapService.getSaveGroup(member);
        model.addAttribute("FeedGroupName" , FeedGroupName);

        List<FeedListDTO> feedList = this.boardDataMapService.getFeed(allBoards);
        FeedDTO selectFeed = (FeedDTO) httpSession.getAttribute("selectFeed");
        if (selectFeed != null) {
            model.addAttribute("selectFeed", selectFeed);
            httpSession.removeAttribute("selectFeed");
        }
        model.addAttribute("feedList", feedList);

        return "Explore/explore";
    }

    @GetMapping("/tags/{hashTagId}")
    public String searchHashTag(Model model, Principal principal, @PathVariable("hashTagId") Long hashTagId, HttpSession httpSession){

        Member member = this.memberService.getMember(principal.getName());

        HashTag hashtag =  this.hashTagService.getHashTagFindById(hashTagId);
        List<Board_HashTag_Map> boardHashTagMaps = this.boardHashTagMapService.getBoardFindByHashTag(hashtag);
        List<Board> boardList = this.boardService.findByBoardHashTagMaps(boardHashTagMaps);
        List<Board> allBoards = new ArrayList<>(boardList);

        List<Board_Save_Map> FeedGroupName = this.boardSaveMapService.getSaveGroup(member);
        model.addAttribute("FeedGroupName" , FeedGroupName);

        List<FeedListDTO> feedList = this.boardDataMapService.getFeed(allBoards);
        FeedDTO selectFeed = (FeedDTO) httpSession.getAttribute("selectFeed");
        if (selectFeed != null) {
            model.addAttribute("selectFeed", selectFeed);
            httpSession.removeAttribute("selectFeed");
        }

        model.addAttribute("hashTag", hashtag);
        model.addAttribute("feedList", feedList);

        String hashtagSize = this.exploreService.formatHashTagCount(feedList.size());
        model.addAttribute("hashTagSize", hashtagSize);

        return "Explore/explore_hashTag";
    }

    @GetMapping("/locations/{locationId}")
    public String searchLocation(Model model, Principal principal, @PathVariable("locationId") String locationId, HttpSession httpSession) {

        Member member = this.memberService.getMember(principal.getName());

        List<Location> location = this.locationService.getLocationListFindById(locationId);
        List<Board> boardList = this.locationService.getBoardFindByLocation(location);
        List<Board> allBoards = new ArrayList<>(boardList);

        List<Board_Save_Map> FeedGroupName = this.boardSaveMapService.getSaveGroup(member);
        model.addAttribute("FeedGroupName" , FeedGroupName);

        List<FeedListDTO> feedList = this.boardDataMapService.getFeed(allBoards);
        FeedDTO selectFeed = (FeedDTO) httpSession.getAttribute("selectFeed");
        if (selectFeed != null) {
            model.addAttribute("selectFeed", selectFeed);
            httpSession.removeAttribute("selectFeed");
        }

        model.addAttribute("location", location.get(0));
        model.addAttribute("feedList", feedList);

        return "Explore/explore_location";
    }

    @GetMapping(value = "/locations/locationMap", produces = "application/json")
    @ResponseBody
    public Location mapSearchLocation(@RequestParam("locationId") String locationId) {

        // 업로드 된 보드는 모두 동일한 장소를 가지기 때문에 1번 Index는 무조건 존재하게 된다. 따라서 get(0)
        return this.locationService.getLocationListFindById(locationId).get(0);
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, HttpSession httpSession) {
        Board board = this.boardService.getBoardById(id);

        FeedDTO selectFeed = this.boardDataMapService.getFeedWithComments(board);
        httpSession.setAttribute("selectFeed", selectFeed);

        return "redirect:/explore";
    }

    @GetMapping("/location/detail/{id}")
    public String detailLocation(@PathVariable("id") Long id, HttpSession httpSession) {
        Board board = this.boardService.getBoardById(id);
        Integer locationId = parseInt(board.getLocation().getLocationId());

        FeedDTO selectFeed = this.boardDataMapService.getFeedWithComments(board);
        httpSession.setAttribute("selectFeed", selectFeed);

        String redirectPath = String.format("/explore/locations/%d", locationId);

        return "redirect:" + redirectPath;
    }

    @GetMapping("/hashtag/detail/{id}")
    public String detailHashTag(@PathVariable("id") Long id, @RequestParam("hid") Integer hid, HttpSession httpSession) {
        Board board = this.boardService.getBoardById(id);

        FeedDTO selectFeed = this.boardDataMapService.getFeedWithComments(board);
        httpSession.setAttribute("selectFeed", selectFeed);

        String redirectPath = String.format("/explore/tags/%d", hid);

        return "redirect:" + redirectPath;
    }

    // board Update
    @GetMapping("/board/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();

        Board board = this.boardService.getBoardById(id);
        FeedDTO updateFeed = this.boardDataMapService.getFeedWithComments(board);
        result.put("updateFeed", updateFeed);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/board/update/{id}")
    public String update(@PathVariable("id") Long id, LocationForm locationForm, BindingResult bindingResult, BoardUpdateForm boardUpdateForm) {
        if (bindingResult.hasErrors()) {
            return "redirect:/explore";
        }

        // targetLocation이 내가 사용했던 장소임. locationForm이 내가 이번에 입력한 것.
        Board board = this.boardService.getBoardById(id);
        Location targetLocation = board.getLocation();

        Location islocation = this.locationService.exists(locationForm.getModifyLocationId());
        Location location;
        if (islocation == null) {
            location = this.locationService.createNewLocation(locationForm);
        } else {
            location = this.locationService.getLocationByLocationId(locationForm.getModifyLocationId());
        }

        // Location이 필요한 경우 명시적으로 저장 또는 병합
        if (location.getId() == null) {
            this.boardService.modify(board, null, boardUpdateForm.getModifyContent(), boardUpdateForm.isModifyLikeHide(), boardUpdateForm.isModifyCommentDisable());
        } else {
            this.boardService.modify(board, location, boardUpdateForm.getModifyContent(), boardUpdateForm.isModifyLikeHide(), boardUpdateForm.isModifyCommentDisable());
        }

        // # HashTag
        List<String> hashTagsList = Collections.emptyList();  // 기본적으로 빈 리스트로 초기화
        this.boardHashTagMapService.delete(board);

        if (boardUpdateForm.getModifyHashTag() != null) {
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

        // 등록했던 장소가 존재하며(null이 아니며) 수정 후의 장소를 어떤 보드에서도 참조하지 않을 때 삭제
        if (targetLocation != null) {
            this.locationService.uselessLocationDelete(targetLocation);
        }

        return "redirect:/explore";
    }

    // board Delete
    @GetMapping("/board/delete/{id}")
    public String exploreBoardDelete(@PathVariable("id") Long id) {
        Board board = this.boardService.getBoardById(id);
        boardService.delete(board);
        Location location = board.getLocation();
        this.locationService.uselessLocationDelete(location);
        return "redirect:/explore";
    }

}

