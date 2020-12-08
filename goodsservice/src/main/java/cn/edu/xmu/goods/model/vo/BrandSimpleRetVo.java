package cn.edu.xmu.goods.model.vo;

import cn.edu.xmu.goods.model.bo.Brand;
import cn.edu.xmu.ooad.model.VoObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 品牌简略视图对象
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 18:16
 * modifiedBy Yancheng Lai 18:16
 **/
@Data
@ApiModel(description = "品牌简略视图对象")
public class BrandSimpleRetVo {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "图片url")
    private String imageUrl;

    public BrandSimpleRetVo(Brand brand){
        this.setId( brand.getId());
        this.setName ( brand.getName());
        this.setImageUrl( brand.getImageUrl());;
    }
}
