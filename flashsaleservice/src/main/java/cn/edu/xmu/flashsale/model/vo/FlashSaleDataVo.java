package cn.edu.xmu.flashsale.model.vo;

import cn.edu.xmu.flashsale.model.po.FlashSaleItemPo;

import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-03 17:30
 */
public class FlashSaleDataVo {
    /**
     * {
     * "id": 0,
     * "goodsSku": {
     * "id": 0,
     * "name": "string",
     * "skuSn": "string",
     * "imageUrl": "string",
     * "inventory": 0,
     * "originalPrice": 0,
     * "price": 0,
     * "disable": false
     * },
     * "price": 0,
     * "quantity": 0,
     * "gmtCreate": "string",
     * "gmtModified": "string"
     * }
     */
    private Long id;

    private Long saleId;

    private Long goodsSkuId;

    private Long price;

    private Integer quantity;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
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

    // 此处缺少一个GoodsSKuVo
    public FlashSaleDataVo(FlashSaleItemPo flashSaleItemPo) {
        this.gmtCreated = flashSaleItemPo.getGmtCreated();
        this.gmtModified = flashSaleItemPo.getGmtModified();
        this.goodsSkuId = flashSaleItemPo.getGoodsSkuId();
        this.id = flashSaleItemPo.getId();
        this.price = flashSaleItemPo.getPrice();
        this.quantity = flashSaleItemPo.getQuantity();
        this.saleId = flashSaleItemPo.getSaleId();
    }
}
