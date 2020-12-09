package cn.edu.xmu.coupon.model.vo;

import cn.edu.xmu.coupon.model.bo.Coupon;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Feiyan Liu
 * @date Created at 2020/12/9 12:51
 */

@Data
public class CouponStateVo {
    @ApiModelProperty(value = "商品id")
    Integer code;
    String name;

    public CouponStateVo(Coupon.State state) {
        this.code = state.getCode();
        this.name = state.getDescription();
    }
}

