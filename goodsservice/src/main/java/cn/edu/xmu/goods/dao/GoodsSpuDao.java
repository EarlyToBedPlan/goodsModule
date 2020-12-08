package cn.edu.xmu.goods.dao;

import cn.edu.xmu.goods.mapper.BrandPoMapper;
import cn.edu.xmu.goods.mapper.GoodsSkuPoMapper;
import cn.edu.xmu.goods.mapper.GoodsSpuPoMapper;
import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.bo.GoodsSpu;
import cn.edu.xmu.goods.model.bo.Shop;
import cn.edu.xmu.goods.model.po.*;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;
/**
 * SPU Dao
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/1 19:55
 * modifiedBy Yancheng Lai 19:55
 **/

@Repository
public class GoodsSpuDao {

    private static final Logger logger = LoggerFactory.getLogger(GoodsSpuDao.class);

    @Autowired
    private GoodsSkuPoMapper goodsSkuPoMapper;

    @Autowired
    private GoodsSpuPoMapper goodsSpuPoMapper;

    @Autowired
    private BrandPoMapper brandPoMapper;


    /**
    * @Description:  查询GoodsSpuPo以id
    * @Param: [id]
    * @return: GoodsSpu
    * @Author: Yancheng Lai
    * @Date: 2020/12/1 22:18
    */
    public ReturnObject<GoodsSpuPo> getGoodsSpuPoById(Long id) {
        //以后改成selectbyprimarykey

        GoodsSpuPoExample example = new GoodsSpuPoExample();
        GoodsSpuPoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<GoodsSpuPo> goodsSpuPos = null;
        try {
            goodsSpuPos = goodsSpuPoMapper.selectByExample(example);
        } catch (DataAccessException e) {
            StringBuilder message = new StringBuilder().append("getGoodsSpuPoById: ").append(e.getMessage());
            logger.error(message.toString());
        }
        if (null == goodsSpuPos || goodsSpuPos.isEmpty()) {
            return new ReturnObject<>();
        } else {
                return new ReturnObject<>(goodsSpuPos.get(0));
            }
        }
    /**
    * @Description: 新增SPU
    * @Param: [goodsSpu]
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject>
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 19:50
    */
    public ReturnObject<VoObject> insertGoodsSpu(GoodsSpu goodsSpu) {
        GoodsSpuPo goodsSpuPo = goodsSpu.getGoodsSpuPo();
        ReturnObject<VoObject> retObj = null;
        try{
            int ret = goodsSpuPoMapper.insertSelective(goodsSpuPo);
            if (ret == 0) {
                retObj = new ReturnObject<VoObject>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("Insert false：" + goodsSpuPo.getName()));
            } else {
                retObj = new ReturnObject<VoObject>(goodsSpu);
            }
        }
        catch (DataAccessException e) {
            retObj = new ReturnObject<VoObject>(ResponseCode.INTERNAL_SERVER_ERR, String.format("Database Eoor: %s", e.getMessage()));
        }
        return retObj;
    }
    /**
    * @Description: 用skuid查询SPU
    * @Param: [id]
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.goods.model.po.GoodsSpuPo>
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 19:50
    */
    public ReturnObject<GoodsSpuPo> getGoodsSpuPoBySkuId(Long id) {

        GoodsSpuPoExample example = new GoodsSpuPoExample();
        GoodsSpuPoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<GoodsSpuPo> goodsSpuPos = null;
        try {
            goodsSpuPos = goodsSpuPoMapper.selectByExample(example);
        } catch (DataAccessException e) {
            StringBuilder message = new StringBuilder().append("getGoodsSpuPoById: ").append(e.getMessage());
            logger.error(message.toString());
        }
        if (null == goodsSpuPos || goodsSpuPos.isEmpty()) {
            return new ReturnObject<>();
        } else {
            return new ReturnObject<>(goodsSpuPos.get(0));
        }
    }
    /**
    * @Description: 查询SPUId是否在商店内
    * @Param: [shopId, spuId]
    * @return: boolean
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 19:55
    */
    public boolean checkSpuId(Long shopId, Long spuId) {
        GoodsSpuPo goodsSpuPo = goodsSpuPoMapper.selectByPrimaryKey(spuId);
        if (goodsSpuPo == null) {
            return false;
        }
        if (goodsSpuPo.getShopId() != shopId){
            return false;
        }
        return true;
    }

    public ReturnObject<VoObject> updateSpu(GoodsSpu goodsSpu){
        GoodsSpuPo po = goodsSpuPoMapper.selectByPrimaryKey(goodsSpu.getId());

        goodsSpuPoMapper.updateByPrimaryKeySelective(goodsSpu.getGoodsSpuPo());
        return new ReturnObject<VoObject>();
    }


    public ReturnObject<VoObject> revokeSpu( Long id){
        GoodsSpuPoExample goodsSpuPoExample =  new GoodsSpuPoExample();
        GoodsSpuPoExample.Criteria criteria = goodsSpuPoExample.createCriteria();
        criteria.andIdEqualTo(id);
        GoodsSpuPo goodsSpuPo = goodsSpuPoMapper.selectByPrimaryKey(id);
        //shopid或spuid不存在
        if ( goodsSpuPo == null) {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }

        try {
            //int state = goodsSkuPoMapper.deleteByPrimaryKey(id);
            goodsSpuPo.setDisabled((byte)0);
            int state = goodsSpuPoMapper.updateByPrimaryKeySelective(goodsSpuPo);
            if (state == 0){
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            }

        } catch (DataAccessException e) {
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("Database Exception：%s", e.getMessage()));
        } catch (Exception e) {
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("Unknown Exception：%s", e.getMessage()));
        }
        return new ReturnObject<>();
    }



    public ReturnObject<VoObject>insertGoodsBrand(Long spuId,Long id)
    {
        GoodsSpuPo goodsSpuPo = goodsSpuPoMapper.selectByPrimaryKey(spuId);
            BrandPo brandPo = brandPoMapper.selectByPrimaryKey(id);
            if(brandPo == null || goodsSpuPo == null){
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            }
            else goodsSpuPo.setBrandId(id);
            goodsSpuPoMapper.updateByPrimaryKeySelective(goodsSpuPo);
            return new ReturnObject<>();

    }

    public ReturnObject<VoObject>deleteGoodsBrand(Long spuId,Long id)
    {
        GoodsSpuPo goodsSpuPo = goodsSpuPoMapper.selectByPrimaryKey(spuId);
            BrandPo brandPo = brandPoMapper.selectByPrimaryKey(id);
            if(brandPo == null || goodsSpuPo == null || goodsSpuPo.getBrandId() != id){
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            }
            else goodsSpuPo.setBrandId(0l);
            goodsSpuPoMapper.updateByPrimaryKeySelective(goodsSpuPo);
            return new ReturnObject<>();

    }
}
