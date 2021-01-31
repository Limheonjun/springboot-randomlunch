package emgc.randomlunch.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Cafeteria {

    @Id
    @GeneratedValue
    @Column(name = "cafeteria_id")
    private Long id;

    @OneToMany(mappedBy = "cafeteria", cascade = CascadeType.ALL)
    private List<WeekMenu> weekMenuList = new ArrayList<>();

    private String name;
    private int price;
}
