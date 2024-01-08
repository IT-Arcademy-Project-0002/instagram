package com.instargram.instargram.Data;

import com.instargram.instargram.Data.Image.Image;
import com.instargram.instargram.Data.Video.Video;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FileDTO {
    private Long id;
    private String path;
    private String name;

    public FileDTO(Image image, Video video) {
        if (image != null) {
            id = image.getId();
            path = image.getPath();
            name = image.getName();
        }else{
            id = video.getId();
            path = video.getPath();
            name = video.getName();
        }
    }
}
