package com.instargram.instargram.Story.Controller;

import com.instargram.instargram.Data.Image.*;
import com.instargram.instargram.Data.Video.*;
import com.instargram.instargram.Enum_Data;
import com.instargram.instargram.Member.Model.Entity.Member;
import com.instargram.instargram.Member.Service.MemberService;
import com.instargram.instargram.Story.Service.StoryDataMapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/story")
public class StoryDataMapController {
    private final MemberService memberService;
    private final ImageService imageService;
    private final VideoService videoService;
    private final StoryDataMapService storyDataMapService;

    @PostMapping("/create")
    public String create(Principal principal, @RequestParam("story") MultipartFile file) throws IOException {
        Member member = this.memberService.getMember(principal.getName());

        if (!file.isEmpty()) {
            String currName = file.getOriginalFilename();
            assert currName != null;

            int lastDotIndex = currName.lastIndexOf('.');
            String nameWithoutExtension = currName;

            if (lastDotIndex != -1) {
                nameWithoutExtension = currName.substring(0, lastDotIndex);
            }

            String[] type = Objects.requireNonNull(file.getContentType()).split("/");
            if (!type[type.length - 1].equals("octet-stream")) {
                String fileExtension = type[type.length - 1];
                if (fileExtension.equals("jpeg") || fileExtension.equals("png")) {
                    Image image = this.imageService.saveImage(file, nameWithoutExtension, fileExtension);
                    this.storyDataMapService.create(image, Enum_Data.IMAGE.getNumber(), member);
                } else if (fileExtension.equals("mp4")) {
                    Video video = this.videoService.saveVideo(file, nameWithoutExtension, fileExtension);
                    this.storyDataMapService.create(video, Enum_Data.VIDEO.getNumber(), member);
                }
            }
        }
        return "redirect:/main";
    }
}
