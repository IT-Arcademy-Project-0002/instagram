package com.instargram.instargram.Story.Service;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Story.Model.Entity.Story_Data_Map;
import com.instargram.instargram.Story.Model.Entity.Story_Highlight_Map;
import com.instargram.instargram.Story.Model.Repository.StoryHighlightMapRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Builder
public class StoryHighlightMapService {

    private final StoryHighlightMapRepository storyHighlightMapRepository;

    public Map<String, List<Story_Data_Map>> getStories(Member member)
    {
        return storyHighlightMapRepository.findByOwner(member)
                .stream()
                .collect(Collectors.groupingBy(
                        storyHighlight -> storyHighlight.getSaveGroup().getName(),
                        Collectors.mapping(Story_Highlight_Map::getStoryData, Collectors.toList())
                ));
    }
}
