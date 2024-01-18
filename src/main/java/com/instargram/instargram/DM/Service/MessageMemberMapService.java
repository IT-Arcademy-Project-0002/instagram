package com.instargram.instargram.DM.Service;

import com.instargram.instargram.Community.Comment.Model.Entity.Comment;
import com.instargram.instargram.DM.Model.DTO.MessageDTO;
import com.instargram.instargram.DM.Model.Entity.Message.CommentMessage;
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

    public Message_Member_Map getMap(Long id)
    {
        return messageMemberMapRepository.findById(id).orElse(null);
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

    public Message_Member_Map createDefault(Message_Member_Map messageMemberMap, Room room, Map<String, Object> msg)
    {
        Member sender = memberService.getMember(msg.get("sender").toString());
        messageMemberMap.setMember(sender);
        messageMemberMap.setCreateDate(LocalDateTime.now());
        messageMemberMap.setRoom(room);
        messageMemberMap.setEmpathy("");
        messageMemberMap.setSeeMember("");

        return messageMemberMapRepository.save(messageMemberMap);
    }
    public Message_Member_Map createMessageMap(Map<String, Object> msg, Message message, Room room)
    {
        Message_Member_Map messageMemberMap = new Message_Member_Map();
        messageMemberMap.setDataId(message.getId());
        messageMemberMap.setDataType(Enum_Data.MESSAGE.getNumber());
        messageMemberMap.setComment(false);

        return createDefault(messageMemberMap, room, msg);
    }

    public Message_Member_Map createCommentMessageMap(Map<String, Object> msg, CommentMessage commentMessage, Room room)
    {
        Message_Member_Map messageMemberMap = new Message_Member_Map();
        messageMemberMap.setDataId(commentMessage.getMessage().getId());
        messageMemberMap.setDataType(Enum_Data.MESSAGE.getNumber());
        messageMemberMap.setComment(true);

        return createDefault(messageMemberMap, room, msg);
    }

    public Message_Member_Map createImageMap(Map<String, Object> msg, Image image, Room room)
    {
        Message_Member_Map messageMemberMap = new Message_Member_Map();
        messageMemberMap.setDataId(image.getId());
        messageMemberMap.setDataType(Enum_Data.IMAGE.getNumber());
        messageMemberMap.setComment(false);

        return createDefault(messageMemberMap, room, msg);
    }

    public Message_Member_Map createVideoMap(Map<String, Object> msg, Video video, Room room)
    {
        Message_Member_Map messageMemberMap = new Message_Member_Map();
        messageMemberMap.setDataId(video.getId());
        messageMemberMap.setDataType(Enum_Data.VIDEO.getNumber());
        messageMemberMap.setComment(false);

        return createDefault(messageMemberMap, room, msg);
    }

    public Message_Member_Map createMessage(Map<String, Object> msg, Room room)
    {
        Message message = messageService.create(msg);

        return createMessageMap(msg, message, room);
    }

    public Message_Member_Map createCommentMessage(Map<String, Object>msg, Room room)
    {
        Message_Member_Map map = getMap(Long.valueOf(msg.get("mapId").toString()));
        CommentMessage comment = messageService.createComment(msg, map);

        msg.put("commentUserText",
                map.getMember().getNickname().isEmpty()?
                        map.getMember().getUsername() :
                        map.getMember().getNickname());
        msg.put("commentUsername", map.getMember().getUsername());

        if(Objects.equals(map.getDataType(), Enum_Data.MESSAGE.getNumber()))
        {
            msg.put("commentDataType", Enum_Data.MESSAGE.getNumber());
            msg.put("commentContent", messageService.getMessage(map.getDataId()).getContent());
        }
        else if(Objects.equals(map.getDataType(), Enum_Data.IMAGE.getNumber()))
        {
            msg.put("commentDataType", Enum_Data.IMAGE.getNumber());
            msg.put("commentContent", imageService.getImageByID(map.getDataId()).getName());
        }
        else if(Objects.equals(map.getDataType(), Enum_Data.VIDEO.getNumber()))
        {
            msg.put("commentDataType", Enum_Data.VIDEO.getNumber());
            msg.put("commentContent", videoService.getVideoByID(map.getDataId()).getName());
        }

        return createCommentMessageMap(msg, comment, room);
    }

    public List<MessageDTO> getMessageDTOList(Room room)
    {
        List<MessageDTO> messageDTOS = new ArrayList<>();

        for(Message_Member_Map map : getList(room))
        {
            if(map.getDataType().equals(Enum_Data.MESSAGE.getNumber()) &&!map.isComment())
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
            else if(map.getDataType().equals(Enum_Data.MESSAGE.getNumber()) && map.isComment())
            {
                CommentMessage commentMessage = messageService.getCommentMessage(map.getDataId());

                Message_Member_Map commentMap = commentMessage.getMessageMap();
                if(commentMap.getDataType().equals(Enum_Data.MESSAGE.getNumber()))
                {
                    Message message = messageService.getMessage(commentMap.getDataId());
                    messageDTOS.add(new MessageDTO(map, commentMessage, message));
                }
                else if(commentMap.getDataType().equals(Enum_Data.IMAGE.getNumber()))
                {
                    Image image = imageService.getImageByID(commentMap.getDataId());
                    messageDTOS.add(new MessageDTO(map, commentMessage, image));
                }
                else if(commentMap.getDataType().equals(Enum_Data.VIDEO.getNumber()))
                {
                    Video video = videoService.getVideoByID(commentMap.getDataId());
                    messageDTOS.add(new MessageDTO(map, commentMessage, video));
                }
            }
        }

        return messageDTOS;
    }

    public void delete(Long id)
    {
        Message_Member_Map map = getMap(id);

        if(map != null)
        {
            if(Objects.equals(map.getDataType(), Enum_Data.MESSAGE.getNumber()))
            {
                messageService.delete(map.getDataId());
            }
            else if(Objects.equals(map.getDataType(), Enum_Data.IMAGE.getNumber()))
            {
                imageService.deleteImage(map.getDataId());
            }
            else if(Objects.equals(map.getDataType(), Enum_Data.VIDEO.getNumber()))
            {
                videoService.delete(map.getDataId());
            }
        }

        messageMemberMapRepository.deleteById(id);
    }
}
