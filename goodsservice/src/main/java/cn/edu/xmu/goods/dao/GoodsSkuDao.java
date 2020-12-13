package cn.edu.xmu.goods.dao;

import cn.edu.xmu.goods.mapper.GoodsSkuPoMapper;
import cn.edu.xmu.goods.mapper.GoodsSpuPoMapper;
import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.po.GoodsSkuPo;
import cn.edu.xmu.goods.model.po.GoodsSkuPoExample;
import cn.edu.xmu.goods.model.po.GoodsSpuPo;
import cn.edu.xmu.goods.model.po.GoodsSpuPoExample;
import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSkuSimpleRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSpuRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 21:58
 * modifiedBy Yancheng Lai 21:58
 **/
@Repository
public class GoodsSkuDao {

    @Autowired
    private GoodsSpuPoMapper goodsSpuPoMapper;
    @Autowired
    private GoodsSkuPoMapper goodsSkuPoMapper;

    public static final Logger logger = LoggerFactory.getLogger(GoodsSkuDao.class);

    /**
    * @Description: 分页获取Sku信息
    * @Param: [shopId, skuSn, spuId, spuSn, page, pagesize]
    * @return: com.github.pagehelper.PageInfo<cn.edu.xmu.goods.model.vo.GoodsSkuRetVo> 返回Vo
    * @Author: Yancheng Lai
    * @Date: 2020/12/2 23:18
    */
    public PageInfo<GoodsSkuRetVo> findAllGoodSkus(Long shopId, String skuSn, Long spuId, String spuSn, Integer page, Integer pagesize) {
        GoodsSpuPoExample spuPoExample = new GoodsSpuPoExample();
        GoodsSpuPoExample.Criteria criteria = spuPoExample.createCriteria();
        GoodsSkuPoExample skuPoExample = new GoodsSkuPoExample();
        GoodsSkuPoExample.Criteria ccriteria = skuPoExample.createCriteria();
        if(shopId != null)
            criteria.andShopIdEqualTo(shopId);
        if(spuId != null)
            criteria.andIdEqualTo(spuId);
        if(spuSn != null)
            criteria.andGoodsSnEqualTo(spuSn);
        List<GoodsSpuPo> goodsSpuPos = goodsSpuPoMapper.selectByExample(spuPoExample);
        GoodsSkuPo goodsSkuPo = null;
        List<GoodsSkuPo> goodsSkuPos = null;
        List<GoodsSkuRetVo> goodsSkuRetVos = null;
        if(goodsSpuPos.size() > 0) {
            for (GoodsSpuPo goodsSpuPo : goodsSpuPos) {
                if(skuSn != null) {
                    ccriteria.andSkuSnEqualTo(skuSn);
                }
                ccriteria.andGoodsSpuIdEqualTo(goodsSpuPo.getId());
                goodsSkuPos = goodsSkuPoMapper.selectByExample(skuPoExample);

            }
        } else if(goodsSpuPos.size() == 0){
            goodsSkuPos = null;
        } else {
            if(skuSn != null) {
                ccriteria.andSkuSnEqualTo(skuSn);
            }
            goodsSkuPos = goodsSkuPoMapper.selectByExample(skuPoExample);
        }

        for(GoodsSkuPo goodsSkuRetPo: goodsSkuPos){
            GoodsSkuRetVo goodsSkuRetVo = new GoodsSku(goodsSkuPo).createVo();
            GoodsSpuRetVo goodsSpuRetVo = new GoodsSpuRetVo();
            goodsSkuRetVos.add(new GoodsSku(goodsSkuPo).createVo());
        }

        return new PageInfo<>(goodsSkuRetVos);
    }
    /** 
    * @Description: 查询该sku是否位于shop内
    * @Param: [skuId, id] 
    * @return: boolean 
    * @Author: Yancheng Lai
    * @Date: 2020/12/2 23:36
    */

