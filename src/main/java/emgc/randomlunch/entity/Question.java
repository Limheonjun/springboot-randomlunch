package emgc.randomlunch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;
    private String email;
    private String content;
}
