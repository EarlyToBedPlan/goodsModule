package cn.edu.xmu.groupon.model.vo;

import cn.edu.xmu.ooad.model.VoObject;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-03 14:43
 */
public class GrouponSimpleVo implements VoObject {
    private Long id;
    private String name;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;

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

    @Override
    public VoObject createVo() {
        return this;
    }

    @Override
    public VoObject createSimpleVo() {
        return this;
    }
}

