package cn.edu.xmu.goods.model.bo;

import cn.edu.xmu.goods.model.po.GoodsSpuPo;
import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSpuRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSpuSimpleRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/** 
* @Description: 商品SKU Bo
* @Author: Yancheng Lai
* @Date: 2020/12/1 20:08
*/



@Data
public class GoodsSpu implements VoObject, Serializable {

    public enum State {
        WAITING(0, "还未开始的"),
        INVALID(4, "被强制下线的"),
        DELETED(6, "已被删除的");

        private static final Map<Integer, State> stateMap;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap();
            for (GoodsSpu.State enum1 : values()) {
                stateMap.put(enum1.code, enum1);
            }
        }

        private int code;
        private String description;

        State(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static GoodsSpu.State getTypeByCode(Integer code) {
            return stateMap.get(code);
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
    private GoodsSpuPo goodsSpuPo;

    /**
    * @Description:  构造函数byPo
    * @Param: [goodsSpuPo]
    * @return:  GoodsSpu
    * @Author: Yancheng Lai
    * @Date: 2020/12/1 19:51
    */
    public GoodsSpu(GoodsSpuPo goodsSpuPo) {
        this.goodsSpuPo = new GoodsSpuPo(goodsSpuPo);
    }

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
        this.setGmtModified(goodsSpuRetVo.getGmtModified());
        this.setGmtCreated(goodsSpuRetVo.getGmtCreated());
        this.setId(goodsSpuRetVo.getId());
        this.setImageUrl(goodsSpuRetVo.getImageUrl());
        this.setName(goodsSpuRetVo.getName());
        this.setShopId(goodsSpuRetVo.getShop().getId());
        this.setSpec(goodsSpuRetVo.getSpec().toString());
        this.setState(goodsSpuRetVo.getState());
        this.setGoodsSn(goodsSpuRetVo.getGoodsSn());
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

    public Byte getState() {
        return goodsSpuPo.getState();
    }

    public void setState(Byte state) {
        goodsSpuPo.setState(state);
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

    public LocalDateTime getGmtCreated() {
        return goodsSpuPo.getGmtCreated();
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        goodsSpuPo.setGmtCreated(gmtCreated);
    }

    public LocalDateTime getGmtModified() {
        return goodsSpuPo.getGmtModified();
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        goodsSpuPo.setGmtModified(gmtModified);
    }
}
