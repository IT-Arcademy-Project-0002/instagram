package com.instargram.instargram.DM.Model.DTO;

import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RoomDTO {

    private Room room;

    private Map<String, Member> members;

    public RoomDTO(Room room, Map<String, Member> members)
    {
        this.room = room;
        this.members = members;
    }
}