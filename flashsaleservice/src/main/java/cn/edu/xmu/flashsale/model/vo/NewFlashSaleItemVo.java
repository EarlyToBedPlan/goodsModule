package cn.edu.xmu.flashsale.model.vo;

import cn.edu.xmu.ooad.model.VoObject;

/**
 * @author LJP_3424
 * @create 2020-12-04 7:34
 */
public class NewFlashSaleItemVo {

    private Long SkuId;

    private Long price;

    private Integer quantity;

    public Long getSkuId() {
        return SkuId;
    }

    public void setSkuId(Long skuId) {
        SkuId = skuId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
