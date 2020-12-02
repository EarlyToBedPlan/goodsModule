package cn.edu.xmu.coupon.model.vo;

import cn.edu.xmu.coupon.model.bo.CouponActivity;
import cn.edu.xmu.coupon.model.bo.CouponSpu;
import lombok.Data;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/30 3:30
 */
@Data
public class CouponSpuSimpleRetVo {
    Long id;
    String name;


    public CouponSpuSimpleRetVo(CouponSpu couponSpu) {
        this.id = couponSpu.getId();
        this.name = couponSpu.getName();
    }
}
