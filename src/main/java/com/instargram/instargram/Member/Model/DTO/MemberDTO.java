package com.instargram.instargram.Member.Model.DTO;

import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageDTO;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Story.Model.DTO.StoryDTO;
import com.instargram.instargram.Story.Model.Entity.Story_Data_Map;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class MemberDTO {
    private Long id;
    private String username;
    private List<Long> storyDataMaps;
    private List<Image> images;
    private ImageDTO imageDTO;  // 프로필 이미지
    private boolean isFollowMemberStory;

    public MemberDTO(Member member) {
        id = member.getId();
        username = member.getUsername();

        if (member.getImage() != null) {
            imageDTO = new ImageDTO(member.getImage());
        }

        // images가 null이 아닌 경우에만 비교하도록 수정
        if (member.getStoryDataMaps() != null && images != null) {
            storyDataMaps = member.getStoryDataMaps().stream()
                    .filter(storyDataMap -> images.stream().anyMatch(image -> image.getId().equals(storyDataMap.getDataId())))
                    .map(Story_Data_Map::getDataId)
                    .collect(Collectors.toList());
        }
    }
}
