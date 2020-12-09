package cn.edu.xmu.goods.model.vo;

import cn.edu.xmu.goods.model.bo.*;
import cn.edu.xmu.shop.model.vo.ShopSimpleVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import cn.edu.xmu.ooad.util.JacksonUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

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
    @ApiModelProperty(value = "品牌")
    private BrandSimpleRetVo brand;
    @ApiModelProperty(value = "种类")
    private GoodsCategorySimpleVo category;
    @ApiModelProperty(value = "运费模板id")
    private Long freightId;
    @ApiModelProperty(value = "店铺")
    private ShopSimpleVo shop;
    @ApiModelProperty(value = "商品条码")
    private String goodsSn;
    @ApiModelProperty(value = "商品细节")
    private String detail;
    @ApiModelProperty(value = "商品图片链接")
    private String imageUrl;

    @ApiModelProperty(value = "规格")
    private Spec spec;
    @ApiModelProperty(value = "Sku")
    private List<GoodsSkuSimpleRetVo> sku;
    @ApiModelProperty(value = "是否被逻辑删除")
    private Byte disabled;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;
    @ApiModelProperty(value = "编辑时间")
    private LocalDateTime gmtModified;
    /**
     * 用Bo le对象建立Vo对象
     *
     * @author 24320182203216 Yancheng Lai
     * @param goodsSpu
     * @return GoodsSpuRetVo
     * createdBy Yancheng Lai 2020/12/01 19:30
     * modifiedBy Yancheng Lai 2020/12/01 19:30
     */
    //brand category shop sku未初始化
    public GoodsSpuRetVo(GoodsSpu goodsSpu){
        this.setId(goodsSpu.getId());
        this.setName(goodsSpu.getName());
        this.setFreightId(goodsSpu.getFreightId());
        this.setGoodsSn(goodsSpu.getGoodsSn());
        this.setDetail(goodsSpu.getDetail());
        this.setImageUrl(goodsSpu.getImageUrl());
        try{
            Spec spec = new Spec( goodsSpu.getSpec());
            setSpec(spec);
        }
        catch (Exception e){

        }
        this.setGmtCreate(goodsSpu.getGmtCreate());
        this.setGmtModified(goodsSpu.getGmtModified());

    }

    public GoodsSpuRetVo(){}

}
