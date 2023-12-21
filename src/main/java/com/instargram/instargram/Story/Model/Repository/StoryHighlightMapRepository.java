package com.instargram.instargram.Story.Model.Repository;

import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Story.Model.Entity.Story_Highlight_Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryHighlightMapRepository extends JpaRepository<Story_Highlight_Map, Long> {

    List<Story_Highlight_Map> findByOwner(Member member);
}
