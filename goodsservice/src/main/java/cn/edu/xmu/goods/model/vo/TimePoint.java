package cn.edu.xmu.goods.model.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TimePoint {
    LocalDateTime point;
    boolean isbegin;
    public TimePoint(LocalDateTime l,boolean i){
        this.point=l;
        this.isbegin=i;
    }
}
