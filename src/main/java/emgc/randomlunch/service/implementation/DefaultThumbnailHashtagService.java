package emgc.randomlunch.service.implementation;

import emgc.randomlunch.entity.*;
import emgc.randomlunch.repository.ThumbnailHashtagRepository;
import emgc.randomlunch.service.function.ThumbnailHashtagService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultThumbnailHashtagService implements ThumbnailHashtagService {

    private final ThumbnailHashtagRepository thumbnailHashtagRepository;

    @Override
    public List<ThumbnailHashtag> getThumbnailHashtagListByHashtagList(List<Hashtag> hashtagList) {
        return thumbnailHashtagRepository.findAllByHashtagIn(hashtagList);
    }
}
