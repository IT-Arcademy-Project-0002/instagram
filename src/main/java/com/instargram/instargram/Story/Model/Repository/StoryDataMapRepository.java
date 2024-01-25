package com.instargram.instargram.Story.Model.Repository;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Story.Model.Entity.Story_Data_Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryDataMapRepository extends JpaRepository<Story_Data_Map, Long> {
    List<Story_Data_Map> findByOwner(Member member);
}
