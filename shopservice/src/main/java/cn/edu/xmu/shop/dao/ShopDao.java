package cn.edu.xmu.shop.dao;

import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.shop.mapper.ShopPoMapper;
import cn.edu.xmu.shop.model.bo.Shop;
import cn.edu.xmu.shop.model.po.ShopPo;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.shop.model.po.ShopPoExample;
import cn.edu.xmu.shop.model.vo.ShopVo;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.accessibility.AccessibleRelation;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


/**
 * @author Ruzhen Chang
 */
@Repository
public class ShopDao implements InitializingBean{
    @Autowired
    private ShopPoMapper shopPoMapper;

    private  static  final Logger logger = LoggerFactory.getLogger(ShopDao.class);

    @Value("${privilegeservice.initialization}")
    private Boolean initialization;

    @Override
    public void afterPropertiesSet() {

    }
    /**
     * 由店铺id获得店铺
     * @author Ruzhen Chang
     */
    public ShopPo getShopById(long shopId){
        ShopPo po=new ShopPo();
        try{
            po=shopPoMapper.selectByPrimaryKey(shopId);
        }catch (DataAccessException e){
            StringBuilder message=new StringBuilder().append("getShopById:").append(e.getMessage());
            logger.debug(message.toString());
        }
        return po;

    }

    /**
     * 获得店铺所有状态
     * @author Ruzhen Chang
     */
    public Byte getShopState(long shopId){
        ShopPo po=new ShopPo();
        Byte shopState=0;
        try{
            po=shopPoMapper.selectByPrimaryKey(shopId);
            shopState= po.getState();
        }catch (DataAccessException e){
            StringBuilder message=new StringBuilder().append("getShopById:").append(e.getMessage());
            logger.debug(message.toString());
        }
        return shopState;
    }

    /**
     * @Description 检查店铺名是否重复
     * @author Ruzhen Chang
     */
    public Boolean checkShopName(String shopName){
        ShopPoExample example=new ShopPoExample();
        ShopPoExample.Criteria criteria= example.createCriteria();
        criteria.andStateNotEqualTo((byte)Shop.State.FAILED.getCode());
        if(shopName!=null) {
            criteria.andNameEqualTo(shopName);
        }
        boolean empty=false;
        try{
            List<ShopPo> shopPos=shopPoMapper.selectByExample(example);
            empty=shopPos.isEmpty();
        } catch (Exception e){
            logger.error("发生严重服务器内部错误："+e.getMessage());
        }
        return !empty;
    }



    /**
     * 店家修改店铺信息
     * @author Ruzhen Chang
     */
    public ReturnObject<Shop> updateShop(Shop shop){
        ShopPo shopPo= new ShopPo();
        shopPo.setId(shop.getId());
        shopPo.setName(shop.getShopName());
        shopPo.setGmtModified(shop.getGmtModified());
        ReturnObject<Shop> returnObject=null;
        try {
            int ret = shopPoMapper.updateByPrimaryKeySelective(shopPo);
            if (ret == 0) {
                logger.debug("updateShop: update fail. shopId: " + shop.getId());
                returnObject = new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
            } else {
                logger.debug("updateShop: update shop success : " + shop.toString());
                returnObject = new ReturnObject();
            }
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return returnObject;

    }


    /**
     * 关闭店铺
     * @author Ruzhen Chang
     */
    public ReturnObject closeShop(long shopId){
        ReturnObject returnObject = null;
        ShopPo shopPo = new ShopPo();
        shopPo.setId(shopId);

        try {
            int ret;
            if(shopPo.getState()==Shop.State.UNAUDITED.getCode()) {
                ret = shopPoMapper.deleteByPrimaryKey(shopId);
            }
            else{
                shopPo.setState((byte) Shop.State.DELETE.getCode());
                ret = shopPoMapper.updateByPrimaryKeySelective(shopPo);
            }
            if (ret == 0) {
                logger.debug("closeShop fail. shopId: " + shopId);
                returnObject = new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
            } else {
                logger.debug("closeShop success. shopId: " + shopId);
                returnObject = new ReturnObject();
            }
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return returnObject;
    }


    /**
     * 审核店铺信息
     * @author Ruzhen Chang
     */
    public ReturnObject<Object> auditShop(long shopId,boolean conclusion){
        ReturnObject returnObject = null;
        ShopPo shopPo = new ShopPo();
        shopPo.setId(shopId);
        try {
            if(conclusion) {
                shopPo.setState((byte) Shop.State.OFFLINE.getCode());
            } else {
                shopPo.setState((byte)Shop.State.FAILED.getCode());
            }
            int ret = shopPoMapper.updateByPrimaryKeySelective(shopPo);
            if (ret == 0) {
                logger.debug("closeShop fail. shopId: " + shopId);
                returnObject = new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
            } else {
                logger.debug("closeShop success. shopId: " + shopId);
                returnObject = new ReturnObject();
            }
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return returnObject;

    }

    /**
     * 上线店铺
     * @author Ruzhen Chang
     */
    public ReturnObject onShelvesShop(long shopId){
        ReturnObject returnObject = null;
        ShopPo shopPo = new ShopPo();
        shopPo.setId(shopId);
        shopPo.setState((byte) Shop.State.ONLINE.getCode());
        try {
            int ret = shopPoMapper.updateByPrimaryKeySelective(shopPo);
            if (ret == 0) {
                logger.debug("closeShop fail. shopId: " + shopId);
                returnObject = new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
            } else {
                logger.debug("closeShop success. shopId: " + shopId);
                returnObject = new ReturnObject();
            }
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return returnObject;
    }


    /**
     * 下线店铺
     * @author Ruzhen Chang
     */
    public ReturnObject offShelvesShop(long shopId){
        ReturnObject returnObject = null;
        ShopPo shopPo = new ShopPo();
        shopPo.setId(shopId);
        shopPo.setState((byte) Shop.State.OFFLINE.getCode());
        try {
            int ret = shopPoMapper.updateByPrimaryKeySelective(shopPo);
            if (ret == 0) {
                logger.debug("closeShop fail. shopId: " + shopId);
                returnObject = new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
            } else {
                logger.debug("closeShop success. shopId: " + shopId);
                returnObject = new ReturnObject();
            }
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return returnObject;
    }

    /**
     * @Description 新增店铺
     * @author Ruzhen Chang
     */
    public ShopPo insertShop(Shop shop) {
        ShopPo shopPo = shop.createPo();
        try {
            shopPoMapper.insert(shopPo);
            //插入成功
            logger.debug("insertShop: insert shop = " + shopPo.toString());
        } catch (Exception e) {
            shopPo = null;
            logger.error("严重错误：" + e.getMessage());
        }
        return shopPo;
    }



}
