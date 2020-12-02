package cn.edu.xmu.goods.model.vo;

import cn.edu.xmu.goods.model.bo.FloatPrice;
import lombok.Data;

import java.time.LocalDateTime;
@Data
class createdBy{
    private Long id;
    private String userName;
    //用户名暂时不添加
    public void setId(Long id){
        this.id=id;
    }
}

@Data
class modifiedBy{
    private Long id;
    private String userName;
    //用户名暂时不添加
    public void setId(Long id){
        this.id=id;
    }
}

@Data
public class FloatPriceRetVo {
    private Long id;
    private Long activityPrive;
    private String beginTime;
    private String endTime;
    private String gmtCreated;
    private String gmtModified;
    private Byte valid;
    public createdBy created=new createdBy();
    public modifiedBy modified=new modifiedBy();
    private Integer quantity;

    public FloatPriceRetVo(FloatPrice floatPrice,Long createdId,Long modifiedId){
        this.id=floatPrice.getId();
        this.activityPrive=floatPrice.getActivityPrive();
        this.beginTime=floatPrice.getBeginTime().toString();
        this.endTime=floatPrice.getEndTime().toString();
        this.gmtCreated=floatPrice.getGmtCreated().toString();
        this.gmtModified=floatPrice.getGmtModified().toString();
        this.quantity=floatPrice.getQuantity();
        this.valid=floatPrice.getValid();
        created.setId(createdId);
        modified.setId(modifiedId);
    }
}




