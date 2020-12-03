package cn.edu.xmu.goods.model.bo;

import cn.edu.xmu.goods.model.po.GoodsSpuPo;
import cn.edu.xmu.goods.model.vo.GoodsSpuRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSpuSimpleRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.goods.model.po.*;
import lombok.Data;
import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSkuSimpleRetVo;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/1 19:48
 * modifiedBy Yancheng Lai 19:48
 **/
@Data
public class GoodsSku implements VoObject, Serializable {

    private GoodsSkuPo goodsSkuPo;
//    private Long id;
//
//    private Long goodsSpuId;
//
//    private String skuSn;
//
//    private String name;
//
//    private Long originalPrice;
//
//    private String configuration;
//
//    private Long weight;
//
//    private Integer price;
//
//    private String imageUrl;
//
//    private Integer inventory;
//
//    private String detail;
//
//    private Byte disabled;
//
//    private LocalDateTime gmtCreated;
//
//    private LocalDateTime gmtModified;

    @Override
    public GoodsSkuRetVo createVo() {
        return new GoodsSkuRetVo(this);
    }

    @Override
    public GoodsSkuSimpleRetVo createSimpleVo() {
        return new GoodsSkuSimpleRetVo(this);
    }


    /**
    * @Description: 构造函数by Po
    * @Param: [goodsSkuPo]
    * @return:  GoodsSku
    * @Author: Yancheng Lai
    * @Date: 2020/12/1 19:56
    */
    public GoodsSku(GoodsSkuPo goodsSkuPo) {
        this.goodsSkuPo.setDetail(goodsSkuPo.getDetail());
        this.goodsSkuPo.setDisabled(goodsSkuPo.getDisabled());
        this.goodsSkuPo.setDisabled(goodsSkuPo.getDisabled());
        this.goodsSkuPo.setId(goodsSkuPo.getId());
        this.goodsSkuPo.setImageUrl(goodsSkuPo.getImageUrl());
        this.goodsSkuPo.setName(goodsSkuPo.getName());
        this.goodsSkuPo.setGoodsSpuId(goodsSkuPo.getGoodsSpuId());
        this.goodsSkuPo.setSkuSn(goodsSkuPo.getSkuSn());
        this.goodsSkuPo.setOriginalPrice(goodsSkuPo.getOriginalPrice());
        this.goodsSkuPo.setConfiguration(goodsSkuPo.getConfiguration());
        this.goodsSkuPo.setWeight(goodsSkuPo.getWeight());
        this.goodsSkuPo.setInventory(goodsSkuPo.getInventory());
    }

    /** 
    * @Description: Vo构造 
    * @Param: [goodsSkuRetVo] 
    * @return:  
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 15:41
    */
    public GoodsSku(GoodsSkuRetVo goodsSkuRetVo){
        this.goodsSkuPo.setDetail(goodsSkuRetVo.getDetail());
        this.goodsSkuPo.setDisabled(goodsSkuRetVo.getDisabled());
        this.goodsSkuPo.setDisabled(goodsSkuRetVo.getDisabled());
        this.goodsSkuPo.setId(goodsSkuRetVo.getId());
        this.goodsSkuPo.setImageUrl(goodsSkuRetVo.getImageUrl());
        this.goodsSkuPo.setName(goodsSkuRetVo.getName());
        this.goodsSkuPo.setGoodsSpuId(goodsSkuRetVo.getGoodsSpu().getId());
        this.goodsSkuPo.setSkuSn(goodsSkuRetVo.getSkuSn());
        this.goodsSkuPo.setOriginalPrice(goodsSkuRetVo.getOriginalPrice());
        this.goodsSkuPo.setConfiguration(goodsSkuRetVo.getConfiguration());
        this.goodsSkuPo.setWeight(goodsSkuRetVo.getWeight());
        this.goodsSkuPo.setInventory(goodsSkuRetVo.getInventory());
    }
    /** 
    * @Description: 得到Po 
    * @Param: [] 
    * @return: cn.edu.xmu.goods.model.po.GoodsSkuPo 
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 17:05
    */
    public GoodsSkuPo getPo(){
        return this.goodsSkuPo;
    }

    public Long getId() {
        return goodsSkuPo.getId();
    }

    public void setId(Long id) {
        goodsSkuPo.setId(id);
    }

    public Long getGoodsSpuId() {
        return goodsSkuPo.getGoodsSpuId();
    }

    public void setGoodsSpuId(Long goodsSpuId) {
        goodsSkuPo.setGoodsSpuId(goodsSpuId);
    }

    public String getSkuSn() {
        return goodsSkuPo.getSkuSn();
    }

    public void setSkuSn(String skuSn) {
        goodsSkuPo.setSkuSn(skuSn);
    }

    public String getName() {
        return goodsSkuPo.getName();
    }

    public void setName(String name) {
        goodsSkuPo.setName(name);
    }

    public Long getOriginalPrice() {
        return goodsSkuPo.getOriginalPrice();
    }

    public void setOriginalPrice(Long originalPrice) {
        goodsSkuPo.setOriginalPrice(originalPrice);
    }

    public String getConfiguration() {
        return goodsSkuPo.getConfiguration();
    }

    public void setConfiguration(String configuration) {
        goodsSkuPo.setConfiguration(configuration);
    }

    public Long getWeight() {
        return goodsSkuPo.getWeight();
    }

    public void setWeight(Long weight) {
        goodsSkuPo.setWeight(weight);
    }

    public String getImageUrl() {
        return goodsSkuPo.getImageUrl();
    }

    public void setImageUrl(String imageUrl) {
        goodsSkuPo.setImageUrl(imageUrl);
    }

    public Integer getInventory() {
        return goodsSkuPo.getInventory();
    }

    public void setInventory(Integer inventory) {
        goodsSkuPo.setInventory(inventory);
    }

    public String getDetail() {
        return goodsSkuPo.getDetail();
    }

    public void setDetail(String detail) {
        goodsSkuPo.setDetail(detail);
    }

    public Byte getDisabled() {
        return goodsSkuPo.getDisabled();
    }

    public void setDisabled(Byte disabled) {
        goodsSkuPo.setDisabled(disabled);
    }

}
