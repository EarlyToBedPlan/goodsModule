package cn.edu.xmu.goods.model.vo;

import cn.edu.xmu.goods.model.bo.GoodsCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/7 18:20
 * modifiedBy Yancheng Lai 18:20
 **/
@Data
@ApiModel(description = "种类简略视图对象")
public class GoodsCategorySimpleVo {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "名称")
    String name;


}
