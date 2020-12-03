package cn.edu.xmu.flashsale.model.vo;

import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-03 17:56
 */
public class FlashSaleVo {

    private Long id;

    private LocalDateTime flashDate;

    private Long timeSegId;

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

}
