package cn.edu.xmu.shop.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "管理员用户信息视图对象")

public class ShopVo {
    @ApiModelProperty(value = "店铺名字")
    private String shopName;
}
