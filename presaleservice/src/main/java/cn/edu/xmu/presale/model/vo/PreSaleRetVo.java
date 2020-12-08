package cn.edu.xmu.presale.model.vo;

import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.presale.model.po.PreSalePo;

import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-02 13:16
 */
public class PreSaleRetVo {

    private Long id;

    private String name;

    private LocalDateTime beginTime;

    private LocalDateTime payTime;

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

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    private LocalDateTime endTime;

    // private GoodsSpuVo goodsSpuVo;
    // private ShopVo shopVo;
    private Byte state;

    private Integer quantity;

    private Long advancePayPrice;

    private Long restPayPrice;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;


    public PreSaleRetVo(PreSalePo preSalePo) {

        // 缺少goodsSpuVo
        // 缺少shopSpuVo
        this.id = preSalePo.getId();
        this.name = preSalePo.getName();
        this.beginTime = preSalePo.getBeginTime();
        this.payTime = preSalePo.getPayTime();
        this.state = preSalePo.getState();
        this.endTime = preSalePo.getEndTime();
        this.quantity = preSalePo.getQuantity();
        this.advancePayPrice = preSalePo.getAdvancePayPrice();
        this.restPayPrice = preSalePo.getRestPayPrice();
        this.gmtCreated = preSalePo.getGmtCreated();
        this.gmtModified = preSalePo.getGmtModified();
    }
}
