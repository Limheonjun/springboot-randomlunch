package emgc.randomlunch.dto;

import emgc.randomlunch.entity.WeekDay;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CafeteriaListDto {

    private Long cafeteriaId;
    private String name;
    private int price;
    private Long weekMenuId;
    private WeekDay weekDay;
    private String content;

}
