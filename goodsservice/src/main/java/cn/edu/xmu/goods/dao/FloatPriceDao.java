package cn.edu.xmu.goods.dao;

import cn.edu.xmu.goods.controller.FloatPriceController;
import cn.edu.xmu.goods.mapper.FloatPricePoMapper;
import cn.edu.xmu.goods.model.bo.FloatPrice;
import cn.edu.xmu.goods.model.bo.GoodsCategory;
import cn.edu.xmu.goods.model.po.FloatPricePo;
import cn.edu.xmu.goods.model.po.FloatPricePoExample;
import cn.edu.xmu.goods.model.po.GoodsCategoryPo;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author：谢沛辰
 * @Date: 2020.12.01
 * @Description:价格浮动dao
 */
@Repository
public class FloatPriceDao {

    private static final Logger logger = LoggerFactory.getLogger(FloatPriceDao.class);

    @Autowired
    FloatPricePoMapper floatPricePoMapper;

    public ReturnObject<Object> logicallyDeleteFloatPrice(FloatPrice floatPrice){
        ReturnObject<Object> retObj=null;
        FloatPricePo floatPricePo=floatPrice.createPo();
        try{
            int ret=floatPricePoMapper.updateByPrimaryKey(floatPricePo);
            if(ret==0){
                //修改失败
                logger.debug("deleteFloatprice: update FloatPrice fail : " + floatPricePo.toString());
                retObj = new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("id不存在：" + floatPricePo.getId()));
            }else{
                //修改成功
                logger.debug("deleteFloatprice: update FloatPrice = " + floatPricePo.toString());
                retObj = new ReturnObject<>();
            }
        }catch(DataAccessException e){
            logger.debug("other sql exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        }catch(Exception e){
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
        return retObj;
    }

    public ReturnObject<FloatPrice> insertFloatPrice(FloatPrice floatPrice){
        FloatPricePo floatPricePo=floatPrice.createPo();
        ReturnObject<FloatPrice> retObj=null;
        try{
            int ret=floatPricePoMapper.insert(floatPricePo);
            if(ret==0){
                logger.debug("insertFloatprice: insert floatprice fail "+floatPrice.toString());
                retObj=new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            }else{
                logger.debug("insertFloatprice: insert floatprice ="+floatPrice.toString());
                floatPrice.setId(floatPricePo.getId());
                retObj=new ReturnObject<>(floatPrice);
            }
        }catch(DataAccessException e){
            // 其他数据库错误
            logger.debug("other sql exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        }catch(Exception e){
            logger.error("other exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
        return retObj;
    }

    public ReturnObject<FloatPrice> getFloatPriceById(Long id){
        FloatPricePo floatPricePo= null;
        List<FloatPricePo> floatPricePos=null;
        try {
            floatPricePo=floatPricePoMapper.selectByPrimaryKey(id);
        } catch (DataAccessException e) {
            StringBuilder message = new StringBuilder().append("getUserByName: ").append(e.getMessage());
            logger.error(message.toString());
        }
        if(floatPricePo==null)
        {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        FloatPrice floatPrice=new FloatPrice(floatPricePo);
        return new ReturnObject<>(floatPrice);
    }

    public ReturnObject<List> getFloatPriceBySkuId(long skuId){
        List<FloatPricePo> floatPricePos=null;
        FloatPricePoExample example=new FloatPricePoExample();
        FloatPricePoExample.Criteria criteria=example.createCriteria();
        criteria.andGoodsSkuIdEqualTo(skuId);
        try{
            floatPricePos=floatPricePoMapper.selectByExample(example);
        }catch(DataAccessException e){
            StringBuilder message = new StringBuilder().append("getFloatPriceBySkuId: ").append(e.getMessage());
            logger.error(message.toString());
        }
        if(floatPricePos==null || floatPricePos.isEmpty()){
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        return new ReturnObject<>(floatPricePos);
    }
}
