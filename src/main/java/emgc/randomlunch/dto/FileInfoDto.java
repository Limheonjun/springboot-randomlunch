package emgc.randomlunch.dto;

import emgc.randomlunch.entity.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FileInfoDto {

    private Long id;
    private Long size;
    private int height;
    private int width;
    private String name;
    private String originalName;
    private String extension;

    public FileInfoDto(File file) {
        this.id = file.getId();
        this.size = file.getSize();
        this.height = file.getHeight();
        this.width = file.getWidth();
        this.name = file.getName();
        this.originalName = file.getOriginalName();
        this.extension = file.getExtension();
    }

}
