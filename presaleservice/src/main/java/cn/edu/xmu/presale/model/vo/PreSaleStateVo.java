package cn.edu.xmu.presale.model.vo;

import cn.edu.xmu.presale.model.bo.PreSale;
import lombok.Data;

/**
 * @author LJP_3424
 * @create 2020-12-01 0:49
 */
@Data
public class PreSaleStateVo {
    private Long Code;

    private String name;

    public PreSaleStateVo(PreSale.State state) {
        Code = Long.valueOf(state.getCode());
        name = state.getDescription();
    }
}

