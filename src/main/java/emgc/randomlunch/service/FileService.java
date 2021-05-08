package emgc.randomlunch.service;

import emgc.randomlunch.dto.FileInfoDto;
import emgc.randomlunch.entity.File;
import emgc.randomlunch.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository repository;

    public List<File> addFileList(List<MultipartFile> files, String path) throws IOException {
        List<File> fileList = new ArrayList<>();
        for(MultipartFile file : files) {
            File saveFile = addFile(file, path);
            fileList.add(saveFile);
        }
        return fileList;
    }

    public List<File> addFileList(MultipartFile[] files, String path) throws IOException {
        return addFileList(Arrays.stream(files).collect(Collectors.toList()), path);
    }

    // 파일 엔티티 저장
    public File addFile(MultipartFile file, String path) throws IOException {
        FileInfoDto fileInfoDto = saveFile(file, path);
        return repository.save(new File(fileInfoDto));
    }

    // 실제 파일 저장
    public FileInfoDto saveFile(MultipartFile file, String path) throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        long size = file.getSize();
        int width = image.getWidth();
        int height = image.getHeight();
        String extension = getExtension(file);
        String originalName = getOriginalName(file);
        String name = saveFile(file, path, extension);

        return FileInfoDto.builder()
                .height(height)
                .width(width)
                .name(name)
                .originalName(originalName)
                .extension(extension)
                .build();
    }

    private String getExtension(MultipartFile file){
        String originalName = getOriginFullName(file);
        return originalName.substring(originalName.lastIndexOf(".") + 1);
    }

    private String getOriginalName(MultipartFile file){
        String originalName = getOriginFullName(file);
        return originalName.substring(0, originalName.lastIndexOf("."));
    }

    private String getOriginFullName(MultipartFile file){
        return file.getOriginalFilename();
    }

    private String saveFile(MultipartFile file, String path, String extension) throws IOException {
        UUID uuid = UUID.randomUUID();
        String saveName = uuid + "_" + LocalDate.now() + "." + extension;
        file.transferTo(new java.io.File(path, saveName));
        return saveName;
    }


}
