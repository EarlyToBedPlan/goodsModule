package cn.edu.xmu.coupon.model.bo;

import cn.edu.xmu.coupon.model.po.CouponSpuPo;
import cn.edu.xmu.coupon.model.vo.CouponSpuSimpleRetVo;
import cn.edu.xmu.coupon.model.vo.CouponSpuVo;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/29 10:39
 */
@Data
public class CouponSpu implements VoObject {
    Long id;
    Long activityId;
    Long spuId;
    LocalDateTime gmtCreate;
    LocalDateTime gmtModified;
    String name;

    public CouponSpu(CouponSpuPo po) {
        this.id = po.getId();
        this.activityId = po.getActivityId();
        this.spuId = po.getSpuId();
    }

    public CouponSpu() {
    }

    ;

    @Override
    public Object createVo() {
        return new CouponSpuVo(this);
    }

    @Override
    public VoObject createSimpleVo() {
        return new CouponSpuSimpleRetVo(this);
    }

    public CouponSpuPo createPo() {
        CouponSpuPo po = new CouponSpuPo();
        po.setActivityId(this.activityId);
        po.setId(this.id);
        po.setSpuId(this.spuId);
        return po;
    }
}
