package emgc.randomlunch.service;

import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.entity.ThumbnailHashtag;
import emgc.randomlunch.repository.ThumbnailHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThumbnailHashtagService {

    private final ThumbnailHashtagRepository repository;

    // 썸네일 해시태그 저장
    public ThumbnailHashtag addThumnailHashtag(Thumbnail thumbnail, Hashtag hashtag) {
        ThumbnailHashtag thumbnailHashtag = ThumbnailHashtag.builder()
                                    .hashtag(hashtag)
                                    .thumbnail(thumbnail)
                                    .build();
        ThumbnailHashtag save = repository.save(thumbnailHashtag);
        return save;
    }
}
