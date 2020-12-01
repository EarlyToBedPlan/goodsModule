package cn.edu.xmu.goods.model.bo;

import cn.edu.xmu.goods.model.po.GoodsSpuPo;
import cn.edu.xmu.goods.model.vo.GoodsSpuRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSpuSimpleRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/** 
* @Description: 商品SKU Bo
* @Author: Yancheng Lai
* @Date: 2020/12/1 20:08
*/
@Data
public class GoodsSpu implements VoObject, Serializable {
    private Long id;

    private String name;

    private Long brandId;

    private Long categoryId;

    private Long freightId;

    private Long shopId;

    private String goodsSn;

    private String detail;

    private String imageUrl;

    private Byte state;

    private String spec;

    private Byte disabled;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    /**
    * @Description:  构造函数byPo
    * @Param: [goodsSpuPo]
    * @return:  GoodsSpu
    * @Author: Yancheng Lai
    * @Date: 2020/12/1 19:51
    */
    public GoodsSpu(GoodsSpuPo goodsSpuPo) {
        this.brandId = goodsSpuPo.getBrandId();
        this.categoryId = goodsSpuPo.getCategoryId();
        this.detail = goodsSpuPo.getDetail();
        this.disabled = goodsSpuPo.getDisabled();
        this.freightId = goodsSpuPo.getFreightId();
        this.gmtCreated = goodsSpuPo.getGmtCreated();
        this.gmtModified = goodsSpuPo.getGmtModified();
        this.id = goodsSpuPo.getId();
        this.imageUrl = goodsSpuPo.getImageUrl();
        this.name = goodsSpuPo.getName();
        this.shopId = goodsSpuPo.getShopId();
        this.spec = goodsSpuPo.getSpec();
        this.state = goodsSpuPo.getState();
    }

    @Override
    public Object createVo() {
        return new GoodsSpuRetVo(this);
    }

    @Override
    public GoodsSpuSimpleRetVo createSimpleVo() {
        return new GoodsSpuSimpleRetVo(this);
    }


}
