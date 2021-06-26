package emgc.randomlunch.security.service;

import emgc.randomlunch.security.domain.Resources;
import emgc.randomlunch.security.repository.ResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SecurityResourceService {

    @Autowired
    private ResourcesRepository resourcesRepository;

    @Transactional
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList(){
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourcesRepository.findAll();
        resourcesList.forEach(resource -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            resource.getRoleResources().stream().forEach(roleResource -> {
                configAttributeList.add(new SecurityConfig(roleResource.getRole().getRoleName()));
                result.put(new AntPathRequestMatcher(resource.getResourceName()), configAttributeList);
            });

        });

        return result;
    }

}
