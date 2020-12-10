package cn.edu.xmu.flashsale.model.bo;

import cn.edu.xmu.flashsale.model.po.FlashSalePo;
import cn.edu.xmu.flashsale.model.vo.FlashSaleItemSimpleVo;
import cn.edu.xmu.flashsale.model.vo.FlashSaleVo;
import cn.edu.xmu.ooad.model.VoObject;

import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-03 21:27
 */
public class FlashSale implements VoObject {

    private Long id;

    private LocalDateTime flashDate;

    //private Long timeSegId;
    //private TimeSegmentVo timeSegmentVo;

    private String imageUrl;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public FlashSale(FlashSalePo po) {
        id = po.getId();
        flashDate = po.getFlashDate();
        gmtCreated = po.getGmtCreate();
        gmtModified = po.getGmtModified();
    }

    @Override
    public VoObject createVo() {
        FlashSaleVo flashSaleVo = new FlashSaleVo();
        flashSaleVo.setFlashDate(this.flashDate);
        flashSaleVo.setGmtCreated(this.gmtCreated);
        flashSaleVo.setGmtModified(this.gmtModified);
        flashSaleVo.setId(this.id);
        return flashSaleVo;
    }

    @Override
    public VoObject createSimpleVo() {
        return null;
    }
}
