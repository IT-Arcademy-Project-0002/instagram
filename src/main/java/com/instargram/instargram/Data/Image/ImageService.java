package com.instargram.instargram.Data.Image;

import com.instargram.instargram.Config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    public Image create(String name, String path) {
        Image image = new Image();
        image.setName(name);
        image.setPath(path);
        return this.imageRepository.save(image);
    }

    public Image saveImage(MultipartFile multipartFile, String nameWithoutExtension, String fileExtension) throws IOException, IOException {
        UUID uuid = UUID.randomUUID();
        String name = uuid + "_" + nameWithoutExtension + "." + fileExtension;

        String savePath = AppConfig.getImageFileDirPath();

        if (!new File(savePath).exists()) {
            try {
                new File(savePath).mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String filePath = savePath + "\\" + name;
        File origFile = new File(filePath);
        multipartFile.transferTo(origFile);

        return create(name, filePath);  // 이미지 생성에 대한 로직을 호출합니다.
    }

    public void deleteImageFile(String imagePath) {
        File file = new File(imagePath);

        if (file.exists()) {
            file.delete();
        }
    }

    public void deleteImage(Image image) {
        if(image != null)
        {
            this.imageRepository.delete(image);
        }
    }

    public void deleteImage(Long id) {

        this.imageRepository.deleteById(id);
    }

    public Image memberImageChange(Image image, MultipartFile multipartFile,
                                   String nameWithoutExtension, String fileExtension) throws IOException {
        if(image != null)
        {
            deleteImageFile(AppConfig.getImageFileDirPath()+"\\"+image.getName());
        }

        return saveImage(multipartFile, nameWithoutExtension, fileExtension);
    }

    public Image getImageByID(Long id) {
        return this.imageRepository.findById(id).orElse(null);
    }
}
