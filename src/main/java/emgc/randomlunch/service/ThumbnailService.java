package emgc.randomlunch.service;

import emgc.randomlunch.dto.FileInfoDto;
import emgc.randomlunch.dto.ThumbnailInfoDto;
import emgc.randomlunch.entity.File;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.repository.ThumbnailRepository;
import emgc.randomlunch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThumbnailService {

    private final ThumbnailRepository repository;
    private final FileUtil fileUtil;

    // 썸네일 목록 조회
    public List<ThumbnailInfoDto> getThumbnailList(int pageNumber) {
        List<ThumbnailInfoDto> thumbnailList = new ArrayList<>();
        Page<Thumbnail> findThumbnails = repository.findAll(PageRequest.of(pageNumber, 25));
        // TODO : thumbnail을 가져올때 hash를 1+n으로 가져오는 현상 eager처리로 변경 요망
        thumbnailList = findThumbnails.stream()
                        .map(thumbnail -> {
                            return ThumbnailInfoDto.builder()
                                    .id(thumbnail.getId())
                                    .restaurantId(thumbnail.getRestaurant().getId())
                                    .restaurantName(thumbnail.getRestaurant().getName())
                                    .file(new FileInfoDto(thumbnail.getFile()))
                                    .hashtags(thumbnail.getThumbnailHashtagList()
                                            .stream()
                                            .map(hashtag -> hashtag.getHashtag().getWord())
                                            .collect(Collectors.toList())
                                    )
                                    .build();
                        })
                        .collect(Collectors.toList());
        return thumbnailList;
    }

    //썸네일 추가
    public List<Thumbnail> addThumbnail(File file, Restaurant restaurant) throws IOException {
        List<Thumbnail> thumbnailList = new ArrayList<>();
        Thumbnail thumbnail = Thumbnail.builder()
                    .restaurant(restaurant)
                    .file(file)
                    .build();
        Thumbnail save = repository.save(thumbnail);
        thumbnailList.add(save);
        return thumbnailList;
    }

}
