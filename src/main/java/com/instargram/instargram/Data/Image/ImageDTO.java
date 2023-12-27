package com.instargram.instargram.Data.Image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
    private Long id;
    private String path;
    private String name;

    public ImageDTO(Image image){
        id = image.getId();
        path = image.getPath();
        name = image.getName();
    }
}
