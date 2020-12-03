package cn.edu.xmu.coupon.model.vo;

import cn.edu.xmu.coupon.model.bo.CouponActivity;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/30 3:30
 */
@Data
public class CouponActivityRetSimpleVo implements VoObject {
    Long id;
    String name;
    String imageUrl;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    int quantity;
    LocalDateTime couponTime;


    public CouponActivityRetSimpleVo(CouponActivity couponActivity) {
        this.id = couponActivity.getId();
        this.name = couponActivity.getName();
//        this.quantity = couponActivity.getQuantity();
//        this.imageUrl = couponActivity.getImg();
//        this.beginTime = couponActivity.getBeginTime().toString();
//        this.endTime = couponActivity.getEndTime().toString();
//        this.couponTime = couponActivity.getCouponTime().toString();
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
