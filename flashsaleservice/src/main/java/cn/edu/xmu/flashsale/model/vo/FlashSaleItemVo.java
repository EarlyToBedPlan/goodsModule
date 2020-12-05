package cn.edu.xmu.flashsale.model.vo;

import cn.edu.xmu.flashsale.model.po.FlashSaleItemPo;

import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-04 12:10
 */
public class FlashSaleItemVo {

    public FlashSaleItemVo(FlashSaleItemPo po) {
        id = po.getId();
        goodsSkuId = po.getGoodsSkuId();
        price = po.getPrice();
        quantity = po.getQuantity();
        gmtCreated = po.getGmtCreated();
        gmtModified = po.getGmtModified();
    }

    private Long id;

    private Long saleId;

    private Long goodsSkuId;

    private Long price;

    private Integer quantity;

    private LocalDateTime gmtCreated;

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

    private LocalDateTime gmtModified;
}
