package cn.edu.xmu.shop.service;

import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.shop.model.bo.Shop;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/7 18:41
 * modifiedBy Yancheng Lai 18:41
 **/

public interface ShopService {
    public ReturnObject<Shop> getShopById(Long id);
}
