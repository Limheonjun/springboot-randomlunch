package emgc.randomlunch.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    private String email;
    private String content;
}
