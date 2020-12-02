package cn.edu.xmu.goods.model.vo;

import lombok.Data;

@Data
public class FloatPriceVo {
    private Long activityPrice;
    private String beginTime;
    private String endTime;
    private Integer quantity;
}
