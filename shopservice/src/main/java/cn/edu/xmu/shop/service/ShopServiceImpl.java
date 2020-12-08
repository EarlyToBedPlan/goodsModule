package cn.edu.xmu.shop.service;

import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.shop.dao.ShopDao;
import cn.edu.xmu.shop.model.bo.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/7 18:41
 * modifiedBy Yancheng Lai 18:41
 **/
@Service
public class ShopServiceImpl implements ShopService{
    @Autowired
    ShopDao shopDao;

    public ReturnObject<Shop> getShopById(Long id){
        Shop shop = new Shop(shopDao.getShopById(id));
        return new ReturnObject<>(shop);
    }
}
