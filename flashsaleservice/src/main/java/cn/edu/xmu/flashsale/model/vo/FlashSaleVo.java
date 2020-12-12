package cn.edu.xmu.flashsale.model.vo;

import cn.edu.xmu.ooad.model.VoObject;

import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-03 17:56
 */
public class FlashSaleVo implements VoObject {

    private Byte state;

    private Long id;

    private LocalDateTime flashDate;

//    private Long timeSegId;

    private LocalDateTime gmtCreated;

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

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}
