package cn.edu.xmu.groupon.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-02 20:54
 */
public class GrouponVo {
    private Long id;

    private String name;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private Byte state;

    private Long shopId;

    private Long goodsSpuId;

    private String strategy;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

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

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
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

}
