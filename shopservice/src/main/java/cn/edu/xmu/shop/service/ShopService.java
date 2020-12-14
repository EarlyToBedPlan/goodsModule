package cn.edu.xmu.shop.service;

import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.shop.dao.ShopDao;
import cn.edu.xmu.shop.model.bo.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ruzhen Chang
 */
public interface ShopService {


    /**
     * @description 创建新店铺
     */
    ReturnObject<VoObject> createShop(Shop shop);

    @Transactional
    ReturnObject updateShop(Shop shop);


    @Transactional
    ReturnObject closeShop(Long shopId);


    @Transactional
    ReturnObject auditShop(Long shopId, Boolean conclusion);

    @Transactional
    ReturnObject onShelvesShop(Long shopId);

    @Transactional
    ReturnObject offShelvesShop(Long shopId);


}