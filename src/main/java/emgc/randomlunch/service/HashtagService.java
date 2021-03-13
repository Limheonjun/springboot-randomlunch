package emgc.randomlunch.service;

import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository repository;

    //해시태그 생성
    public List<Hashtag> makeHashtag(String hashtags[]) {
        List<Hashtag> hashtagList = new ArrayList<>();

        for (String hashtag : hashtags) {
            // TODO : DB에 해시태그가 존재하는지 확인 후 있는 경우엔 DB에서 꺼내오기
            Hashtag saveHashtag = Hashtag.builder().word(hashtag).build();
            hashtagList.add(saveHashtag);
        }
        return hashtagList;
    }

    // 해시태그 추가
    public List<Hashtag> addHashtag(String[] hashtags) {
        List<Hashtag> hashtagList = new ArrayList<>();
        for(String hashtag : hashtags) {
            Optional<Hashtag> findHashtag = repository.findByWord(hashtag);
            if(findHashtag.isEmpty()) {
                Hashtag saveHashtag = Hashtag.builder().word(hashtag).build();
                Hashtag save = repository.save(saveHashtag);
                hashtagList.add(save);
            }
            else hashtagList.add(findHashtag.get());
        }
        return hashtagList;
    }
}
