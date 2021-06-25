package emgc.randomlunch.security.domain;

import emgc.randomlunch.entity.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class Resources extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "resource_id")
    private Long id;

    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;

    @OneToMany(mappedBy = "resources")
    private Set<Role> roles = new HashSet<>();
}
