package emgc.randomlunch.util;

import emgc.randomlunch.dto.FileInfoDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class FileUtil {

    // 실제 파일 저장
    public FileInfoDto saveFile(MultipartFile file, String path) throws IOException {
        FileInfoDto fileInfo = null;

        BufferedImage image = ImageIO.read(file.getInputStream());
        int width = image.getWidth();
        int height = image.getHeight();
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);


        // 파일 이름 변경
        UUID uuid = UUID.randomUUID();
        String saveName = uuid + "_" + LocalDate.now() + "." + extension;
        // 저장할 File 객체를 생성(껍데기 파일)
        File saveFile = new File(path, saveName); // 저장할 폴더 이름, 저장할 파일 이름
        file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘

        fileInfo = FileInfoDto.builder()
                            .height(height)
                            .width(width)
                            .name(saveName)
                            .extension(extension)
                            .build();


        return fileInfo;
    }
}
