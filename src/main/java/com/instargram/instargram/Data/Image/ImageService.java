package com.instargram.instargram.Data.Image;

import com.instargram.instargram.Config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    public Image create(String name, MultipartFile multipartFile) {
        Image image = new Image();
        image.setName(name);
        image.setPath(String.valueOf(multipartFile));
        return this.imageRepository.save(image);
    }
}
