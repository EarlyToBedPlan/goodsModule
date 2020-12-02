package cn.edu.xmu.goods.model.vo;

import cn.edu.xmu.goods.model.bo.Shop;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商店的店铺视图对象
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 18:08
 * modifiedBy Yancheng Lai 18:08
 **/
@Data
@ApiModel(description = "商店对象")
public class ShopRetVo {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "状态")
    private Integer state;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;
    @ApiModelProperty(value = "编辑时间")
    private LocalDateTime gmtModified;

    public ShopRetVo(Shop shop){
        this.id = shop.getId();
        this.name = shop.getName();
        this.state = shop.getState();
        this.gmtCreate  =shop.getGmtCreate();
        this.gmtModified = shop.getGmtModified();
    }
}
