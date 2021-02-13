package emgc.randomlunch.service.business;

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

}
