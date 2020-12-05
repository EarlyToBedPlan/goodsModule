package cn.edu.xmu.presale.model.vo;

import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.presale.model.bo.PreSale;
import cn.edu.xmu.presale.model.po.PreSalePo;

import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-02 0:49
 */
public class PreSaleVo {
    private Long id;

    private String name;

    private LocalDateTime beginTime;

    private LocalDateTime payTime;

    private LocalDateTime endTime;


    private Byte state;

    private Long shopId;

    private Long goodsSpuId;


    private Integer quantity;

    private Long advancePayPrice;

    private Long restPayPrice;


    private LocalDateTime gmtCreated;


    private LocalDateTime gmtModified;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getGoodsSpuId() {
        return goodsSpuId;
    }

    public void setGoodsSpuId(Long goodsSpuId) {
        this.goodsSpuId = goodsSpuId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getAdvancePayPrice() {
        return advancePayPrice;
    }

    public void setAdvancePayPrice(Long advancePayPrice) {
        this.advancePayPrice = advancePayPrice;
    }

    public Long getRestPayPrice() {
        return restPayPrice;
    }

    public void setRestPayPrice(Long restPayPrice) {
        this.restPayPrice = restPayPrice;
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

    public void setGmtModified(LocalDateTime getGmtModified) {
        this.gmtModified = gmtModified;
    }

    public PreSaleVo(PreSale preSale) {
        this.id = preSale.getId();
        this.name = preSale.getName();
        this.beginTime = preSale.getBeginTime();
        this.payTime = preSale.getPayTime();
        this.state = preSale.getState();
        this.endTime = preSale.getEndTime();
        this.quantity = preSale.getQuantity();
        this.advancePayPrice = preSale.getAdvancePayPrice();
        this.restPayPrice = preSale.getRestPayPrice();
        this.gmtCreated = preSale.getGmtCreated();
        this.gmtModified = preSale.getGmtModified();
    }

    public PreSalePo creatPo() {
        PreSalePo preSalePo = new PreSalePo();
        preSalePo.setId(this.id);
        preSalePo.setName(this.name);
        preSalePo.setBeginTime(this.beginTime);
        preSalePo.setPayTime(this.payTime);
        preSalePo.setState(this.state);
        preSalePo.setEndTime(this.endTime);
        preSalePo.setQuantity(this.quantity);
        preSalePo.setAdvancePayPrice(this.advancePayPrice);
        preSalePo.setRestPayPrice(this.restPayPrice);
        preSalePo.setGmtCreated(this.gmtCreated);
        preSalePo.setGmtModified(this.gmtModified);
        return preSalePo;
    }

}
