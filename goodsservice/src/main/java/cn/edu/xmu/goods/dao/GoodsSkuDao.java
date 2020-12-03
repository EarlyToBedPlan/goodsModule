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
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;
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
    public ReturnObject<VoObject> revokeSku(Long shopId, Long id){
        GoodsSkuPoExample goodsSkuPoExample =  new GoodsSkuPoExample();
        GoodsSkuPoExample.Criteria criteria = goodsSkuPoExample.createCriteria();
        criteria.andIdEqualTo(id);
        Shop shop = (Shop)getShopById(shopId).getData();
        GoodsSkuPo goodsSkuPo = goodsSkuPoMapper.selectByPrimaryKey(id);

        //shopid或skuid不存在
        if (shop == null|| goodsSkuPo == null) {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }

        try {
            int state = goodsSkuPoMapper.deleteByPrimaryKey(id);
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

    /** 
    * @Description: 插入GoodsSku
    * @Param: [goodsSku] 
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 17:09
    */

    public ReturnObject<VoObject> insertGoodsSku(GoodsSku goodsSku) {
        GoodsSkuPo goodsSkuPo = goodsSku.getPo();
        ReturnObject<VoObject> retObj = null;
        try{
            int ret = goodsSkuPoMapper.insertSelective(goodsSkuPo);
            if (ret == 0) {
                retObj = new ReturnObject<VoObject>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("Insert false：" + goodsSkuPo.getName()));
            } else {
                goodsSku.setId(goodsSkuPo.getId());
                retObj = new ReturnObject<VoObject>(goodsSku);
            }
        }
        catch (DataAccessException e) {
                retObj = new ReturnObject<VoObject>(ResponseCode.INTERNAL_SERVER_ERR, String.format("Database Eoor: %s", e.getMessage()));
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

}
