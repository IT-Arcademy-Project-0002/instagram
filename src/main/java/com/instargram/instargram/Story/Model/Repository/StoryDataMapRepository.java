package com.instargram.instargram.Story.Model.Repository;

import com.instargram.instargram.Story.Model.Entity.Story_Data_Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryDataMapRepository extends JpaRepository<Story_Data_Map, Long> {
}
