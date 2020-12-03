package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.dao.GoodsSpuDao;
import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.bo.GoodsSpu;
import cn.edu.xmu.goods.model.po.GoodsSpuPo;
import cn.edu.xmu.goods.model.po.GoodsSpuPoExample;
import cn.edu.xmu.goods.model.vo.GoodsSpuRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * SPU接口的实现类
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 19:03
 * modifiedBy Yancheng Lai 19:03
 **/
@Service
public class GoodsSpuServiceImpl implements GoodsSpuService{
    private Logger logger = LoggerFactory.getLogger(GoodsSpuService.class);

    @Autowired
    GoodsSpuDao goodsSpuDao;

    /**
    * @Description: 查询 Spu 以 Id
    * @Param: [id]
    * @return: ReturnObject<VoObject> Spu
    * @Author: Yancheng Lai
    * @Date: 2020/12/2 19:07
    */
    @Override
    public ReturnObject<GoodsSpuRetVo> findSpuById(Long id) {
        ReturnObject<GoodsSpuRetVo> returnObject = null;

        GoodsSpuPo goodsSpuPo = goodsSpuDao.getGoodsSpuPoById(id).getData();
        if(goodsSpuPo != null) {
            logger.debug("findSpuById : " + returnObject);
            returnObject = new ReturnObject<> (new GoodsSpu(goodsSpuPo).createVo());
        } else {
            logger.debug("findSpuById: Not Found");
            returnObject = new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }

        return returnObject;

    }

    /**
    * @Description: 插入SPU
    * @Param: [goodsSpu, id]
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject>
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 19:48
    */
    @Override
    public ReturnObject<VoObject> insertGoodsSpu(GoodsSpu goodsSpu, Long id) {

        ReturnObject<VoObject> retObj = null;

        goodsSpu.setShopId(id);
        retObj = goodsSpuDao.insertGoodsSpu(goodsSpu);

        return retObj;
    }

    /**
    * @Description: 更新Spu
    * @Param: [vo, shopId, id]
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject>
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 20:36
    */

    @Transactional
    public ReturnObject<VoObject> updateSpu(GoodsSpu vo,Long shopId,Long id){
        if (goodsSpuDao.checkSpuId(shopId,id)) {
            vo.setShopId(shopId);
            vo.setId(id);
            return goodsSpuDao.updateSpu(vo);
        } else {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
    }

    @Transactional
    public ReturnObject<List<GoodsSpu.State>>findSpuStates(){
        List<GoodsSpu.State> lst = null;

        for (GoodsSpu.State e : GoodsSpu.State.values()) {
            lst.add(e);
        }

        return new ReturnObject<>( (lst));
    }

    @Transactional
    public ReturnObject<VoObject> revokeSpu( Long shopId, Long id){
        if (goodsSpuDao.checkSpuId(shopId,id)) {
            return goodsSpuDao.revokeSpu(shopId, id);
        } else {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }

    }

    @Transactional
    public ReturnObject<VoObject> updateSpuOnShelves(Long shopId,Long id){
        if (goodsSpuDao.checkSpuId(shopId,id)) {
            return goodsSpuDao.updateSpuOnShelves(id);
        } else {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
    }

    @Transactional
    public ReturnObject<VoObject> updateSpuOffShelves(Long shopId,Long id){
        if (goodsSpuDao.checkSpuId(shopId,id)) {
            return goodsSpuDao.updateSpuOnShelves(id);
        } else {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
    }
}
