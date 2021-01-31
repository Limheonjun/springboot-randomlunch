package emgc.randomlunch.repository;

import emgc.randomlunch.entity.Thumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {
}
