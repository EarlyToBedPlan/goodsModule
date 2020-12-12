package cn.edu.xmu.goods.dao;

import cn.edu.xmu.goods.mapper.BrandPoMapper;
import cn.edu.xmu.goods.mapper.GoodsSpuPoMapper;
import cn.edu.xmu.goods.model.bo.Brand;
import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.po.*;
import cn.edu.xmu.goods.model.vo.BrandRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/3 22:07
 * modifiedBy Yancheng Lai 22:07
 **/

@Repository
public class BrandDao {

    public static final Logger logger = LoggerFactory.getLogger(BrandDao.class);
    @Autowired
    BrandPoMapper brandPoMapper;

    @Autowired
    GoodsSpuPoMapper goodsSpuPoMapper;

    /** 
    * @Description: 得到所有的品牌 
    * @Param: [page, pagesize] 
    * @return: com.github.pagehelper.PageInfo<VoObject> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 20:48
    */
    public PageInfo<VoObject> getAllBrands(Integer page, Integer pagesize) {

        BrandPoExample brandPoExample = new BrandPoExample();
        BrandPoExample.Criteria criteria = brandPoExample.createCriteria();

        List<BrandPo> brandPos = brandPoMapper.selectByExample(brandPoExample);
        List<VoObject> brands = new ArrayList<>();

        for(BrandPo brandPo: brandPos){
            brands.add(new Brand(brandPo));
        }

        return new PageInfo<>(brands);
    }

    /** 
    * @Description: ID查询品牌 
    * @Param: [id] 
    * @return: ReturnObject<Brand> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 20:52
    */
    public ReturnObject<Brand> getBrandById(Long id) {
        Brand brand = new Brand(brandPoMapper.selectByPrimaryKey(id));
        if(brand == null){
            logger.info("brand == null");
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        logger.info("get brand by id: "+ brand.getId().toString());
        return new ReturnObject<>(brand);
    }
    /**
    * @Description: 更新品牌
    * @Param: [brand]
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject>
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 15:28
    */
    public ReturnObject<VoObject> updateBrand(Brand brand){
        logger.info("Dao:update Brand id = "+brand.getId().toString());
        BrandPo po = brandPoMapper.selectByPrimaryKey(brand.getId());
        if (po != null) {
            logger.info("Brand Po != null"+brand.getId().toString());
            po.setGmtModified(LocalDateTime.now());
            brandPoMapper.updateByPrimaryKeySelective(brand.getBrandPo());
            return new ReturnObject<VoObject>(ResponseCode.OK);
        }
        return new ReturnObject<VoObject>(ResponseCode.RESOURCE_ID_NOTEXIST);
    }
    /**
    * @Description: 删除品牌 并且将spu的brandid置为0
    * @Param: [id]
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject>
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 15:28
    */
    public ReturnObject<VoObject> revokeBrand(Long id){
        BrandPo po = brandPoMapper.selectByPrimaryKey(id);
        if (po != null) {
            po.setGmtModified(LocalDateTime.now());
            brandPoMapper.deleteByPrimaryKey(id);
            GoodsSpuPoExample example = new GoodsSpuPoExample();
            GoodsSpuPoExample.Criteria criteria = example.createCriteria();
            criteria.andBrandIdEqualTo(id);
            List<GoodsSpuPo> goodsSpuPos = goodsSpuPoMapper.selectByExample(example);
            for(GoodsSpuPo goodsSpuPo: goodsSpuPos){
                goodsSpuPo.setBrandId(0l);
                goodsSpuPoMapper.updateByPrimaryKeySelective(goodsSpuPo);
            }
            return new ReturnObject<VoObject>();
        }
        return new ReturnObject<VoObject>(ResponseCode.RESOURCE_ID_NOTEXIST);
    }

    /**
    * @Description: SPU 品牌id置0
    * @Param: [brandId]
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject>
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 15:35
    */

    public ReturnObject<VoObject> setSpuBrandNull(Long brandId){
        GoodsSpuPoExample goodsSpuPoExample =  new GoodsSpuPoExample();
        GoodsSpuPoExample.Criteria criteria = goodsSpuPoExample.createCriteria();
        criteria.andBrandIdEqualTo(brandId);
        List<GoodsSpuPo> goodsSpuPos = goodsSpuPoMapper.selectByExample(goodsSpuPoExample);

        for(GoodsSpuPo goodsSpuPo : goodsSpuPos){
            goodsSpuPo.setBrandId(0l);
            goodsSpuPoMapper.updateByPrimaryKeySelective(goodsSpuPo);
        }
        return new ReturnObject<>(ResponseCode.OK);
    }

    /**
    * @Description: 增加品牌
    * @Param: [brand]
    * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.goods.model.bo.Brand>
    * @Author: Yancheng Lai
    * @Date: 2020/12/11 15:35
    */
    public ReturnObject<Brand> addBrand(Brand brand){
        BrandPo brandPo = brand.getBrandPo();
        brandPo.setGmtCreate(LocalDateTime.now());
        int res = brandPoMapper.insertSelective(brandPo);
        if(res == 0){
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
        return new ReturnObject<>(brand);
    }

}
