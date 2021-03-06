package cn.edu.xmu.shop.model.vo;

import cn.edu.xmu.shop.model.bo.Shop;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopVo {
    @ApiModelProperty(value = "店铺名字")
    String shopName;
    @ApiModelProperty(value = "店铺id")
    Long id;
    @ApiModelProperty(value = "店铺状态")
    Byte state=(byte) Shop.State.UNAUDITED.getCode();
    @ApiModelProperty(value = "创建时间")
    LocalDateTime gmtCreate;
    @ApiModelProperty(value = "修改时间")
    LocalDateTime gmtModified;

    public ShopVo(Shop shop) {
        this.setId(shop.getId());
        this.setShopName(shop.getShopName());
        this.setState(shop.getState());
        this.setGmtCreate(shop.getGmtCreate());
        this.setGmtModified(shop.getGmtModified());
    }


    public Shop createShop(){
        Shop shop=new Shop();
        shop.setId(this.id);
        shop.setShopName(this.shopName);
        shop.setState((byte)Shop.State.UNAUDITED.getCode());
        shop.setGmtCreate(LocalDateTime.now());
        shop.setGmtModified(LocalDateTime.now());
        return shop;
    }
}
