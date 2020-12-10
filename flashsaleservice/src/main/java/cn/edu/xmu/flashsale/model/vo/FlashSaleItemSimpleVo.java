package cn.edu.xmu.flashsale.model.vo;

import cn.edu.xmu.flashsale.model.po.FlashSaleItemPo;

import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-04 10:49
 */
public class FlashSaleItemSimpleVo {
    private Long id;

    public FlashSaleItemSimpleVo(FlashSaleItemVo vo) {
        this.id = vo.getId();
        this.gmtCreated = vo.getGmtCreated();
        this.gmtModified = vo.getGmtModified();
        this.price = vo.getPrice();
        this.quantity = vo.getQuantity();
    }

    public FlashSaleItemSimpleVo(FlashSaleItemPo po) {
        this.id = po.getId();
        this.gmtCreated = po.getGmtCreate();
        this.gmtModified = po.getGmtModified();
        this.price = po.getPrice();
        this.quantity = po.getQuantity();
    }

    //缺少一个 goodsSKuVo
    //private GoodsSkuVo goodsSkuVo = new goodsSkuVo();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
