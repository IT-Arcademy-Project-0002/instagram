package com.instargram.instargram.Story.Service;

import com.instargram.instargram.Story.Model.Repository.StoryDataMapRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
@Builder
public class StoryDataMapService {

    private final StoryDataMapRepository storyDataMapRepository;
}
