package cn.edu.xmu.goods.model.bo;

import cn.edu.xmu.goods.model.po.GoodsSpuPo;
import cn.edu.xmu.goods.model.vo.*;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.goods.model.po.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/1 19:48
 * modifiedBy Yancheng Lai 19:48
 **/
@Data
public class GoodsSku implements VoObject, Serializable {

    private GoodsSkuPo goodsSkuPo;

    private Byte statecode;
//    State state;
    public enum State {
        WAITING(0, "未上架"),
        INVALID(4, "上架"),
        DELETED(6, "已删除");

        private static final Map<Integer, State> stateMap;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap();
            for (GoodsSku.State enum1 : values()) {
                stateMap.put(enum1.code, enum1);
            }
        }

        private int code;
        private String description;

        State(int code, String description) {
            this.code = code;
            this.description = description;
        }


        public static GoodsSku.State getTypeByCode(Byte code) {
            return stateMap.get(code);
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

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
        this.statecode = goodsSkuPo.getState();
        this.goodsSkuPo = goodsSkuPo;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        goodsSkuPo.setGmtCreate(gmtCreate);
    }

    public GoodsSku(GoodsSkuPostVo goodsSkuPostVo){
        this.goodsSkuPo = new GoodsSkuPo();
        this.setSkuSn(goodsSkuPostVo.getSn());
        this.setName(goodsSkuPostVo.getName());
        this.setOriginalPrice(goodsSkuPostVo.getOriginalPrice());
        this.setConfiguration(goodsSkuPostVo.getConfiguration());
        this.setWeight(goodsSkuPostVo.getWeight());
        this.setImageUrl(goodsSkuPostVo.getImageUrl());
        this.setInventory(goodsSkuPostVo.getInventory());
        this.setDetail(goodsSkuPostVo.getDetail());
        this.setGmtCreate(LocalDateTime.now());
        this.setGmtModified(LocalDateTime.now());
    }

    /**
    * @Description: Vo构造 
    * @Param: [goodsSkuRetVo] 
    * @return:  
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 15:41
    */
    public GoodsSku(GoodsSkuRetVo goodsSkuRetVo){
        this.goodsSkuPo = new GoodsSkuPo();
        this.statecode = (byte)State.WAITING.getCode();
        this.goodsSkuPo.setDetail(goodsSkuRetVo.getDetail());
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
        this.goodsSkuPo.setGmtCreate(goodsSkuRetVo.getGmtCreate());
        this.goodsSkuPo.setGmtModified(goodsSkuRetVo.getGmtModified());
    }

    public GoodsSku(OrderItemVo vo){
        setId(vo.getSkuId());
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

    public LocalDateTime getGmtCreate() {
        return goodsSkuPo.getGmtCreate();
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        goodsSkuPo.setGmtCreate(gmtCreated);
    }

    public LocalDateTime getGmtModified() {
        return goodsSkuPo.getGmtModified();
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        goodsSkuPo.setGmtModified(gmtModified);
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
