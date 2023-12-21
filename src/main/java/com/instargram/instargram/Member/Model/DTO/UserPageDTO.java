package com.instargram.instargram.Member.Model.DTO;

import com.instargram.instargram.Community.Board.Model.DTO.FeedListDTO;
import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Service.BoardService;
import com.instargram.instargram.Community.Board.Service.Board_Data_MapService;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.FollowMapService;
import com.instargram.instargram.Story.Model.Entity.Story_Data_Map;
import com.instargram.instargram.Story.Service.StoryDataMapService;
import com.instargram.instargram.Story.Service.StoryHighlightMapService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserPageDTO {

    private List<FeedListDTO> feeds;
    private List<Member> followers;
    private List<Member> followings;
    private Map<String, List<Story_Data_Map>> stories;

    public UserPageDTO(Member member, BoardService boardService, FollowMapService followMapService,
                       StoryHighlightMapService storyHighlightMapService,
                       Board_Data_MapService boardDataMapService)
    {
        feeds = boardDataMapService.getFeed(boardService.getBoardByMember(member));

        followers = followMapService.getFollowers(member);

        followings = followMapService.getFollowings(member);

        stories = storyHighlightMapService.getStories(member);
    }
}
