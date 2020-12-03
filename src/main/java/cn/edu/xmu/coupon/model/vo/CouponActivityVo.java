package cn.edu.xmu.coupon.model.vo;

import cn.edu.xmu.coupon.model.bo.CouponActivity;
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
@ApiModel("优惠活动传值对象")
public class CouponActivityVo {
    @ApiModelProperty(value = "活动姓名")
    String name;
    @ApiModelProperty(value = "活动id")
    Long id;
    @ApiModelProperty(value = "优惠券数量，0-不用优惠券")
    int quantity;
    @ApiModelProperty(value = "优惠券数量类型")
    Byte quantityType;
    @ApiModelProperty(value = "优惠券从领取那天起的有效时间，0-与活动同，其他：从领券起的有效天数")
    Byte validTerm;
    @ApiModelProperty(value = "开始时间")
    LocalDateTime beginTime;
    @ApiModelProperty(value = "开始领优惠券时间")
    LocalDateTime couponTime;
    @ApiModelProperty(value = "结束时间")
    LocalDateTime endTime;
    @ApiModelProperty(value = "优惠规则JSON")
    String strategy;

    public CouponActivity createCouponActivity() {
        CouponActivity couponActivity = new CouponActivity();
        couponActivity.setId(this.id);
        couponActivity.setName(this.name);
        couponActivity.setBeginTime(this.beginTime);
        couponActivity.setCouponTime(this.couponTime);
        couponActivity.setEndTime(this.endTime);
        couponActivity.setStrategy(this.strategy);
        couponActivity.setQuantity(this.quantity);
        couponActivity.setQuantityType(this.quantityType);
        couponActivity.setValidTerm(this.validTerm);
        return couponActivity;
    }
}
