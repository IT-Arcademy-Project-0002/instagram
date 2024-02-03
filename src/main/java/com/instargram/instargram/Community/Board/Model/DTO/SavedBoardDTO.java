package com.instargram.instargram.Community.Board.Model.DTO;

import com.instargram.instargram.Community.Board.Service.Board_Data_MapService;
import com.instargram.instargram.Community.Board.Service.Board_Save_MapService;
import com.instargram.instargram.Community.SaveGroup.Model.Entity.SaveGroup;
import com.instargram.instargram.Data.FileDTO;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.FollowMapService;
import com.instargram.instargram.Story.Model.Entity.Story_Data_Map;
import com.instargram.instargram.Story.Service.StoryHighlightMapService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SavedBoardDTO {
    private Map<SaveGroup, List<FileDTO>> savedGroupFiles;
    private List<Member> followers;
    private List<Member> followings;
    private Map<String, List<Story_Data_Map>> stories;

    // 내가 팔로우 상태인지
    boolean follow;
    // 내 팔로워인지
    boolean follower;
    // 팔로우 요청 상태인지
    boolean requestFollow;

    public SavedBoardDTO(Member target, Member loginMember, FollowMapService followMapService,
                         StoryHighlightMapService storyHighlightMapService,
                         Board_Data_MapService boardDataMapService, Board_Save_MapService boardSaveMapService)
    {
        this.followers = followMapService.getFollowers(target);

        this.followings = followMapService.getFollowings(target);

        this.stories = storyHighlightMapService.getStories(target);


        this.follow = followMapService.isFollow(loginMember, target);
        this.follower = followMapService.isFollower(loginMember, target);
        this.requestFollow = followMapService.isRequestFollow(loginMember, target);


        this.savedGroupFiles = boardDataMapService.getSavedGroupFileList(boardSaveMapService.getSavedBoardMapByMember(target));
    }
}
