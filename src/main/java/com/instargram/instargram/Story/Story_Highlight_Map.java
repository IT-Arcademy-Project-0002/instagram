package com.instargram.instargram.Story;

import com.instargram.instargram.Community.SaveGroup.SaveGroup;
import com.instargram.instargram.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Story_Highlight_Map {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name="owner_id")
    Member owner;

    @ManyToOne
    @JoinColumn(name="story_id")
    Story_Data_Map storyDataMap;

    @ManyToOne
    @JoinColumn(name="group_id")
    SaveGroup saveGroup;

}
