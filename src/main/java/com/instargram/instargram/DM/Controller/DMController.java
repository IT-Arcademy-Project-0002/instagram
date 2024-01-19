package com.instargram.instargram.DM.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instargram.instargram.DM.Model.DTO.ChattingMemberDTO;
import com.instargram.instargram.DM.Model.DTO.MessageDTO;
import com.instargram.instargram.DM.Model.DTO.RoomDTO;
import com.instargram.instargram.DM.Model.Entity.Message.Message_Member_Map;
import com.instargram.instargram.DM.Model.Entity.Room.Room;
import com.instargram.instargram.DM.Service.MessageMemberMapService;
import com.instargram.instargram.DM.Service.RoomMemberMapService;
import com.instargram.instargram.DM.Service.RoomService;
import com.instargram.instargram.Data.DataDTO;
import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Image.ImageService;
import com.instargram.instargram.Data.Video.Video;
import com.instargram.instargram.Data.Video.VideoService;
import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.*;

@Controller
@Builder
@RequestMapping("/direct")
public class DMController {
    private final RoomService roomService;
    private final MemberService memberService;

    private final MessageMemberMapService messageMemberMapService;
    private final ImageService imageService;
    private final VideoService videoService;


    @GetMapping("")
    public String dmUrl(){
        return "Dm/dm";
    }

    @GetMapping("/inbox")
    public String dmList(Model model, Principal principal){

        List<RoomDTO> roomList = roomService.getRoomDTOList(memberService.getMember(principal.getName()));
        model.addAttribute("roomList",roomList);
        return "Dm/DirectInbox";
    }

    @GetMapping("/chatting")
    public String chatting(@RequestParam("member-search-input")String usernames,
                           Principal principal)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 배열을 List로 변환
            List<ChattingMemberDTO> userList = objectMapper.readValue(usernames, new TypeReference<List<ChattingMemberDTO>>() {});

            Member loginMember = memberService.getMember(principal.getName());

            List<Member> memberList = new ArrayList<>();
            for(ChattingMemberDTO dto : userList)
            {
                memberList.add(memberService.getMember(dto.getValue()));
            }

            Room room = roomService.findRoom(loginMember, memberList);

            if(room != null)
            {
                return "redirect:/direct/t/"+room.getId();
            }
            else{
                return "redirect:/direct/t/"+roomService.create(loginMember, memberList).getId();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/direct/create";
    }

    @GetMapping("/t/{id}")
    public String room(@PathVariable("id")Long id, Model model, Principal principal)
    {
        Member loginUSer = memberService.getMember(principal.getName());
        RoomDTO roomDTO = roomService.getRoomDTO(loginUSer, id);
        List<MessageDTO> messageList = roomService.getMessageDTOList(id);

        model.addAttribute("messageList", messageList);

        List<RoomDTO> roomList = roomService.getRoomDTOList(memberService.getMember(principal.getName()));
        model.addAttribute("roomList",roomList);
        model.addAttribute("nowRoomDTO",roomDTO);
        return "Dm/DirectRoom";
    }

    @PostMapping("/file/upload")
    public ResponseEntity<Map<String, Object>> createFile(@RequestParam("file-sender") List<MultipartFile> multipartFiles,
                                             @RequestParam("talkMsg") String talkMsgJson,
                                             Principal principal) throws IOException, NoSuchAlgorithmException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> talkMsg = objectMapper.readValue(talkMsgJson, new TypeReference<>() {});

        List<DataDTO> msgs = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                String currName = multipartFile.getOriginalFilename();
                assert currName != null;

                int lastDotIndex = currName.lastIndexOf('.');
                String nameWithoutExtension = currName;

                if (lastDotIndex != -1) {
                    nameWithoutExtension = currName.substring(0, lastDotIndex);
                }

                String[] type = Objects.requireNonNull(multipartFile.getContentType()).split("/");
                if (!type[type.length - 1].equals("octet-stream")) {
                    String fileExtension = type[type.length - 1];

                    if (fileExtension.equals("jpeg") || fileExtension.equals("png")) {
                        Image image = this.imageService.saveImage(multipartFile, nameWithoutExtension, fileExtension);
                        if (image != null) {
                            Room room = roomService.getRoom(Long.valueOf(talkMsg.get("roomId").toString()));
                            Message_Member_Map map = messageMemberMapService.createImageMap(talkMsg, image, room);
                            msgs.add(new DataDTO(Enum_Data.IMAGE.getNumber(), Objects.requireNonNull(image).getName(), map.getId()));
                        }
                    }
                    else if (fileExtension.equals("mp4")) {
                        Video video = this.videoService.saveVideo(multipartFile, nameWithoutExtension, fileExtension);
                        if (video != null) {
                            Room room = roomService.getRoom(Long.valueOf(talkMsg.get("roomId").toString()));
                            Message_Member_Map map = messageMemberMapService.createVideoMap(talkMsg, video, room);
                            msgs.add(new DataDTO(Enum_Data.VIDEO.getNumber(), Objects.requireNonNull(video).getName(),map.getId()));
                        }
                    }
                }
            }
        }

        Member sender =memberService.getMember(talkMsg.get("sender").toString());
        talkMsg.put("msg", msgs);
        talkMsg.put("userText", sender.getNickname().isEmpty()?sender.getUsername(): sender.getNickname());

        // 원하는 응답을 반환
        return ResponseEntity.ok().body(talkMsg);
    }

    @PostMapping("/message/create")
    public ResponseEntity<Map<String, Object>> create(
            @RequestBody Map<String, Object> talkMsg)
    {
        Room room = roomService.getRoom(Long.valueOf(talkMsg.get("roomId").toString()));
        Message_Member_Map map = messageMemberMapService.createMessage(talkMsg, room);
        talkMsg.put("id", map.getId());
        talkMsg.put("userText", map.getMember().getNickname().isEmpty()?map.getMember().getUsername():map.getMember().getNickname());
        talkMsg.put("commented", "false");
        return ResponseEntity.ok().body(talkMsg);
    }

    @PostMapping("/comment/create")
    public ResponseEntity<Map<String, Object>> commentCreate(
            @RequestBody Map<String, Object> talkMsg)
    {
        Room room = roomService.getRoom(Long.valueOf(talkMsg.get("roomId").toString()));
        Message_Member_Map map = messageMemberMapService.createCommentMessage(talkMsg, room);
        talkMsg.put("id", map.getId());

        talkMsg.put("userText", map.getMember().getNickname().isEmpty()?map.getMember().getUsername():map.getMember().getNickname());
        talkMsg.put("commented", "true");
        return ResponseEntity.ok().body(talkMsg);
    }


    @PostMapping("/quit")
    public ResponseEntity<Map<String, Object>> quit(
            @RequestBody Map<String, Object> quitMsg)
    {
        Map<String, Object> result = new HashMap<>();

        roomService.readMessageState(Long.valueOf(quitMsg.get("roomId").toString()),quitMsg.get("sender").toString());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/readMessage")
    public ResponseEntity<Map<String, Object>> readMessage(
            @RequestBody Map<String, Object> quitMsg, Principal principal)
    {
        Map<String, Object> result = new HashMap<>();

        roomService.readMessageState(Long.valueOf(quitMsg.get("roomId").toString()), principal.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteMap(@PathVariable("id")Long id)
    {
        Map<String, Object> result = messageMemberMapService.delete(id);

        return ResponseEntity.ok(result);
    }
}
