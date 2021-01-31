package emgc.randomlunch.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class WeekMenu {

    @Id
    @GeneratedValue
    @Column(name = "weekmenu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafeteria_id")
    private Cafeteria cafeteria;

    private String content;

    @Enumerated(EnumType.STRING)
    private WeekDay weekDay;
}

