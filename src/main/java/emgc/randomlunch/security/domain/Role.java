package emgc.randomlunch.security.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    private String roleName;
    private String roleDesc;

    @OneToMany(mappedBy = "role")
    @OrderBy("ordernum desc")
    private Set<Resources> resourcesSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<User> users = new HashSet<>();
}
