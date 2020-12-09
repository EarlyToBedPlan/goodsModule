package cn.edu.xmu.shop.service;

import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.shop.dao.ShopDao;
import cn.edu.xmu.shop.model.bo.Shop;
import cn.edu.xmu.shop.model.po.ShopPo;
import cn.edu.xmu.shop.model.vo.ShopVo;
import com.mysql.cj.log.Log;
import net.bytebuddy.asm.Advice;
import org.apache.tomcat.util.security.Escape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ruzhen Chang
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    ShopDao shopDao;
    private Logger logger =  LoggerFactory.getLogger(ShopServiceImpl.class);


    /**
     * @param shop
     * @return
     * @description 新建店铺
     * @author Ruzhen Chang
     */
    @Transactional
    @Override
    public ReturnObject<VoObject> createShop(Shop shop) {
        ReturnObject returnObject = new ReturnObject();
        try {
            boolean checkShopName = shopDao.checkShopName(shop.getShopName());
            if (checkShopName) {
                return new ReturnObject<>(ResponseCode.valueOf("该姓名已被占用"));
            }
            if (shop.getId() == -1) {
                return new ReturnObject<>(ResponseCode.USER_HASSHOP);
            }
            ShopPo newShopPo=shopDao.insertShop(shop);
            VoObject returnVo=shop.createVo();
            return new ReturnObject<>(returnVo);

        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }


    /**
     * @Description 修改店铺信息
     * @author Ruzhen Chang
     */
    @Transactional
    @Override
    public ReturnObject updateShop(Shop shop) {
        try {
            ShopPo po = shopDao.getShopById(shop.getId());
            if (po == null) {
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("店铺不存在 id：" + shop.getId()));
            }
            return shopDao.updateShop(shop);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }


    /**
     * @description 关闭店铺
     * @author Ruzhen Chang
     * @param shopId
     * @return
     */
    @Transactional
    @Override
    public ReturnObject closeShop(Long shopId) {
        try {
            if (shopDao.getShopById(shopId) == null) {
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("店铺 id：" + shopId));
            }
            shopDao.closeShop(shopId);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }

        return new ReturnObject();
    }



    @Transactional
    @Override
    public ReturnObject auditShop(Long shopId, Boolean conclusion) {
        try {
            if (shopDao.getShopById(shopId) == null) {
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("店铺 id：" + shopId));
            }
            shopDao.auditShop(shopId,conclusion);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }

        return new ReturnObject();
    }

    /**
     * @description 上线店铺
     * @author Ruzhen Chang
     * @param shopId
     * @return
     */
    @Transactional
    @Override
    public ReturnObject onShelvesShop(Long shopId) {
        try {
            if (shopDao.getShopById(shopId) == null) {
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("店铺 id：" + shopId));
            }
            shopDao.onShelvesShop(shopId);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }

        return new ReturnObject();
    }


    /**
     * @description 下线店铺
     * @author Ruzhen Chang
     * @param shopId
     * @return
     */
    @Transactional
    @Override
    public ReturnObject offShelvesShop(Long shopId) {
        try {
            if (shopDao.getShopById(shopId) == null) {
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("店铺 id：" + shopId));
            }
            shopDao.offShelvesShop(shopId);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }

        return new ReturnObject();
    }
}
