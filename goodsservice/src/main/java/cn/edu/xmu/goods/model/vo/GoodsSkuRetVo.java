package cn.edu.xmu.goods.model.vo;

import cn.edu.xmu.goods.dao.GoodsSpuDao;
import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.bo.GoodsSpu;
import cn.edu.xmu.goods.model.po.GoodsSkuPo;
import cn.edu.xmu.goods.service.GoodsSpuService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

    public static final Logger logger = LoggerFactory.getLogger("GoodsSkuRetVo");
//    @Autowired
//    GoodsSpuService goodsSpuService;
    @ApiModelProperty(value = "Skuid")
    private Long id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "Sku条码")
    private String skuSn;
    @ApiModelProperty(value = "详细描述")
    private String detail;
    @ApiModelProperty(value = "图片链接")
    private String imageUrl;
    @ApiModelProperty(value = "原价")
    private Long originalPrice;
    @ApiModelProperty(value = "现价")
    private Integer price;
    @ApiModelProperty(value = "库存")
    private Integer inventory;
    @ApiModelProperty(value = "状态")
    private Integer state;
    @ApiModelProperty(value = "配置参数json")
    private String configuration;
    @ApiModelProperty(value = "重量")
    private Long weight;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;
    @ApiModelProperty(value = "编辑时间")
    private LocalDateTime gmtModified;
//    @ApiModelProperty(value = "Spuid")
//    private Long goodsSpuId;
    @ApiModelProperty(value = "Spu")
    private GoodsSpuRetVo goodsSpu;
    @ApiModelProperty(value = "是否被逻辑删除")
    private Byte disabled;

    //price goodsSpu未初始化
    public GoodsSkuRetVo(GoodsSku goodsSku){
//        this.configuration = goodsSku.getConfiguration();
//        this.detail = goodsSku.getDetail();
//        this.disabled = goodsSku.getDisabled();
//        this.gmtCreate = goodsSku.getGmtCreate();
//        this.gmtModified = goodsSku.getGmtModified();
//        this.id = goodsSku.getId();
//        this.imageUrl = goodsSku.getImageUrl();
//        this.inventory = goodsSku.getInventory();
//        this.skuSn = goodsSku.getSkuSn();
//        this.weight = goodsSku.getWeight();
        //this.price = goodsSku.getp;
        //this.goodsSpu = goodsSpuService.findSpuById(goodsSku.getGoodsSpuId()).getData();
        logger.info("paraGoods skuname == "+goodsSku.getName());
        this.setOriginalPrice(goodsSku.getOriginalPrice());
        this.setName(goodsSku.getName());
        this.setConfiguration( goodsSku.getConfiguration());
        this.setDetail (goodsSku.getDetail());
        this.setDisabled ( goodsSku.getDisabled());
        this.setGmtCreate ( goodsSku.getGmtCreate());
        this.setGmtModified ( goodsSku.getGmtModified());
        this.setId ( goodsSku.getId());
        this.setImageUrl (goodsSku.getImageUrl());
        this.setInventory ( goodsSku.getInventory());
        this.setSkuSn ( goodsSku.getSkuSn());
        this.setWeight (goodsSku.getWeight());
        StateVo state = new StateVo();
        this.setState((int)goodsSku.getStatecode());
        //this.setState(goodsSku.getState());
    }


    public GoodsSkuRetVo() {

    }
}
