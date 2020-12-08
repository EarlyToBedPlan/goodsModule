package cn.edu.xmu.goods.model.vo;

import cn.edu.xmu.goods.model.bo.GoodsSku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/1 21:40
 * modifiedBy Yancheng Lai 21:40
 **/
@Data
@ApiModel(description = "商品SKU简略视图对象")
public class GoodsSkuSimpleRetVo {
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
    @ApiModelProperty(value = "图片链接")
    private String imageUrl;
    @ApiModelProperty(value = "库存量")
    private Integer inventory;
    @ApiModelProperty(value = "是否被逻辑删除")
    private Byte disabled;

    /**
    * @Description: SKU返回简单视图
    * @Param: [goodSku]
    * @return:  GoodsSkuSimpleRetVo
    * @Author: Yancheng Lai
    * @Date: 2020/12/1 21:47
    */

    public GoodsSkuSimpleRetVo(GoodsSku goodSku){
        this.setDisabled( goodSku.getDisabled());
        this.setId (goodSku.getId());
        this.setImageUrl(goodSku.getImageUrl());
        this.setInventory( goodSku.getInventory());
        this.setName(goodSku.getName());
        this.setOriginalPrice ( goodSku.getOriginalPrice());
        //this.price = goodSku.getPrice();
        this.setSkuSn ( goodSku.getSkuSn());
    }
}
