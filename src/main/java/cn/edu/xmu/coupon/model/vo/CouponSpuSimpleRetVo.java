package cn.edu.xmu.coupon.model.vo;

import cn.edu.xmu.coupon.model.bo.CouponSpu;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/30 3:30
 */
@Data
public class CouponSpuSimpleRetVo implements VoObject {
    Long id;
    String name;

    public CouponSpuSimpleRetVo(CouponSpu couponSpu) {
        this.id = couponSpu.getId();
        this.name = couponSpu.getName();
    }

    @Override
    public Object createVo() {
        return null;
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }
}
