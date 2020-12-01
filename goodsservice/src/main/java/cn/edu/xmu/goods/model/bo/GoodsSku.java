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

    private Long id;

    private Long goodsSpuId;

    private String skuSn;

    private String name;

    private Long originalPrice;

    private String configuration;

    private Long weight;

    private Integer price;

    private String imageUrl;

    private Integer inventory;

    private String detail;

    private Byte disabled;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    @Override
    public Object createVo() {
        return new GoodsSkuRetVo(this);
    }

    @Override
    public Object createSimpleVo() {
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
        this.detail = goodsSkuPo.getDetail();
        this.disabled = goodsSkuPo.getDisabled();
        this.gmtCreated = goodsSkuPo.getGmtCreated();
        this.gmtModified = goodsSkuPo.getGmtModified();
        this.id = goodsSkuPo.getId();
        this.imageUrl = goodsSkuPo.getImageUrl();
        this.name = goodsSkuPo.getName();
        this.goodsSpuId = goodsSkuPo.getGoodsSpuId();
        this.skuSn = goodsSkuPo.getSkuSn();
        this.originalPrice = goodsSkuPo.getOriginalPrice();
        this.configuration = goodsSkuPo.getConfiguration();
        this.weight = goodsSkuPo.getWeight();
        this.inventory = goodsSkuPo.getInventory();
    }
}
