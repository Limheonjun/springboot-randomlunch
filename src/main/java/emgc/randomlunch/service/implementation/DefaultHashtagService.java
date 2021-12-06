package emgc.randomlunch.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.repository.HashtagRepository;
import emgc.randomlunch.service.function.HashtagService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultHashtagService implements HashtagService {

    private final HashtagRepository hashtagRepository;

    @Override
    public List<Hashtag> getHashtagListByWord(String word) {
        return hashtagRepository.findAllByWordLike(word);
    }


}
