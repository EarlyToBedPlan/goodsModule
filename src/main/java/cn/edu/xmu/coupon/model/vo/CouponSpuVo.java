package cn.edu.xmu.coupon.model.vo;

import cn.edu.xmu.coupon.model.bo.CouponSpu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/30 3:30
 */
@Data
public class CouponSpuVo {
    @ApiModelProperty(value = "商品id")
    Long id;
    String name;

    public CouponSpuVo(CouponSpu couponSpu) {
        this.id = couponSpu.getId();
        this.name = couponSpu.getName();
    }
}
