package emgc.randomlunch.service.business;

import emgc.randomlunch.dto.ThumbnailListDto;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.repository.ThumbnailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThumbnailService {

    private final ThumbnailRepository repository;

    // 모든 썸네일
    public List<ThumbnailListDto> getAllThumbnails() {
        List<Thumbnail> thumbnailList = repository.findAll();
        List<ThumbnailListDto> thumbnailListDtoList = thumbnailList
                                                        .stream()
                                                        .map(thumbnail -> new ThumbnailListDto(thumbnail))
                                                        .collect(Collectors.toList());
        return thumbnailListDtoList;
    }

}
