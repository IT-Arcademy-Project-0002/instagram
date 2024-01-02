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
import java.util.Objects;

@Getter
@Setter
public class UserPageDTO {

    private List<FeedListDTO> feeds;
    private Integer feedSize;
    private List<Member> followers;
    private List<Member> followings;
    private Map<String, List<Story_Data_Map>> stories;
    // 내 계정인지
    boolean mine = false;
    // 내가 팔로우 상태인지
    boolean follow = false;
    // 내 팔로워인지
    boolean follower = false;
    // 팔로우 요청 상태인지
    boolean requestFollow = false;

    public UserPageDTO(Member target, Member loginMember, BoardService boardService, FollowMapService followMapService,
                       StoryHighlightMapService storyHighlightMapService,
                       Board_Data_MapService boardDataMapService)
    {
        followers = followMapService.getFollowers(target);

        followings = followMapService.getFollowings(target);

        stories = storyHighlightMapService.getStories(target);

        if(Objects.equals(target, loginMember))
        {
            mine = true;
        }
        else{
            follow = followMapService.isFollow(loginMember, target);
            follower = followMapService.isFollower(loginMember, target);
            requestFollow = followMapService.isRequestFollow(loginMember, target);
        }

        if(loginMember.isScope() || mine)
        {
            feeds = boardDataMapService.getFeed(boardService.getBoardByMember(target));
            feedSize = feeds.size();
        }
        else {
            feedSize = boardService.getSizeByMember(loginMember);
        }
    }
}
