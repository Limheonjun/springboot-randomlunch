package emgc.randomlunch.security.repository;

import emgc.randomlunch.security.domain.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResourcesRepository extends JpaRepository<Resources, Long> {

    Resources findByResourceNameAndHttpMethod(String resourceName, String httpMethod);

    @Query("select r from Resources r join fetch r.roleResources where r.resourceType = 'url' order by r.orderNum desc")
    List<Resources> findAll();
}
