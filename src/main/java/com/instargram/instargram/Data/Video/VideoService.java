package com.instargram.instargram.Data.Video;

import com.instargram.instargram.Config.AppConfig;
import com.instargram.instargram.Data.Image.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    public Video create(String name, String path) {
        Video video = new Video();
        video.setName(name);
        video.setPath(path);
        return this.videoRepository.save(video);
    }

    public Video saveVideo(MultipartFile multipartFile, String nameWithoutExtension, String fileExtension)throws IOException, IOException {
        UUID uuid = UUID.randomUUID();
        String name = uuid + "_" + nameWithoutExtension + "." + fileExtension;

        String savePath = AppConfig.getVideoFileDirPath();

        if (!new File(savePath).exists()) {
            try {
                new File(savePath).mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String filePath = savePath + name;
        File origFile = new File(filePath);
        multipartFile.transferTo(origFile);

        return create(name, filePath);  // 이미지 생성에 대한 로직을 호출합니다.
    }

    public Video getVideoByID(Long id) {
        return this.videoRepository.findById(id).orElse(null);
    }

    public void delete(Long id)
    {
        videoRepository.deleteById(id);
    }
}
