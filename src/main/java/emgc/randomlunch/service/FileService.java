package emgc.randomlunch.service;

import emgc.randomlunch.entity.File;
import emgc.randomlunch.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository repository;

    // 파일 엔티티 저장
    public File addFile(File file) {
        File saveFile = repository.save(file);
        return saveFile;
    }
}
