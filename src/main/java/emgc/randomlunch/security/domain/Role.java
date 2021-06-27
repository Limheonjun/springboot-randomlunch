package emgc.randomlunch.security.domain;

import emgc.randomlunch.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    private String roleName;
    private String roleDesc;

    @OneToMany(mappedBy = "role")
    @OrderBy("ordernum desc")
    private Set<RoleResources> roleResources = new LinkedHashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<UserRole> userRoles = new HashSet<>();
}
