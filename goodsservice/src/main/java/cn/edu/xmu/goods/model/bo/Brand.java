package cn.edu.xmu.goods.model.bo;

import cn.edu.xmu.goods.model.po.BrandPo;
import cn.edu.xmu.goods.model.vo.BrandRetVo;
import cn.edu.xmu.goods.model.vo.BrandSimpleRetVo;
import cn.edu.xmu.goods.model.vo.UpdateBrandVoBody;
import cn.edu.xmu.ooad.model.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 品牌
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 16:56
 * modifiedBy Yancheng Lai 16:56
 **/
@Data
public class Brand implements VoObject{

//    private Long id;
//
//    private String name;
//
//    private String imageUrl;
//
//    private String detail;
//
//    private LocalDateTime gmtCreate;
//
//    private LocalDateTime gmtModified;

    private BrandPo brandPo;

    public Brand(BrandPo brandPo){
        this.brandPo = brandPo;
    }

    public Brand(UpdateBrandVoBody vo) {
        this.brandPo = new BrandPo();
        this.brandPo.setDetail(vo.getDetail());
        this.brandPo.setName(vo.getName());
    }

    public BrandPo getPo(){
        return this.brandPo;
    }

    public Brand(BrandRetVo brandRetVo){
        this.brandPo = new BrandPo();
        brandPo.setDetail(brandRetVo.getDetail());
        brandPo.setId(brandRetVo.getId());
        brandPo.setImageUrl(brandRetVo.getImageUrl());
        brandPo.setName(brandRetVo.getName());
        brandPo.setGmtCreate(brandRetVo.getGmtCreate());
        brandPo.setGmtModified(brandRetVo.getGmtModified());
    }

    @Override
    public BrandRetVo createVo() {
        return new BrandRetVo(this);
    }

    @Override
    public BrandSimpleRetVo createSimpleVo() {
        return new BrandSimpleRetVo(this);
    }

    public Long getId() {
        return brandPo.getId();
    }

    public void setId(Long id) {
        brandPo.setId(id);
    }

    public String getName() {
        return brandPo.getName();
    }

    public void setName(String name) {
        brandPo.setName(name);
    }

    public String getDetail() {
        return brandPo.getDetail();
    }

    public void setDetail(String detail) {
        brandPo.setDetail(detail);
    }

    public LocalDateTime getGmtCreate() {
        return brandPo.getGmtCreate();
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        brandPo.setGmtCreate(gmtCreate);
    }

    public LocalDateTime getGmtModified() {
        return brandPo.getGmtModified();
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        brandPo.setGmtModified(gmtModified);
    }

    public String getImageUrl() {
        return brandPo.getImageUrl();
    }

    public void setImageUrl(String imageUrl) {
        brandPo.setImageUrl(imageUrl);
    }
}
