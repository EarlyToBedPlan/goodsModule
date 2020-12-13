package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.dao.BrandDao;
import cn.edu.xmu.goods.dao.GoodsCategoryDao;
import cn.edu.xmu.goods.dao.GoodsSkuDao;
import cn.edu.xmu.goods.dao.GoodsSpuDao;
import cn.edu.xmu.goods.model.bo.*;
import cn.edu.xmu.goods.model.po.GoodsSkuPo;
import cn.edu.xmu.goods.model.po.GoodsSpuPo;
import cn.edu.xmu.goods.model.vo.*;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ImgHelper;
import cn.edu.xmu.ooad.util.JacksonUtil;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.shop.dao.ShopDao;
import cn.edu.xmu.shop.model.bo.Shop;
import cn.edu.xmu.shop.model.po.ShopPo;
import cn.edu.xmu.shop.model.vo.ShopSimpleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import cn.edu.xmu.shop.service.*;
import cn.edu.xmu.shop.model.vo.ShopVo;

import java.io.IOException;
import java.util.ArrayList;
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
    ShopService shopService;

    @Autowired
    GoodsSpuDao goodsSpuDao;

    @Autowired
    GoodsCategoryService categoryService;

    @Autowired
    BrandDao brandDao;

    @Autowired
    GoodsSkuDao goodsSkuDao;

    @Autowired
    ShopDao shopDao;

    @Autowired
    GoodsCategoryDao goodsCategoryDao;

    //@Autowired
    //ShopService shopService;

    @Value("${goodsservice.dav.username}")
    private String davUsername;

    @Value("${goodsservice.dav.password}")
    private String davPassword;

    @Value("${goodsservice.dav.baseUrl}")
    private String baseUrl;
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
            logger.info("findSpuById : " + returnObject);
            GoodsSpuRetVo goodsSpuRetVo = new GoodsSpuRetVo(new GoodsSpu(goodsSpuPo));
            //goodsSpuRetVo.setCategory(categoryService.findCategorySimpleVoById(goodsSpuPo.getCategoryId()).getData());
            ShopPo shopPo = shopDao.getShopById(goodsSpuPo.getShopId());
            Shop shop = new Shop(shopPo);
            ShopSimpleVo shopSimpleVo = shop.createSimpleVo();
            goodsSpuRetVo.setShop(shopSimpleVo);
            Brand brand = brandDao.getBrandById(goodsSpuPo.getBrandId()).getData();
            if(brand != null){
                BrandSimpleRetVo brandSimpleRetVo = brand.createSimpleVo();
                if (brandSimpleRetVo == null){
                    logger.info("simplevo == null");
                }
                else logger.info("brand!=null");
                goodsSpuRetVo.setBrand(brandSimpleRetVo);
            }

            GoodsCategory goodsCategory = goodsCategoryDao.getCategoryById(goodsSpuPo.getCategoryId()).getData();
            if(goodsCategory != null){
                GoodsCategorySimpleVo goodsCategorySimpleVo = goodsCategory.createSimpleVo();
                if (goodsCategorySimpleVo == null){
                    logger.info("simplevo == null");
                }
                else logger.info("brand!=null");
                goodsSpuRetVo.setCategory(goodsCategorySimpleVo);
            }

            goodsSpuRetVo.setFreightId(goodsSpuPo.getFreightId());
            Spec s = new Spec();
            String specJson = goodsSpuPo.getSpec();
            if(specJson!=null && (!specJson.contentEquals("default")))
            {
                s.setName(JacksonUtil.parseObject(specJson,"name",String.class));
                s.setId(JacksonUtil.parseObject(specJson,"id",Long.class));
                List<SpecItems> l = JacksonUtil.parseObjectList(specJson,"specItems",SpecItems.class);
                s.setSpecItems(JacksonUtil.parseObjectList(specJson,"specItems",SpecItems.class));
                goodsSpuRetVo.setSpec(s);
            }


            List<GoodsSkuPo> Skus = goodsSkuDao.getSkuBySpuId(goodsSpuPo.getId());
            List<GoodsSkuSimpleRetVo> retSkus = new ArrayList<>();
            for( GoodsSkuPo goodsSkuPo : Skus) {
                retSkus.add(new GoodsSkuSimpleRetVo(new GoodsSku(goodsSkuPo)));
            }
            goodsSpuRetVo.setSku(retSkus);
            //goodsSpuRetVo.setShop((ShopSimpleRetVo) shopService.getShopById(goodsSpuPo.getShopId()).getData().createSimpleVo());
            returnObject = new ReturnObject<> (goodsSpuRetVo);
        } else {
            logger.info("findSpuById: Not Found");
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
    public ReturnObject<GoodsSpuRetVo> insertGoodsSpu(GoodsSpu goodsSpu, Long id) {

        ReturnObject<GoodsSpuRetVo> retObj = null;

        goodsSpu.setShopId(id);
        GoodsSpu spu = goodsSpuDao.insertGoodsSpu(goodsSpu).getData();
        GoodsSpuRetVo goodsSpuRetVo = spu.createVo();
        retObj = new ReturnObject<>(goodsSpuRetVo);
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
        if (goodsSpuDao.checkSpuIdInShop(shopId,id)) {
            vo.setShopId(shopId);
            vo.setId(id);
            return goodsSpuDao.updateSpu(vo);
        } else {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
    }

    @Transactional
    @Override
    public ReturnObject<VoObject> removeSpuCategory(Long shopId,Long spuId, Long id) {
        return goodsSpuDao.deleteGoodsCategory(shopId,spuId,id);
    }

    @Transactional
    @Override
    public ReturnObject<VoObject> addSpuCategory(Long shopId,Long spuId, Long id) {
        return goodsSpuDao.insertGoodsCategory(shopId,spuId,id);
    }

    @Override
    @Transactional
    public ReturnObject<VoObject> revokeSpu( Long shopId, Long id){
        ReturnObject<VoObject> ret =  goodsSpuDao.revokeSpu(shopId, id);
        if(ret.getCode() == ResponseCode.OK){
            ReturnObject<VoObject> skuret = goodsSkuDao.setSpuNull(id);
                return skuret;
            }

        return ret;
    }


    @Override
    @Transactional
    public ReturnObject uploadSpuImg(Long id, MultipartFile multipartFile){
        ReturnObject<GoodsSpu> goodsSpuReturnObject = new ReturnObject<>(new GoodsSpu(goodsSpuDao.getGoodsSpuPoById(id).getData()));

        if(goodsSpuReturnObject.getCode() == ResponseCode.RESOURCE_ID_NOTEXIST) {
            return goodsSpuReturnObject;
        }
        GoodsSpu goodsSpu = goodsSpuReturnObject.getData();

        ReturnObject returnObject = new ReturnObject();
        try{
            returnObject = ImgHelper.remoteSaveImg(multipartFile,2,davUsername, davPassword,baseUrl);
            if(returnObject.getCode()!=ResponseCode.OK){
                return returnObject;
            }

            String oldFilename = goodsSpu.getImageUrl();
            goodsSpu.setImageUrl(returnObject.getData().toString());

            ReturnObject updateReturnObject = goodsSpuDao.updateSpu(goodsSpu);
            if(updateReturnObject.getCode()==ResponseCode.FIELD_NOTVALID) {
                ImgHelper.deleteRemoteImg(returnObject.getData().toString(), davUsername, davPassword, baseUrl);
                return updateReturnObject;
            }
            if(oldFilename!=null) {
                ImgHelper.deleteRemoteImg(oldFilename, davUsername, davPassword,baseUrl);
            }
        }
        catch (IOException e){
            return new ReturnObject(ResponseCode.FILE_NO_WRITE_PERMISSION);
        }
        return new ReturnObject<>();
    }



    @Override
    public ReturnObject<VoObject> setCategoryDefault(Long id,Long defaultValue){
        return goodsSpuDao.setCategoryIdDefault(id,defaultValue);
    }

    public GoodsCartVo getCartByskuId(Long Sku) {
        return null;
    }

    @Override
    public boolean checkSpuIdInShop(Long shopId, Long spuId) {
        return goodsSpuDao.checkSpuIdInShop(shopId,spuId);
    }

    @Override
    public boolean checkSpuIdDisabled(Long spuId) {
        return goodsSpuDao.checkSpuDisabled(spuId);
    }

    @Override
    @Transactional
    public ReturnObject setSkuDisabledByShopId(Long shopId){
        List<Long> spuIds = goodsSpuDao.setAllSpuDisabledByShopId(shopId);
        for(Long spuId : spuIds){
            goodsSkuDao.setSkuDisabledBySpuId(spuId);
        }
        return new ReturnObject<>();
    }

    @Override
    @Transactional
    public ReturnObject setAllSkuOnShelvesByShopId(Long shopId){
        return goodsSkuDao.setAllSkuOnShelvesByShopId(shopId);
    }

    @Override
    @Transactional
    public ReturnObject setAllSkuOffShelvesByShopId(Long shopId){
        return goodsSkuDao.setAllSkuOffShelvesByShopId(shopId);
    }
}
