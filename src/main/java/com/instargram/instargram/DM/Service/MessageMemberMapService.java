package com.instargram.instargram.DM.Service;

import com.instargram.instargram.DM.Model.DTO.MessageDTO;
import com.instargram.instargram.DM.Model.Entity.Message.Message;
import com.instargram.instargram.DM.Model.Entity.Message.Message_Member_Map;
import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Model.Repository.MessageMemberMapRepository;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Data.Video.Video;
import com.instargram.instargram.Data.Video.VideoService;
import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Builder
public class MessageMemberMapService {

    private final MessageMemberMapRepository messageMemberMapRepository;
    private final MemberService memberService;
    private final MessageService messageService;
    private final ImageService imageService;
    private final VideoService videoService;

    public List<Message_Member_Map> getList(Room room)
    {
        return messageMemberMapRepository.findAllByRoomOrderByCreateDateAsc(room);
    }

    public void readMessageState(Room room, String username)
    {
        List<Message_Member_Map> a =getNotReadMessage(room, username);
        for(Message_Member_Map msg : a)
        {
            msg.setSeeMember(msg.getSeeMember() + ">" + username);

            messageMemberMapRepository.save(msg);
        }
    }

    public List<Message_Member_Map> getNotReadMessage(Room room, String name)
    {
        return messageMemberMapRepository.findByRoomAndMember_UsernameNotAndSeeMemberNotContainingOrderByCreateDateAsc(room, name, name);
    }

    public void createDefault(Message_Member_Map messageMemberMap, Room room, Map<String, Object> msg)
    {
        Member sender = memberService.getMember(msg.get("sender").toString());
        messageMemberMap.setMember(sender);
        messageMemberMap.setCreateDate(LocalDateTime.now());
        messageMemberMap.setRoom(room);
        messageMemberMap.setEmpathy(null);
        messageMemberMap.setSeeMember("");

        messageMemberMapRepository.save(messageMemberMap);
    }
    public void createMessageMap(Map<String, Object> msg, Message message, Room room)
    {
        Message_Member_Map messageMemberMap = new Message_Member_Map();
        messageMemberMap.setDataId(message.getId());
        messageMemberMap.setDataType(Enum_Data.MESSAGE.getNumber());

        createDefault(messageMemberMap, room, msg);
    }

    public void createImageMap(Map<String, Object> msg, Image image, Room room)
    {
        Message_Member_Map messageMemberMap = new Message_Member_Map();
        messageMemberMap.setDataId(image.getId());
        messageMemberMap.setDataType(Enum_Data.IMAGE.getNumber());

        createDefault(messageMemberMap, room, msg);
    }

    public void createMessage(Map<String, Object> msg, Room room)
    {
        Message message = messageService.create(msg);

        createMessageMap(msg, message, room);
    }

    public List<MessageDTO> getMessageDTOList(Room room)
    {
        List<MessageDTO> messageDTOS = new ArrayList<>();

        for(Message_Member_Map map : getList(room))
        {
            if(map.getDataType().equals(Enum_Data.MESSAGE.getNumber()))
            {
                Message message = messageService.getMessage(map.getDataId());
                messageDTOS.add(new MessageDTO(map, message));
            }
            else if(map.getDataType().equals(Enum_Data.IMAGE.getNumber()))
            {
                Image image = imageService.getImageByID(map.getDataId());
                messageDTOS.add(new MessageDTO(map, image));
            }
            else if(map.getDataType().equals(Enum_Data.VIDEO.getNumber()))
            {
                Video video = videoService.getVideoByID(map.getDataId());
                messageDTOS.add(new MessageDTO(map, video));
            }
        }

        return messageDTOS;
    }
}
