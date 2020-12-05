package cn.edu.xmu.flashsale.model.bo;

import cn.edu.xmu.flashsale.model.po.FlashSalePo;
import cn.edu.xmu.flashsale.model.vo.FlashSaleItemSimpleVo;
import cn.edu.xmu.ooad.model.VoObject;

import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-03 21:27
 */
public class FlashSale implements VoObject {

    private Long id;

    private LocalDateTime flashDate;

    private Long timeSegId;

    private LocalDateTime gmtCreated;

    public FlashSale(FlashSalePo po) {
        id = po.getId();
        flashDate = po.getFlashDate();
        timeSegId = po.getTimeSegId();
        gmtCreated = po.getGmtCreated();
        gmtModified = po.getGmtModified();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFlashDate() {
        return flashDate;
    }

    public void setFlashDate(LocalDateTime flashDate) {
        this.flashDate = flashDate;
    }

    public Long getTimeSegId() {
        return timeSegId;
    }

    public void setTimeSegId(Long timeSegId) {
        this.timeSegId = timeSegId;
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


    @Override
    public Object createVo() {
        return null;
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }
}
