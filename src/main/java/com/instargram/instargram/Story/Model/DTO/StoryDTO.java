package com.instargram.instargram.Story.Model.DTO;

import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryDTO {
    private Integer dataType;
    private Long dataId;

    public StoryDTO(Integer dataType, Long dataId) {
        this.dataType = dataType;
        this.dataId = dataId;
    }
}
