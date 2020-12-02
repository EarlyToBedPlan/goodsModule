package cn.edu.xmu.coupon.model.vo;

import cn.edu.xmu.coupon.model.bo.CouponActivity;
import cn.edu.xmu.ooad.model.VoObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/30 3:30
 */
@Data
public class CouponActivityRetVo implements VoObject {
    //还有商店 创建者 修改者 创建时间 修改时间没有写好
    Long id;
    String name;
    byte state;
    ShopRetVo shop;
    int quantity;
    byte quantityType;
    byte validTerm;
    String imageUrl;
    String beginTime;
    String endTime;
    String couponTime;
    String strategy;
    UserRetVo creator;
    UserRetVo modifiedBy;
    LocalDateTime gmtCreate;
    LocalDateTime gmtModified;

    public CouponActivityRetVo(CouponActivity couponActivity) {
        this.id = couponActivity.getId();
        this.name = couponActivity.getName();
        this.quantity = couponActivity.getQuantity();
        this.quantityType = couponActivity.getQuantityType();
        this.validTerm = couponActivity.getValidTerm();
        this.imageUrl = couponActivity.getImg();
        this.beginTime = couponActivity.getBeginTime().toString();
        this.endTime = couponActivity.getEndTime().toString();
        this.couponTime = couponActivity.getCouponTime().toString();
        this.strategy = couponActivity.getStrategy();
        this.creator=couponActivity.getCreator();
        this.modifiedBy=couponActivity.getModifiedBy();
        this.gmtCreate=couponActivity.getGmtCreate();
        this.gmtModified=couponActivity.getGmtModified();
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
