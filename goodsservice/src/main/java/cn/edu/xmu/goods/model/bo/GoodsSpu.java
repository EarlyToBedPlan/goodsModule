package cn.edu.xmu.goods.model.bo;

import cn.edu.xmu.goods.model.po.GoodsSpuPo;
import cn.edu.xmu.goods.model.vo.*;
import cn.edu.xmu.goods.service.BrandService;
import cn.edu.xmu.goods.service.GoodsCategoryService;
import cn.edu.xmu.goods.service.GoodsSkuService;
import cn.edu.xmu.goods.service.GoodsSpuService;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @Description: 商品SKU Bo
* @Author: Yancheng Lai
* @Date: 2020/12/1 20:08
*/



@Data
public class GoodsSpu implements VoObject, Serializable {



    private GoodsSpuPo goodsSpuPo;

    /**
    * @Description:  构造函数byPo
    * @Param: [goodsSpuPo]
    * @return:  GoodsSpu
    * @Author: Yancheng Lai
    * @Date: 2020/12/1 19:51
    */
    public GoodsSpu(GoodsSpuPo goodsSpuPo) {
        this.goodsSpuPo = goodsSpuPo;
    }

    //注意 注意 由于集成的历史原因 这里的FreightModel还没有初始化
    @Override
    public GoodsSpuRetVo createVo() {
           return new GoodsSpuRetVo(this);
    }




    @Override
    public GoodsSpuSimpleRetVo createSimpleVo() {
        return new GoodsSpuSimpleRetVo(this);
    }


    //Spec转换还要做
    public GoodsSpu(GoodsSpuRetVo goodsSpuRetVo){
        this.setBrandId(goodsSpuRetVo.getBrand().getId());
        this.setCategoryId(goodsSpuRetVo.getCategory().getId());
        this.setDetail(goodsSpuRetVo.getDetail());
        this.setDisabled(goodsSpuRetVo.getDisabled());
        this.setFreightId(goodsSpuRetVo.getFreightId());
        this.setId(goodsSpuRetVo.getId());
        this.setImageUrl(goodsSpuRetVo.getImageUrl());
        this.setName(goodsSpuRetVo.getName());
        this.setShopId(goodsSpuRetVo.getShop().getId());
        this.setSpec(JacksonUtil.toJson(goodsSpuRetVo.getSpec()));
        this.setGoodsSn(goodsSpuRetVo.getGoodsSn());
        this.setGmtCreate(goodsSpuRetVo.getGmtCreate());
        this.setGmtModified(goodsSpuRetVo.getGmtModified());
    }


    public Long getId() {
        return goodsSpuPo.getId();
    }

    public void setId(Long id) {
        goodsSpuPo.setId(id);
    }

    public String getName() {
        return goodsSpuPo.getName();
    }

    public void setName(String name) {
        goodsSpuPo.setName(name);
    }

    public Long getBrandId() {
        return goodsSpuPo.getBrandId();
    }

    public void setBrandId(Long brandId) {
        goodsSpuPo.setBrandId(brandId);
    }

    public Long getCategoryId() {
        return goodsSpuPo.getCategoryId();
    }

    public void setCategoryId(Long categoryId) {
        goodsSpuPo.setCategoryId(categoryId);
    }

    public Long getFreightId() {
        return goodsSpuPo.getFreightId();
    }

    public void setFreightId(Long freightId) {
        goodsSpuPo.setFreightId(freightId);
    }

    public Long getShopId() {
        return goodsSpuPo.getShopId();
    }

    public void setShopId(Long shopId) {
        goodsSpuPo.setShopId(shopId);
    }

    public String getGoodsSn() {
        return goodsSpuPo.getGoodsSn();
    }

    public void setGoodsSn(String goodsSn) {
        goodsSpuPo.setGoodsSn(goodsSn);
    }

    public String getDetail() {
        return goodsSpuPo.getDetail();
    }

    public void setDetail(String detail) {
        goodsSpuPo.setDetail(detail);
    }

    public String getImageUrl() {
        return goodsSpuPo.getImageUrl();
    }

    public void setImageUrl(String imageUrl) {
        goodsSpuPo.setImageUrl(imageUrl);
    }


    public String getSpec() {
        return goodsSpuPo.getSpec();
    }

    public void setSpec(String spec) {
        goodsSpuPo.setSpec(spec);
    }

    public Byte getDisabled() {
        return goodsSpuPo.getDisabled();
    }

    public void setDisabled(Byte disabled) {
        goodsSpuPo.setDisabled(disabled);
    }

    public LocalDateTime getGmtCreate() {
        return goodsSpuPo.getGmtCreate();
    }

    public void setGmtCreate(LocalDateTime gmtCreated) {
        goodsSpuPo.setGmtCreate(gmtCreated);
    }

    public LocalDateTime getGmtModified() {
        return goodsSpuPo.getGmtModified();
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        goodsSpuPo.setGmtModified(gmtModified);
    }
}
