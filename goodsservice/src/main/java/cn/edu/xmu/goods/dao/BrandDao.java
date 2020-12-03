package cn.edu.xmu.goods.dao;

import cn.edu.xmu.goods.mapper.BrandPoMapper;
import cn.edu.xmu.goods.model.bo.Brand;
import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.po.*;
import cn.edu.xmu.goods.model.vo.BrandRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/3 22:07
 * modifiedBy Yancheng Lai 22:07
 **/

@Repository
public class BrandDao {

    @Autowired
    BrandPoMapper brandPoMapper;

    public PageInfo<BrandRetVo> getAllBrands(Integer page, Integer pagesize) {

        BrandPoExample brandPoExample = new BrandPoExample();
        BrandPoExample.Criteria criteria = brandPoExample.createCriteria();

        List<BrandPo> brandPos = brandPoMapper.selectByExample(brandPoExample);
        List<BrandRetVo> brandRetVos = null;

        for(BrandPo brandPo: brandPos){
            brandRetVos.add(new Brand(brandPo).createVo());
        }

        return new PageInfo<>(brandRetVos);
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
        return new ReturnObject<VoObject>();
    }
}
