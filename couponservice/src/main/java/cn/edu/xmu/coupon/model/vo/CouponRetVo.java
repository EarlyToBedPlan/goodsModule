package cn.edu.xmu.coupon.model.vo;

import cn.edu.xmu.coupon.model.bo.Coupon;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;

/**
 * @author Feiyan Liu
 * @date Created at 2020/12/3 16:43
 */
@Data
public class CouponRetVo implements VoObject {
    Long id;
    VoObject couponActivityRetSimpleVo;
    Long customerId;
    String name;
    String  couponSn;


    public CouponRetVo(Coupon coupon)
    {
        this.id=coupon.getId();
        this.name=coupon.getName();
        this.couponSn=coupon.getCouponSn();
        this.customerId=coupon.getCustomerId();
        this.couponActivityRetSimpleVo=coupon.getCouponActivityRetSimpleVo();
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
