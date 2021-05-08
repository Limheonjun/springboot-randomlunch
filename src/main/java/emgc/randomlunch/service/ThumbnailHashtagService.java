package emgc.randomlunch.service;

import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.entity.ThumbnailHashtag;
import emgc.randomlunch.repository.ThumbnailHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThumbnailHashtagService {

    private final ThumbnailHashtagRepository repository;

    public List<ThumbnailHashtag> addThumbnailHashtagList(List<Thumbnail> thumbnailList, List<Hashtag> hashtagList){
        List<ThumbnailHashtag> thumbnailHashtagList = new ArrayList<>();
        for (Thumbnail thumbnail : thumbnailList) {
            for (Hashtag hashtag : hashtagList) {
                ThumbnailHashtag addedTumbnailHashtag = addThumnailHashtag(thumbnail, hashtag);
                thumbnailHashtagList.add(addedTumbnailHashtag);
            }
        }
        return thumbnailHashtagList;
    }

    // 썸네일 해시태그 저장
    public ThumbnailHashtag addThumnailHashtag(Thumbnail thumbnail, Hashtag hashtag) {
        ThumbnailHashtag thumbnailHashtag = ThumbnailHashtag.builder()
                                    .hashtag(hashtag)
                                    .thumbnail(thumbnail)
                                    .build();
        return repository.save(thumbnailHashtag);
    }
}
