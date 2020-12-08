package cn.edu.xmu.goods.dao;

import cn.edu.xmu.goods.mapper.GoodsSkuPoMapper;
import cn.edu.xmu.goods.mapper.GoodsSpuPoMapper;
import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.bo.Shop;
import cn.edu.xmu.goods.model.po.GoodsSkuPo;
import cn.edu.xmu.goods.model.po.GoodsSkuPoExample;
import cn.edu.xmu.goods.model.po.GoodsSpuPo;
import cn.edu.xmu.goods.model.po.GoodsSpuPoExample;
import cn.edu.xmu.goods.model.vo.BrandRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSkuSimpleRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

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
        if(goodsSpuPos.size() == 1) {
            for (GoodsSpuPo goodsSpuPo : goodsSpuPos) {
                if(skuSn != null) {
                    ccriteria.andSkuSnEqualTo(skuSn);
                }
                ccriteria.andGoodsSpuIdEqualTo(goodsSpuPo.getId());
                goodsSkuPos = goodsSkuPoMapper.selectByExample(skuPoExample);

            }
        } else if(goodsSkuPos.size() == 0){
            goodsSkuPos = null;
        } else {
            if(skuSn != null) {
                ccriteria.andSkuSnEqualTo(skuSn);
            }
            goodsSkuPos = goodsSkuPoMapper.selectByExample(skuPoExample);
        }

        for(GoodsSkuPo goodsSkuRetPo: goodsSkuPos){
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

    public boolean checkSkuId(Long shopId, Long skuId) {
        GoodsSkuPo goodsSkuPo = goodsSkuPoMapper.selectByPrimaryKey(skuId);
        if (goodsSkuPo == null) {
            return false;
        }
        if (goodsSkuPo.getDisabled() != 0) {
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
    * @Description: 待完善 等底层完成
    * @Param: [id] 
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 0:05
    */
    public ReturnObject<VoObject> getShopById(Long id){
        Shop shop =new Shop();
        return new ReturnObject<>(shop);
    }

    /** 
    * @Description: 逻辑删除 
    * @Param: [shopId, id] 
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 0:06
    */
    public ReturnObject<VoObject> revokeSku(Long id){
        GoodsSkuPoExample goodsSkuPoExample =  new GoodsSkuPoExample();
        GoodsSkuPoExample.Criteria criteria = goodsSkuPoExample.createCriteria();
        criteria.andIdEqualTo(id);
        GoodsSkuPo goodsSkuPo = goodsSkuPoMapper.selectByPrimaryKey(id);

        //shopid或skuid不存在
        if ( goodsSkuPo == null) {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }

        try {
            goodsSkuPo.setState((byte)GoodsSku.State.DELETED.getCode());
            goodsSkuPo.setDisabled((byte)0);
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

    public ReturnObject<VoObject> updateSku(GoodsSku goodsSku,Long shopId, Long id){
        GoodsSkuPo po = goodsSkuPoMapper.selectByPrimaryKey(id);
        goodsSkuPoMapper.updateByPrimaryKeySelective(goodsSku.getGoodsSkuPo());
        return new ReturnObject<VoObject>();
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
        GoodsSkuPo goodsSkuPo = goodsSkuPoMapper.selectByPrimaryKey(id);
        if(goodsSkuPo == null){
            logger.info("po = null "+ id);
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        logger.info("po != null "+ id);
        return new  ReturnObject<> (goodsSkuPo);
    }

    public List<GoodsSkuPo> getSkuBySpuId(Long id){
        logger.debug("dao: get Sku by Spu id: "+ id);
        GoodsSkuPoExample example = new GoodsSkuPoExample();
        GoodsSkuPoExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsSpuIdEqualTo(id);

        List<GoodsSkuPo> goodsSkuPos = goodsSkuPoMapper.selectByExample(example);
        return goodsSkuPos;
    }

    public ReturnObject<VoObject> updateSkuOnShelves(Long id){
        GoodsSkuPo po = goodsSkuPoMapper.selectByPrimaryKey(id);
        if(po == null){
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        po.setState((byte)GoodsSku.State.INVALID.getCode());
        po.setDisabled((byte)0);
        goodsSkuPoMapper.updateByPrimaryKeySelective(po);
        return new ReturnObject<VoObject>();
    }

    public ReturnObject<VoObject> updateSkuOffShelves(Long id){
        GoodsSkuPo po = goodsSkuPoMapper.selectByPrimaryKey(id);
        if(po == null){
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        po.setState((byte)GoodsSku.State.WAITING.getCode());
        po.setDisabled((byte)1);
        goodsSkuPoMapper.updateByPrimaryKeySelective(po);
        return new ReturnObject<VoObject>();
    }

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
}
