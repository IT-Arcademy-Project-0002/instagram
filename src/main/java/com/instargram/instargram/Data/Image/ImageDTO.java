package com.instargram.instargram.Data.Image;

import com.instargram.instargram.Config.AppConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
    private Long id;
    private String path;
    private String name;

    public ImageDTO(Image image){
        if (image != null) {
            id = image.getId();
            path = image.getPath();
            name = image.getName();
        }else{
            id = null;
            path = null;
            name = null;
        }
    }
}
