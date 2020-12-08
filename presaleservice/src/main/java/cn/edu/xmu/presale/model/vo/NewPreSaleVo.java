package cn.edu.xmu.presale.model.vo;

import cn.edu.xmu.presale.model.po.PreSalePo;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-02 15:44
 */
public class NewPreSaleVo {
    @NotNull
    private String name;
    @Future(message = "时间段已过")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    @Future(message = "时间段已过")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;
    @Future(message = "时间段已过")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    @NotNull
    @Min(0)
    private Integer quantity;
    @NotNull
    @Min(1)
    private Long advancePayPrice;
    @NotNull
    @Min(1)
    private Long restPayPrice;

    public PreSalePo createPreSalePo(){
        PreSalePo preSalePo = new PreSalePo();
        preSalePo.setName(this.name);
        preSalePo.setAdvancePayPrice(this.advancePayPrice);
        preSalePo.setRestPayPrice(this.restPayPrice);
        preSalePo.setQuantity(this.quantity);
        preSalePo.setBeginTime(this.beginTime);
        preSalePo.setPayTime(this.payTime);
        preSalePo.setEndTime(this.endTime);
        return preSalePo;
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
}
