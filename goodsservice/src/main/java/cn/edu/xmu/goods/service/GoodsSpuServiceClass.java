package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.dao.GoodsSpuDao;
import cn.edu.xmu.goods.model.bo.GoodsSpu;
import cn.edu.xmu.goods.model.po.GoodsSpuPo;
import cn.edu.xmu.goods.model.vo.GoodsSpuRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SPU接口的实现类
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 19:03
 * modifiedBy Yancheng Lai 19:03
 **/
@Service
public class GoodsSpuServiceClass implements GoodsSpuService{
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
}
