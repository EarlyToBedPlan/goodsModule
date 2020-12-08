package cn.edu.xmu.groupon.model.vo;

import cn.edu.xmu.groupon.model.po.GrouponPo;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-07 20:25
 */
public class NewGrouponVo {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    @NotBlank
    private String strategy;
    public GrouponPo createGrouponPo(){
        GrouponPo grouponPo = new GrouponPo();
        grouponPo.setStrategy(this.strategy);
        grouponPo.setBeginTime(this.beginTime);
        grouponPo.setEndTime(this.endTime);
        return grouponPo;
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

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
}
