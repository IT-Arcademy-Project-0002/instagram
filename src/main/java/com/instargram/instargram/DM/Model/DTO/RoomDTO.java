package com.instargram.instargram.DM.Model.DTO;

import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.Member.Model.DTO.DMMemberDTO;
import com.instargram.instargram.Member.Model.Entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RoomDTO {

    private Room room;

    private String name = "";

    private Map<String, DMMemberDTO> members;

    private boolean group = false;

    private boolean block = false;

    public RoomDTO(Member loginUser, Room room, Map<String, DMMemberDTO> members) {
        this.room = room;
        this.members = members;


        for(Map.Entry<String, DMMemberDTO> member : members.entrySet())
        {
            if(!member.getKey().equals(loginUser.getUsername()))
            {
                this.name += member.getValue().getMember().getNickname().isEmpty() ? member.getValue().getMember().getUsername() : member.getValue().getMember().getNickname();
                this.name += " ";
            }

            if(member.getValue().isBlock())
            {
                this.block = true;
            }
        }

        if(members.size() > 2)
        {
            group = true;
        }
    }
}
