package cn.edu.xmu.flashsale.model.vo;

import cn.edu.xmu.flashsale.model.bo.FlashSale;
import lombok.Data;

/**
 * @author LJP_3424
 * @create 2020-12-11 22:34
 */
public class FlashStateVo {
    private Long Code;

    private String name;

    public FlashStateVo(FlashSale.State state) {
        Code = Long.valueOf(state.getCode());
        name = state.getDescription();
    }
}

