package emgc.randomlunch.entity;

import emgc.randomlunch.dto.FileInfoDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private Long size;
    private int height;
    private int width;
    private String name;
    private String originalName;
    private String extension;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "file")
    private Thumbnail thumbnail;

    public File(FileInfoDto fileInfoDto){
        this.id = fileInfoDto.getId();
        this.size = fileInfoDto.getSize();
        this.height = fileInfoDto.getHeight();
        this.width = fileInfoDto.getWidth();
        this.name = fileInfoDto.getName();
        this.originalName = fileInfoDto.getOriginalName();
        this.extension = fileInfoDto.getName();
    }
}
