package emgc.randomlunch.dto;

import emgc.randomlunch.entity.WeekDay;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CafeteriaInfoDto {

    private Long cafeteriaId;
    private List<Long> weekMenuList = new ArrayList<>();
    private List<String> contentList = new ArrayList<>();
    private List<WeekDay> weekDayList = new ArrayList<>();

}
