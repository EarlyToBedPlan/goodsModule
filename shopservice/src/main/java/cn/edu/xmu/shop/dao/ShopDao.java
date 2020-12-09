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
import java.util.Objects;


@Repository
public class ShopDao implements InitializingBean{
    @Autowired
    private ShopPoMapper shopPoMapper;

    private  static  final Logger logger = LoggerFactory.getLogger(ShopDao.class);

    @Value("${shopservice.initialization}")
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
     * 店家申请店铺
     * @author Ruzhen Chang
     */
    public ReturnObject applyNewShop(String shopName){
        ReturnObject returnObject=null;
        ShopPo shopPo=new ShopPo();
        shopPo.setName(shopName);
        try{
            LocalDateTime localDateTime= LocalDateTime.now();
            returnObject=new ReturnObject<>(shopPoMapper.insert(shopPo));
            logger.debug(("success apply shop:")+shopPo.getId());
            shopPo.setState((byte) 0);
            shopPo.setGmtCreate(localDateTime);
            shopPo.setGmtModified(localDateTime);
        }catch (DataAccessException e){
            if (Objects.requireNonNull(e.getMessage()).contains("auth_shop.shop_name_uindex")) {
                //若有重复名则创建失败
                logger.debug("insertShop: have same shop name= " + shopPo.getName());
                returnObject = new ReturnObject<>(ResponseCode.ROLE_REGISTERED, String.format("用户名重复：" + shopPo.getName()));
            } else {
                logger.debug("sql exception : " + e.getMessage());
                returnObject = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
            }
        }
        catch (Exception e){
            logger.error("other exception: "+e.getMessage());
            returnObject=new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,String.format("发生了严重的数据库错误：%s",e.getMessage()));

        }
        return returnObject;
    }


    /**
     * 店家修改店铺信息
     * @author Ruzhen Chang
     */
    public ReturnObject<Shop> modifyShopById(long shopId){
        ShopPo po=getShopById(shopId);
        ReturnObject<Shop> returnObject=null;
        ShopPoExample shopPoExample=new ShopPoExample();
        ShopPoExample.Criteria criteria=shopPoExample.createCriteria();
        criteria.andIdEqualTo(shopId);
        try{
            int ret = shopPoMapper.updateByPrimaryKey(po);
//
            if(ret==0){
            //修改失败
            logger.debug("updateRole: update role fail : " + po.toString());
            returnObject = new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("角色id不存在：" + po.getId()));
        } else {
            //修改成功
            logger.debug("updateRole: update role = " + po.toString());
            returnObject = new ReturnObject<>();
        }}
        catch (DataAccessException e) {
            if (Objects.requireNonNull(e.getMessage()).contains("auth_role.auth_role_name_uindex")) {
                //若有重复的角色名则修改失败
                logger.debug("updateRole: have same role name = " + po.getName());
                returnObject = new ReturnObject<>(ResponseCode.ROLE_REGISTERED, String.format("角色名重复：" + po.getName()));
            } else {
                // 其他数据库错误
                logger.debug("other sql exception : " + e.getMessage());
                returnObject = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
            }
        }
        catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            returnObject = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
        return returnObject;

    }





    /**
     * 关闭店铺
     * @author Ruzhen Chang
     */
    public ReturnObject closeShop(long shopId){
        ReturnObject retObj=null;
        ShopPo shopPo=new ShopPo();
        shopPo.setId(shopId);
        shopPo.setState((byte) Shop.State.DELETE.getCode());


        ShopPoExample shopPoExample=new ShopPoExample();
        ShopPoExample.Criteria criteria=shopPoExample.createCriteria();

        criteria.andIdEqualTo(shopId);
        try{
            int ret=shopPoMapper.deleteByPrimaryKey(shopId);
            if(ret==0){
                logger.debug("closeShop: id not exist ="+shopId);
                retObj=new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST,String.format("店铺id不存在："+shopId));

            } else {
                logger.debug("closeShop: close shop id ="+shopId);
                retObj=new ReturnObject();
            }
        } catch (Exception e){
            logger.error("发生了严重的服务器内部错误："+e.getMessage());
        }
        return retObj;
    }


    /**
     * 审核店铺信息
     * @author Ruzhen Chang
     */
    public ReturnObject<Object> auditShop(long shopId,boolean conclusion){
        ReturnObject retObj=null;
        ShopPo shopPo=new ShopPo();
        shopPo.setId(shopId);
        if(conclusion)
            shopPo.setState((byte) Shop.State.OFFLINE.getCode());
        else
            shopPo.setState((byte)Shop.State.FAILED.getCode());


        ShopPoExample shopPoExample=new ShopPoExample();
        ShopPoExample.Criteria criteria=shopPoExample.createCriteria();

        criteria.andIdEqualTo(shopId);
        try{
            int ret=shopPoMapper.deleteByPrimaryKey(shopId);
            if(ret==0){
                logger.debug("auditShop: id not exist ="+shopId);
                retObj=new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST,String.format("店铺id不存在："+shopId));

            } else {
                logger.debug("auditShop: audited shop id ="+shopId);
                retObj=new ReturnObject();
            }
        } catch (Exception e){
            logger.error("发生了严重的服务器内部错误："+e.getMessage());
        }
        return retObj;

    }

    /**
     * 上线店铺
     * @author Ruzhen Chang
     */
    public ReturnObject<Object> onlineShop(long shopId){
        ReturnObject retObj=null;
        ShopPo shopPo=new ShopPo();
        shopPo.setId(shopId);
        shopPo.setState((byte) Shop.State.ONLINE.getCode());


        ShopPoExample shopPoExample=new ShopPoExample();
        ShopPoExample.Criteria criteria=shopPoExample.createCriteria();

        criteria.andIdEqualTo(shopId);
        try{
            int ret=shopPoMapper.deleteByPrimaryKey(shopId);
            if(ret==0){
                logger.debug("onlineShop: id not exist ="+shopId);
                retObj=new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST,String.format("店铺id不存在："+shopId));

            } else {
                logger.debug("onlineShop: online shop id ="+shopId);
                retObj=new ReturnObject();
            }
        } catch (Exception e){
            logger.error("发生了严重的服务器内部错误："+e.getMessage());
        }
        return retObj;

    }

    /**
     * 下线店铺
     * @author Ruzhen Chang
     */
    public ReturnObject<Object> offlineShop(long shopId){
        ReturnObject retObj=null;
        ShopPo shopPo=new ShopPo();
        shopPo.setId(shopId);
        shopPo.setState((byte) Shop.State.OFFLINE.getCode());


        ShopPoExample shopPoExample=new ShopPoExample();
        ShopPoExample.Criteria criteria=shopPoExample.createCriteria();

        criteria.andIdEqualTo(shopId);
        try{
            int ret=shopPoMapper.deleteByPrimaryKey(shopId);
            if(ret==0){
                logger.debug("offlineShop: id not exist ="+shopId);
                retObj=new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST,String.format("店铺id不存在："+shopId));

            } else {
                logger.debug("offlineShop: offline shop id ="+shopId);
                retObj=new ReturnObject();
            }
        } catch (Exception e){
            logger.error("发生了严重的服务器内部错误："+e.getMessage());
        }
        return retObj;

    }


}
