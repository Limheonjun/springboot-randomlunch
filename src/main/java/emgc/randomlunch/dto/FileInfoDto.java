package emgc.randomlunch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class FileInfoDto {

    private Long id;
    private Long size;
    private int height;
    private int width;
    private String name;
    private String extension;

}
