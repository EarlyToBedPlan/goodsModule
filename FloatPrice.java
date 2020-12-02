package cn.edu.xmu.goods.model.bo;

import cn.edu.xmu.goods.model.po.FloatPricePo;
import cn.edu.xmu.goods.model.vo.FloatPriceRetVo;
import cn.edu.xmu.goods.model.vo.FloatPriceVo;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;

import java.time.LocalDateTime;
/**
 * @Author：谢沛辰
 * @Date: 2020.12.01
 * @Description:价格浮动
 */
@Data
public class FloatPrice implements VoObject {
    private Long id;
    private Long goodsSkuId;
    private Long activityPrive;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;
    private Byte valid;
    private Integer quantity;
    private Long createdBy;
    private Long invaildBy;

    public FloatPrice(FloatPricePo floatPricePo){
        this.id=floatPricePo.getId();
        this.goodsSkuId=floatPricePo.getGoodsSkuId();
        this.activityPrive=floatPricePo.getActivityPrice();
        this.beginTime=floatPricePo.getBeginTime();
        this.endTime=floatPricePo.getEndTime();
        this.gmtCreated=floatPricePo.getGmtCreated();
        this.gmtModified=floatPricePo.getGmtModified();
        this.valid=floatPricePo.getValid();
        this.quantity=floatPricePo.getQuantity();
        this.createdBy=floatPricePo.getCreatedBy();
        this.invaildBy=floatPricePo.getInvalidBy();
    }

    public FloatPrice(FloatPriceVo floatPriceVo){
        this.beginTime=LocalDateTime.parse(floatPriceVo.getBeginTime());
        this.endTime=LocalDateTime.parse(floatPriceVo.getEndTime());
        this.quantity=floatPriceVo.getQuantity();
        this.activityPrive=floatPriceVo.getActivityPrice();
    }

    public FloatPricePo createPo(){
        FloatPricePo floatPricePo=new FloatPricePo();
        floatPricePo.setId(this.id);
        floatPricePo.setActivityPrice(this.activityPrive);
        floatPricePo.setBeginTime(this.beginTime);
        floatPricePo.setEndTime(this.endTime);
        floatPricePo.setCreatedBy(this.createdBy);
        floatPricePo.setGmtCreated(this.gmtCreated);
        floatPricePo.setGmtModified(this.gmtModified);
        floatPricePo.setGoodsSkuId(this.goodsSkuId);
        floatPricePo.setInvalidBy(this.invaildBy);
        floatPricePo.setQuantity(this.quantity);
        floatPricePo.setValid(this.valid);
        return floatPricePo;
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
