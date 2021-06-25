package emgc.randomlunch.security.domain;

import emgc.randomlunch.entity.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class UserRole extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "userrole_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

}