    public boolean checkSkuIdInShop(Long shopId, Long skuId) {
        GoodsSkuPo goodsSkuPo = goodsSkuPoMapper.selectByPrimaryKey(skuId);
        if (goodsSkuPo == null) {
            return false;
        }
        Long spuId = goodsSkuPo.getGoodsSpuId();
        GoodsSpuPo goodsSpuPo = goodsSpuPoMapper.selectByPrimaryKey(spuId);
        if(goodsSpuPo.getShopId() != shopId){
            return false;
        }
        return true;
    }
    /**
    * @Description: 确认是否被删除
    * @Param: [skuId]
    * @return: boolean
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 20:59
    */
    public boolean checkSkuDisabled(Long  skuId){
        GoodsSkuPo goodsSkuPo = goodsSkuPoMapper.selectByPrimaryKey(skuId);
        if(goodsSkuPo.getDisabled() == 0 ){
            return false;
        }
        return true;
    }


    /** 
    * @Description: 逻辑删除 
    * @Param: [shopId, id] 
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 0:06
    */
    public ReturnObject<VoObject> revokeSku(Long shopId,Long id){
        GoodsSkuPoExample goodsSkuPoExample =  new GoodsSkuPoExample();
        GoodsSkuPoExample.Criteria criteria = goodsSkuPoExample.createCriteria();
        criteria.andIdEqualTo(id);
        GoodsSkuPo goodsSkuPo = goodsSkuPoMapper.selectByPrimaryKey(id);
        //shopid或skuid不存在
        if ( goodsSkuPo == null) {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        if(checkSkuIdInShop(shopId,id)==false){
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE);
        }
        try {
            goodsSkuPo.setState((byte)GoodsSku.State.DELETED.getCode());
            goodsSkuPo.setDisabled((byte)1);
            goodsSkuPo.setGmtModified(LocalDateTime.now());
            int state = goodsSkuPoMapper.updateByPrimaryKeySelective(goodsSkuPo);
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

        return new ReturnObject<>(ResponseCode.OK,"删除成功");
    }

    /** 
    * @Description: 插入GoodsSku
    * @Param: [goodsSku] 
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 17:09
    */

    public ReturnObject<GoodsSkuSimpleRetVo> insertGoodsSku(GoodsSku goodsSku) {
        logger.info("Dao");
        GoodsSkuPo goodsSkuPo = goodsSku.getPo();
        ReturnObject<GoodsSkuSimpleRetVo> retObj = null;
        try{
            goodsSkuPo.setGmtModified(LocalDateTime.now());
            goodsSkuPo.setGmtCreate(LocalDateTime.now());
            goodsSkuPo.setState((byte)GoodsSku.State.WAITING.getCode());
            int ret = goodsSkuPoMapper.insert(goodsSkuPo);
            if (ret == 0) {
                retObj = new ReturnObject<GoodsSkuSimpleRetVo>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("Insert false：" + goodsSkuPo.getName()));
            } else {
                goodsSku.setId(goodsSkuPo.getId());
                retObj = new ReturnObject<GoodsSkuSimpleRetVo>(goodsSku.createSimpleVo());
            }
        }
        catch (DataAccessException e) {
                retObj = new ReturnObject<GoodsSkuSimpleRetVo>(ResponseCode.INTERNAL_SERVER_ERR, String.format("Database Error: %s", e.getMessage()));
        }
        return retObj;
    }

    /** 
    * @Description: 更新SKU 
    * @Param: [goodsSku, shopId, id] 
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 17:27
    */

