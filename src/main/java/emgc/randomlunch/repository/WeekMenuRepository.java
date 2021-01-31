package emgc.randomlunch.repository;

import emgc.randomlunch.entity.WeekMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekMenuRepository extends JpaRepository<WeekMenu, Long> {
}
