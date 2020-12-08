package cn.edu.xmu.coupon.model.bo;

import cn.edu.xmu.coupon.model.po.CouponSkuPo;
import cn.edu.xmu.coupon.model.vo.CouponSkuSimpleRetVo;
import cn.edu.xmu.coupon.model.vo.CouponSkuSimpleRetVo;
import cn.edu.xmu.coupon.model.vo.CouponSkuVo;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/29 10:39
 */
@Data
public class CouponSku implements VoObject {
    Long id;
    Long activityId;
    Long SkuId;
    LocalDateTime gmtCreate;
    LocalDateTime gmtModified;
    String name;

    public CouponSku(CouponSkuPo po) {
        this.id = po.getId();
        this.activityId = po.getActivityId();
        this.SkuId = po.getSkuId();
    }

    public CouponSku() {
    }

    ;

    @Override
    public Object createVo() {
        return new CouponSkuVo(this);
    }

    @Override
    public VoObject createSimpleVo() {
        return new CouponSkuSimpleRetVo(this);
    }

    public CouponSkuPo createPo() {
        CouponSkuPo po = new CouponSkuPo();
        po.setActivityId(this.activityId);
        po.setId(this.id);
        po.setSkuId(this.SkuId);
        return po;
    }
}