    public ReturnObject updateSku(GoodsSku goodsSku, Long shopId,Long id){
        GoodsSkuPo newPo = goodsSku.getPo();
        GoodsSkuPo oldPo = goodsSkuPoMapper.selectByPrimaryKey(id);
        if(oldPo == null){
            return new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        if(checkSkuIdInShop(shopId,id)==false){
            return new ReturnObject(ResponseCode.RESOURCE_ID_OUTSCOPE);
        }
        newPo.setId(id);
        newPo.setId(id);
        newPo.setGmtModified(LocalDateTime.now());

        int upd = goodsSkuPoMapper.updateByPrimaryKeySelective(goodsSku.getGoodsSkuPo());
        if(upd == 0){
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        return new ReturnObject<>(ResponseCode.OK);
    }

    /** 
    * @Description: 用id得到goodsSku 
    * @Param: [id] 
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/6 16:35
    */

    public ReturnObject<GoodsSkuPo> getSkuById(Long id){
        logger.info("dao: get Sku by id: "+ id);
        GoodsSkuPo goodsSkuPo = null;
        try {
            goodsSkuPo = goodsSkuPoMapper.selectByPrimaryKey(id);
        } catch (DataAccessException e) {
            //database error
            return new ReturnObject(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("Database Exception: %s", e.getMessage()));
        } catch (Exception e) {
            return new ReturnObject(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("Unknown Exception: %s", e.getMessage()));
        }
        if(goodsSkuPo == null){
            logger.info("po = null "+ id);
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        logger.info("po != null "+ goodsSkuPo.getId());
        return new  ReturnObject<> (goodsSkuPo);
    }

    /** 
    * @Description: SPUID查SKU 
    * @Param: [id] 
    * @return: java.util.List<GoodsSkuPo> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 21:01
    */
    public List<GoodsSkuPo> getSkuBySpuId(Long id){
        logger.debug("dao: get Sku by Spu id: "+ id);
        GoodsSkuPoExample example = new GoodsSkuPoExample();
        GoodsSkuPoExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsSpuIdEqualTo(id);

        List<GoodsSkuPo> goodsSkuPos = goodsSkuPoMapper.selectByExample(example);
        return goodsSkuPos;
    }

    /** 
    * @Description: SKU上架 
    * @Param: [shopId, id] 
    * @return: ReturnObject<VoObject> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 21:01
    */
    public ReturnObject<VoObject> updateSkuOnShelves(Long shopId,Long id){
        GoodsSkuPo po = goodsSkuPoMapper.selectByPrimaryKey(id);
        if(po == null){
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        if(checkSkuIdInShop(shopId,id)==false){
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE);
        }
        po.setGmtModified(LocalDateTime.now());
        po.setState((byte)GoodsSku.State.INVALID.getCode());
        //po.setDisabled((byte)0);
        goodsSkuPoMapper.updateByPrimaryKeySelective(po);
        return new ReturnObject<VoObject>();
    }

    /** 
    * @Description: SKU下架 
    * @Param: [shopId, id] 
    * @return: ReturnObject<VoObject> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 21:01
    */
    public ReturnObject<VoObject> updateSkuOffShelves(Long shopId,Long id){
        GoodsSkuPo po = goodsSkuPoMapper.selectByPrimaryKey(id);
        if(po == null){
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        if(checkSkuIdInShop(shopId,id)==false){
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE);
        }
        po.setState((byte)GoodsSku.State.WAITING.getCode());
        //po.setDisabled((byte)1);
        po.setGmtModified(LocalDateTime.now());
        goodsSkuPoMapper.updateByPrimaryKeySelective(po);
        return new ReturnObject<VoObject>();
    }

    /**
    * @Description: SKU将SPU设置为null
    * @Param: [skuId]
    * @return: ReturnObject<VoObject>
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 21:02
    */
    public ReturnObject<VoObject> setSpuNull(Long skuId){
        GoodsSkuPoExample example = new GoodsSkuPoExample();
        GoodsSkuPoExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsSpuIdEqualTo(skuId);

        List<GoodsSkuPo> goodsSkuPos = goodsSkuPoMapper.selectByExample(example);
        for (GoodsSkuPo goodsSkuPo : goodsSkuPos){
            goodsSkuPo.setGoodsSpuId(0l);
            goodsSkuPoMapper.updateByPrimaryKeySelective(goodsSkuPo);
        }
        return new ReturnObject<>(ResponseCode.OK);
    }
    /**
    * @Description:  删除订单库存
    * @Param: [skuId, quantity]
    * @return: ReturnObject
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 21:02
    */
    public ReturnObject deductStock(Long skuId,Integer quantity) {
        GoodsSkuPo goodsSkuPo = goodsSkuPoMapper.selectByPrimaryKey(skuId);
        logger.info("ddfor "+skuId+" quan = "+quantity);
        if (goodsSkuPo == null) {
            //not found
            return new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
        } else {
            int inventory = goodsSkuPo.getInventory();
            try{
                goodsSkuPo.setInventory(inventory - quantity);
                goodsSkuPoMapper.updateByPrimaryKeySelective(goodsSkuPo);
            }catch (DataAccessException e) {
                //database error
                return new ReturnObject(ResponseCode.INTERNAL_SERVER_ERR,
                        String.format("Database Exception: %s", e.getMessage()));
            } catch (Exception e) {
                return new ReturnObject(ResponseCode.INTERNAL_SERVER_ERR,
                        String.format("Unknown Exception: %s", e.getMessage()));
            }
        return new ReturnObject<>(ResponseCode.OK);
    }}
    /**
    * @Description: 确认订单库存是否足够
    * @Param: [skuId, quantity]
    * @return: ReturnObject<Boolean>
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 21:04
    */
    public ReturnObject<Boolean> checkStock(Long skuId,Integer quantity) {
        GoodsSkuPo goodsSkuPo = goodsSkuPoMapper.selectByPrimaryKey(skuId);
        logger.info("checkfor "+skuId+" quan = "+quantity+"inv = "+goodsSkuPo.getInventory());
        if (goodsSkuPo == null) {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        } else {
            int inventory = goodsSkuPo.getInventory();
            if (inventory < quantity) {
                //not enough
                return new ReturnObject(ResponseCode.SKU_NOTENOUGH);
            } else {

            }
        }
        return new ReturnObject<>(true);
    }

    /**
     * @Description: shop查sku
     * @Param: [shopId]
     * @return: java.util.List<java.lang.Long>
     * @Author: Yancheng Lai
     * @Date: 2020/12/11 21:03
     */
    public List<GoodsSku> getSkuListByShopId(Long shopId){
        GoodsSpuPoExample example = new GoodsSpuPoExample();
        GoodsSpuPoExample.Criteria criteria = example.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        criteria.andDisabledEqualTo((byte)0);
        List<GoodsSpuPo> goodsSpuPos = goodsSpuPoMapper.selectByExample(example);

        List<GoodsSku> goodsSkus = new ArrayList<>();
        for(GoodsSpuPo goodsSpuPo:goodsSpuPos){
            GoodsSkuPoExample goodsSkuPoExample = new GoodsSkuPoExample();
            GoodsSkuPoExample.Criteria criteria1 = goodsSkuPoExample.createCriteria();
            criteria1.andGoodsSpuIdEqualTo(goodsSpuPo.getId());
            List<GoodsSkuPo> goodsSkuPos = goodsSkuPoMapper.selectByExample(goodsSkuPoExample);
            for(GoodsSkuPo goodsSkuPo : goodsSkuPos ){
                if(goodsSkuPo.getDisabled()==0 && goodsSkuPo.getState()==0)
                goodsSkus.add(new GoodsSku(goodsSkuPo));
            }

        }
        return goodsSkus;
    }

    public List<Long> getSkuIdListByShopId(Long shopId){
        GoodsSpuPoExample example = new GoodsSpuPoExample();
        GoodsSpuPoExample.Criteria criteria = example.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        criteria.andDisabledEqualTo((byte)0);
        List<GoodsSpuPo> goodsSpuPos = goodsSpuPoMapper.selectByExample(example);
        List<Long> ret = new ArrayList<>();
        List<GoodsSku> goodsSkus = new ArrayList<>();
        for(GoodsSpuPo goodsSpuPo:goodsSpuPos){
            GoodsSkuPoExample goodsSkuPoExample = new GoodsSkuPoExample();
            GoodsSkuPoExample.Criteria criteria1 = goodsSkuPoExample.createCriteria();
            criteria1.andGoodsSpuIdEqualTo(goodsSpuPo.getId());
            List<GoodsSkuPo> goodsSkuPos = goodsSkuPoMapper.selectByExample(goodsSkuPoExample);
            for(GoodsSkuPo goodsSkuPo : goodsSkuPos ){
                if(goodsSkuPo.getDisabled()==0 && goodsSkuPo.getState()==0)
                    ret.add(goodsSkuPo.getId());
            }

        }
        return ret;
    }
    /**
    * @Description: spu查sku并且逻辑删除
    * @Param: [shopId]
    * @return: java.util.List<java.lang.Long>
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 21:03
    */
    @Transactional
    public ReturnObject setSkuDisabledBySpuId(Long spuId){
            GoodsSkuPoExample goodsSkuPoExample = new GoodsSkuPoExample();
            GoodsSkuPoExample.Criteria criteria1 = goodsSkuPoExample.createCriteria();
            criteria1.andGoodsSpuIdEqualTo(spuId);
            List<GoodsSkuPo> goodsSkuPos = goodsSkuPoMapper.selectByExample(goodsSkuPoExample);
            for(GoodsSkuPo goodsSkuPo : goodsSkuPos ) {
                goodsSkuPo.setDisabled((byte) 1);
                goodsSkuPoMapper.updateByPrimaryKeySelective(goodsSkuPo);
            }
        return new ReturnObject<>();
    }
    /**
    * @Description: 店里面没被删掉的上架
    * @Param: [shopId]
    * @return: cn.edu.xmu.ooad.util.ReturnObject
    * @Author: Yancheng Lai
    * @Date: 2020/12/12 22:30
    */

    public ReturnObject setAllSkuOnShelvesByShopId(Long shopId){
        GoodsSpuPoExample example = new GoodsSpuPoExample();
        GoodsSpuPoExample.Criteria criteria = example.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        criteria.andDisabledEqualTo((byte)0);
        List<GoodsSpuPo> goodsSpuPos = goodsSpuPoMapper.selectByExample(example);
        for(GoodsSpuPo goodsSpuPo:goodsSpuPos){
            GoodsSkuPoExample goodsSkuPoExample = new GoodsSkuPoExample();
            GoodsSkuPoExample.Criteria criteria1 = goodsSkuPoExample.createCriteria();
            criteria1.andGoodsSpuIdEqualTo(goodsSpuPo.getId());
            List<GoodsSkuPo> goodsSkuPos = goodsSkuPoMapper.selectByExample(goodsSkuPoExample);
            for(GoodsSkuPo goodsSkuPo : goodsSkuPos ){
                if(goodsSkuPo.getDisabled()==0){
                    goodsSkuPo.setState((byte)GoodsSku.State.INVALID.getCode());
                    goodsSkuPoMapper.updateByPrimaryKeySelective(goodsSkuPo);
                }
            }

        }
        return new ReturnObject<>();
    }
    /**
    * @Description: 店里面没被删掉的下架
    * @Param: [shopId]
    * @return: cn.edu.xmu.ooad.util.ReturnObject
    * @Author: Yancheng Lai
    * @Date: 2020/12/12 22:32
    */
    public ReturnObject setAllSkuOffShelvesByShopId(Long shopId){
        GoodsSpuPoExample example = new GoodsSpuPoExample();
        GoodsSpuPoExample.Criteria criteria = example.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        criteria.andDisabledEqualTo((byte)0);
        List<GoodsSpuPo> goodsSpuPos = goodsSpuPoMapper.selectByExample(example);
        for(GoodsSpuPo goodsSpuPo:goodsSpuPos){
            GoodsSkuPoExample goodsSkuPoExample = new GoodsSkuPoExample();
            GoodsSkuPoExample.Criteria criteria1 = goodsSkuPoExample.createCriteria();
            criteria1.andGoodsSpuIdEqualTo(goodsSpuPo.getId());
            List<GoodsSkuPo> goodsSkuPos = goodsSkuPoMapper.selectByExample(goodsSkuPoExample);
            for(GoodsSkuPo goodsSkuPo : goodsSkuPos ){
                if(goodsSkuPo.getDisabled()==0){
                    goodsSkuPo.setState((byte)GoodsSku.State.WAITING.getCode());
                    goodsSkuPoMapper.updateByPrimaryKeySelective(goodsSkuPo);
                }
            }

        }
        return new ReturnObject<>();
    }



}
