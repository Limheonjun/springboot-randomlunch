package emgc.randomlunch.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private Long size;
    private int height;
    private int width;
    private String name;
    private String extension;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "file")
    private Thumbnail thumbnail;
}
