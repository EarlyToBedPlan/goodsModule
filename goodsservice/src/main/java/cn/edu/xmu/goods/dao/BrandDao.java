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

    public ReturnObject<Brand> getBrandById(Long id) {
        Brand brand = new Brand(brandPoMapper.selectByPrimaryKey(id));
        if(brand == null){
            logger.info("brand == null");
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        logger.info("get brand by id: "+ brand.getId().toString());
        return new ReturnObject<>(brand);
    }
    public ReturnObject<VoObject> updateBrand(Brand brand){
        BrandPo po = brandPoMapper.selectByPrimaryKey(brand.getId());
        if (po != null) {
            brandPoMapper.updateByPrimaryKeySelective(brand.getBrandPo());
            return new ReturnObject<VoObject>();
        }
        return new ReturnObject<VoObject>();
    }

    public ReturnObject<VoObject> revokeBrand(Long id){
        BrandPo po = brandPoMapper.selectByPrimaryKey(id);
        if (po != null) {
            brandPoMapper.deleteByPrimaryKey(id);
            return new ReturnObject<VoObject>();
        }
        return new ReturnObject<VoObject>(ResponseCode.RESOURCE_ID_NOTEXIST);
    }

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
}
