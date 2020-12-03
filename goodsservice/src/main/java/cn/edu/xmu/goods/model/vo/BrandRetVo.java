package cn.edu.xmu.goods.model.vo;

import cn.edu.xmu.goods.model.bo.Brand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 品牌的展示类
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 17:59
 * modifiedBy Yancheng Lai 17:59
 **/

@Data
@ApiModel(description = "品牌视图对象")
public class BrandRetVo {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "图片url")
    private String imageUrl;
    @ApiModelProperty(value = "详细信息")
    private String detail;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;
    @ApiModelProperty(value = "编辑时间")
    private LocalDateTime gmtModified;

    public BrandRetVo(Brand brand){
        this.id = brand.getId();
        this.name = brand.getName();
        this.imageUrl = brand.getImageUrl();
        this.detail = brand.getDetail();
        this.gmtCreate = brand.getGmtCreate();
        this.gmtModified = brand.getGmtModified();
    }
}
