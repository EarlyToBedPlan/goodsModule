package cn.edu.xmu.goods.model.vo;

import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.bo.GoodsSpu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * SKU返回简单视图
 *
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/1 19:32
 * modifiedBy Yancheng Lai 19:32
 **/
@Data
@ApiModel(description = "商品SPU简略视图对象")
public class GoodsSpuSimpleRetVo {
    @ApiModelProperty(value = "Spuid")
    private Long id;
    @ApiModelProperty(value = "Spu名称")
    private String name;
    @ApiModelProperty(value = "商品条码")
    private String goodsSn;
    @ApiModelProperty(value = "商品图片链接")
    private String imageUrl;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreated;
    @ApiModelProperty(value = "编辑时间")
    private LocalDateTime gmtModified;
    @ApiModelProperty(value = "是否被逻辑删除")
    private Byte disabled;

    /**
    * @Description:  SPU返回简单视图
    * @Param: [goodsSpu]
    * @return:  GoodsSpuSimpleRetVo
    * @Author: Yancheng Lai
    * @Date: 2020/12/1 19:40
    */

    public GoodsSpuSimpleRetVo(GoodsSpu goodsSpu){
        this.disabled = goodsSpu.getDisabled();
//        this.gmtCreated = goodsSpu.getGmtCreated();
//        this.gmtModified = goodsSpu.getGmtModified();
        this.goodsSn = goodsSpu.getGoodsSn();
        this.id = goodsSpu.getId();
        this.imageUrl = goodsSpu.getImageUrl();
        this.name = goodsSpu.getName();

    }
}
