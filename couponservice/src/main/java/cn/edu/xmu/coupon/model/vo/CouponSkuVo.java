package cn.edu.xmu.coupon.model.vo;

import cn.edu.xmu.coupon.model.bo.CouponSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/30 3:30
 */
@Data
public class CouponSkuVo {
    @ApiModelProperty(value = "商品id")
    Long id;
    String name;

    public CouponSkuVo(CouponSku couponSku) {
        this.id = couponSku.getId();
        this.name = couponSku.getName();
    }
}
