package cn.edu.xmu.goods.model.vo;

import cn.edu.xmu.goods.model.bo.Spec;
import cn.edu.xmu.goods.model.bo.SpecItems;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/1 20:19
 * modifiedBy Yancheng Lai 20:19
 **/
@Data
@ApiModel(description = "商品规格对象")
public class GoodsSpecVo {
    @ApiModelProperty(value = "Specid")
    private Integer specid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "Spec对象")
    private Spec spec;

}
