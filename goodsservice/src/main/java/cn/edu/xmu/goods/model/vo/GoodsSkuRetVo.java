package cn.edu.xmu.goods.model.vo;

import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.bo.GoodsSpu;
import cn.edu.xmu.goods.model.po.GoodsSkuPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * SKU访问类
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/1 19:55
 * modifiedBy Yancheng Lai 19:55
 **/

@Data
@ApiModel(description = "商品SKU视图对象")
public class GoodsSkuRetVo {
    @ApiModelProperty(value = "Skuid")
    private Long id;

    @ApiModelProperty(value = "Sku条码")
    private String skuSn;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "原价")
    private Long originalPrice;
    @ApiModelProperty(value = "现价")
    private Integer price;
    @ApiModelProperty(value = "配置参数json")
    private String configuration;
    @ApiModelProperty(value = "重量")
    private Long weight;
    @ApiModelProperty(value = "图片链接")
    private String imageUrl;
    @ApiModelProperty(value = "库存")
    private Integer inventory;
    @ApiModelProperty(value = "详细描述")
    private String detail;
    @ApiModelProperty(value = "是否被逻辑删除")
    private Byte disabled;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreated;
    @ApiModelProperty(value = "编辑时间")
    private LocalDateTime gmtModified;
//    @ApiModelProperty(value = "Spuid")
//    private Long goodsSpuId;
    @ApiModelProperty(value = "Spu")
    private GoodsSpu goodsSpu;

    public GoodsSkuRetVo(GoodsSku goodsSku){
        this.configuration = goodsSku.getConfiguration();
        this.detail = goodsSku.getDetail();
        this.disabled = goodsSku.getDisabled();
        this.gmtCreated = goodsSku.getGmtCreated();
        this.gmtModified = goodsSku.getGmtModified();
        this.id = goodsSku.getId();
        this.imageUrl = goodsSku.getImageUrl();
        this.inventory = goodsSku.getInventory();
        this.skuSn = goodsSku.getSkuSn();
        this.weight = goodsSku.getWeight();
        this.price = null;
        this.goodsSpu = null;
    }


}
