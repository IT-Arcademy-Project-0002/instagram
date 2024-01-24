package com.instargram.instargram.Story.Service;

import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Video.Video;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Story.Model.Entity.Story_Data_Map;
import com.instargram.instargram.Story.Model.Repository.StoryDataMapRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Builder
public class StoryDataMapService {

    private final StoryDataMapRepository storyDataMapRepository;

    public Story_Data_Map create(Image image, Integer data_type, Member member) {
        Story_Data_Map storyDataMap = new Story_Data_Map();
        storyDataMap.setDataId(image.getId());
        storyDataMap.setDataType(data_type);
        storyDataMap.setOwner(member);

        return this.storyDataMapRepository.save(storyDataMap);
    }

    public Story_Data_Map create(Video video, Integer data_type, Member member) {
        Story_Data_Map storyDataMap = new Story_Data_Map();
        storyDataMap.setDataId(video.getId());
        storyDataMap.setDataType(data_type);
        storyDataMap.setOwner(member);

        return this.storyDataMapRepository.save(storyDataMap);
    }

    public List<Story_Data_Map> getStoryList(Member member) {
        return storyDataMapRepository.findByOwner(member);
    }

    public Story_Data_Map hasStory(Member member) {
        List<Story_Data_Map> storyList = getStoryList(member);
        return storyList.isEmpty() ? null : storyList.get(0);
    }
}
