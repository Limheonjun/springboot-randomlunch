package emgc.randomlunch.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public List<Hashtag> createHashtagList(List<String> wordList) {
        //해시태그는 최대 2개, 찾을 수 있는 것도 최대 2개 => 아무리해도 4번으로 시간복잡도 O(1)
        List<Hashtag> hashtagList = hashtagRepository.findAllByWordIn(wordList);
        Set<String> set = hashtagList.stream().map(Hashtag::getWord).collect(Collectors.toSet());

        List<Hashtag> newHashtagList = new ArrayList<>();
        for(String word : wordList) {
            if(!set.contains(word)) {
                newHashtagList.add(Hashtag.builder().word(word).build());
            }
        }

        List<Hashtag> savedHashtagList = hashtagRepository.saveAll(newHashtagList);
        hashtagList.addAll(savedHashtagList);
        return hashtagList;
    }
}
