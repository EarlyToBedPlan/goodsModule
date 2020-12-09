package cn.edu.xmu.goods.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 订单明细传值对象
 * @author jwy
 * @date Created in 2020/11/7 21:24
 **/
public class OrderItemVo {

    private Long skuId;

    private Integer quantity;

    private Long couponActId;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getCouponActId() {
        return couponActId;
    }

    public void setCouponActId(Long couponActId) {
        this.couponActId = couponActId;
    }
}
