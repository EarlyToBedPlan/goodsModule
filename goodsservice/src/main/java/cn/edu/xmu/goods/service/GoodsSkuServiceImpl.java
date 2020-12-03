package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.dao.GoodsSkuDao;
import cn.edu.xmu.goods.dao.GoodsSpuDao;
import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.bo.GoodsSpu;
import cn.edu.xmu.goods.model.po.GoodsSkuPo;
import cn.edu.xmu.goods.model.po.GoodsSpuPo;
import cn.edu.xmu.goods.model.po.GoodsSpuPoExample;
import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SKU Service接口
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 19:54
 * modifiedBy Yancheng Lai 19:54
 **/
@Service
public class GoodsSkuServiceImpl implements GoodsSkuService {

    @Autowired
    GoodsSkuDao goodsSkuDao;

    @Autowired
    GoodsSpuDao goodsSpuDao;

    /** 
    * @Description: 分页获取sku信息 
    * @Param: [shopId, skuSn, spuId, spuSn, page, pagesize] 
    * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo<cn.edu.xmu.ooad.model.VoObject>> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/2 22:06
    */
    @Override
    public ReturnObject<PageInfo<GoodsSkuRetVo>> findAllSkus(Long shopId, String skuSn, Long spuId, String spuSn, Integer page, Integer pagesize) {

        PageHelper.startPage(page, pagesize,true,true,null);

        PageInfo<GoodsSkuRetVo> returnObject = goodsSkuDao.findAllGoodSkus(shopId, skuSn, spuId, spuSn, page, pagesize);

        return new ReturnObject<>(returnObject);
    }
    /** 
    * @Description: 逻辑删除Sku，先检查，后删除
    * @Param: [shopId, id] 
    * @return: java.lang.Object 
    * @Author: Yancheng Lai
    * @Date: 2020/12/2 23:52
    */
    @Transactional
    public ReturnObject<VoObject> revokeSku( Long shopId, Long id){
        if (goodsSkuDao.checkSkuId(shopId,id)) {
            return goodsSkuDao.revokeSku(shopId, id);
        } else {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }

    }

    /**
    * @Description: 新增GoodsSku
    * @Param: [goodsSku]
    * @return: cn.edu.xmu.ooad.util.ReturnObject
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 15:27
    */

    @Transactional
    public ReturnObject<VoObject> insertGoodsSku(GoodsSku goodsSku, Long shopId, Long id) {

        ReturnObject<VoObject> retObj = null;

        //查询spu是否属于该商铺
        GoodsSpuPoExample goodsSpuPoExample = new GoodsSpuPoExample();
        GoodsSpuPoExample.Criteria criteria = goodsSpuPoExample.createCriteria();
        GoodsSpuPo goodsSpuPo = goodsSpuDao.getGoodsSpuPoById(id).getData();
        if(shopId != goodsSpuPo.getShopId()){
            retObj = new ReturnObject<VoObject>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("Insert false: Spu not in shop" + goodsSku.getName()));
        } else {
            goodsSku.setGoodsSpuId(id);

            retObj = goodsSkuDao.insertGoodsSku(goodsSku);
        }

        return retObj;
    }

    /**
    * @Description: 修改SKU
    * @Param: [vo, shopId, id]
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject>
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 19:53
    */

    @Transactional
    public ReturnObject<VoObject> updateSku(GoodsSku vo,Long shopId,Long id){
        if (goodsSkuDao.checkSkuId(shopId,id)) {
            return goodsSkuDao.updateSku(vo, shopId, id);
        } else {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
    }




}
