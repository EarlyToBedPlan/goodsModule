package cn.edu.xmu.coupon.model.vo;

import cn.edu.xmu.coupon.model.bo.CouponSku;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/30 3:30
 */
@Data
public class CouponSkuSimpleRetVo implements VoObject {
    Long id;
    String name;

    public CouponSkuSimpleRetVo(CouponSku couponSku) {
        this.id = couponSku.getId();
        this.name = couponSku.getName();
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
