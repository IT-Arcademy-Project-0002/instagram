package com.instargram.instargram.DM.Model.DTO;

import com.instargram.instargram.DM.Model.Entity.Message.CommentMessage;
import com.instargram.instargram.DM.Model.Entity.Message.Message;
import com.instargram.instargram.DM.Model.Entity.Message.Message_Member_Map;
import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Video.Video;
import com.instargram.instargram.Member.Model.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class MessageDTO {
    private Long id;

    private LocalDateTime createDate;

    private Integer dataType;

    private Long dataId;

    private String Empathy;

    private Room room;

    private Member member;

    private String seeMember;

    private Message message;

    private Image image;

    private Video video;

    private CommentMessage comment;

    private void setData(Message_Member_Map map)
    {
        this.id = map.getId();
        this.createDate = map.getCreateDate();
        this.Empathy = map.getEmpathy();
        this.room = map.getRoom();
        this.member = map.getMember();
        this.dataType = map.getDataType();

        if(map.getSeeMember().equals(""))
        {
            this.seeMember = "";
            return;
        }

        String[] names = map.getSeeMember().split(">");
        for(int i = 0; i < names.length; i++)
        {
            if(i != names.length -1)
            {
                this.seeMember = names[i] + "님,";
            }
            else{
                this.seeMember = names[i] + "님이 확인함";
            }
        }
    }

    public MessageDTO(Message_Member_Map map, Message message)
    {
        setData(map);
        this.message = message;
    }

    public MessageDTO(Message_Member_Map map, Image image)
    {
        setData(map);
        this.image = image;
    }

    public MessageDTO(Message_Member_Map map, Video video)
    {
        setData(map);
        this.video=video;
    }

    public MessageDTO(Message_Member_Map map, CommentMessage comment, Message message)
    {
        setData(map);
        this.comment = comment;
        this.message = message;
    }

    public MessageDTO(Message_Member_Map map, CommentMessage comment, Image image)
    {
        setData(map);
        this.comment = comment;
        this.image = image;
    }

    public MessageDTO(Message_Member_Map map, CommentMessage comment, Video video)
    {
        setData(map);
        this.comment = comment;
        this.video = video;
    }
}
