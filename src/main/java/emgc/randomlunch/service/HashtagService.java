package emgc.randomlunch.service;

import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository repository;

    //해시태그 생성
    public List<Hashtag> makeHashtag(String hashtags[]) {
        return Arrays.stream(hashtags).map(Hashtag::new).collect(Collectors.toList());
    }

    // 해시태그 추가
    public List<Hashtag> addHashtag(String[] hashtags) {
        List<Hashtag> hashtagList = new ArrayList<>();
        for(String hashtag : hashtags) {
            Hashtag addHashtag = repository.findByWord(hashtag).orElseGet(() -> repository.save(new Hashtag(hashtag)));
            hashtagList.add(addHashtag);
        }
        return hashtagList;
    }

}
