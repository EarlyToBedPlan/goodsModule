package cn.edu.xmu.shop.model.vo;

import cn.edu.xmu.shop.model.bo.Shop;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/9 17:59
 * modifiedBy Yancheng Lai 17:59
 **/
@Data
public class ShopSimpleVo {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "店铺名字")
    private String shopName;

    public ShopSimpleVo(Shop shop){
        setId(shop.getId());
        setShopName(shop.getShopName());
    }

    public Shop createShop(){
        Shop shop=new Shop();
        shop.setId(this.id);
        shop.setShopName(this.shopName);
        shop.setGmtModified(LocalDateTime.now());
        return shop;
    }

}
