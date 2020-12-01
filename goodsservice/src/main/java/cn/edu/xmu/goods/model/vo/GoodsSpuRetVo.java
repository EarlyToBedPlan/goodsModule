package cn.edu.xmu.goods.model.vo;

import cn.edu.xmu.goods.model.bo.GoodsSpu;
import cn.edu.xmu.goods.model.bo.SpecItems;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import cn.edu.xmu.ooad.util.JacksonUtil;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * SPU访问类
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/11/02 13:57
 * modifiedBy Yancheng Lai 2020/11/7 19:20
 **/

@Data
@ApiModel(description = "商品SPU视图对象")
public class GoodsSpuRetVo {
    @ApiModelProperty(value = "Spuid")
    private Long id;
    @ApiModelProperty(value = "Spu名称")
    private String name;
    @ApiModelProperty(value = "品牌id")
    private Long brandId;
    @ApiModelProperty(value = "种类id")
    private Long categoryId;
    @ApiModelProperty(value = "运费模板id")
    private Long freightId;
    @ApiModelProperty(value = "店铺id")
    private Long shopId;
    @ApiModelProperty(value = "商品条码")
    private String goodsSn;
    @ApiModelProperty(value = "商品细节")
    private String detail;
    @ApiModelProperty(value = "商品图片链接")
    private String imageUrl;
    @ApiModelProperty(value = "状态")
    private Byte state;
    @ApiModelProperty(value = "规格")
    private List<SpecItems> spec;
    @ApiModelProperty(value = "是否被逻辑删除")
    private Byte disabled;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreated;
    @ApiModelProperty(value = "编辑时间")
    private LocalDateTime gmtModified;
    /**
     * 用Role对象建立Vo对象
     *
     * @author 24320182203216 Yancheng Lai
     * @param goodsSpu
     * @return GoodsSpuRetVo
     * createdBy Yancheng Lai 2020/12/01 19:30
     * modifiedBy Yancheng Lai 2020/12/01 19:30
     */

    public GoodsSpuRetVo(GoodsSpu goodsSpu){
        this.brandId = goodsSpu.getBrandId();
        this.categoryId = goodsSpu.getCategoryId();
        this.detail = goodsSpu.getDetail();
        this.disabled = goodsSpu.getDisabled();
        this.freightId = goodsSpu.getFreightId();
        this.gmtCreated = goodsSpu.getGmtCreated();
        this.gmtModified = goodsSpu.getGmtModified();
        this.goodsSn = goodsSpu.getGoodsSn();
        this.id = goodsSpu.getId();
        this.imageUrl = goodsSpu.getImageUrl();
        this.shopId = goodsSpu.getShopId();
        this.name = goodsSpu.getName();
        this.state = goodsSpu.getState();
        this.spec = (List<SpecItems>) JacksonUtil.toNode(goodsSpu.getSpec());
    }

}
