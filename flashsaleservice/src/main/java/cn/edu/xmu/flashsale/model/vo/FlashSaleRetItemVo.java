package cn.edu.xmu.flashsale.model.vo;

import cn.edu.xmu.flashsale.model.bo.FlashSaleItem;
import cn.edu.xmu.flashsale.model.bo.FlashSaleRetItem;
import cn.edu.xmu.flashsale.model.po.FlashSaleItemPo;

import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-04 21:02
 */
public class FlashSaleRetItemVo {
    private Long id;

    private Long goodsSkuId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Long goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
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

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    private Long price;

    private Integer quantity;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public FlashSaleRetItemVo(FlashSaleRetItem flashSaleRetItem) {
        id = flashSaleRetItem.getId();
        goodsSkuId = flashSaleRetItem.getGoodsSkuId();
        price = flashSaleRetItem.getPrice();
        quantity = flashSaleRetItem.getQuantity();
        gmtCreated = flashSaleRetItem.getGmtCreated();
        gmtModified = flashSaleRetItem.getGmtModified();
    }

}
