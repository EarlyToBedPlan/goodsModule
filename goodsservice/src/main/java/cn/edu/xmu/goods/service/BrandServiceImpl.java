package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.dao.BrandDao;
import cn.edu.xmu.goods.dao.GoodsSkuDao;
import cn.edu.xmu.goods.model.bo.Brand;
import cn.edu.xmu.goods.model.vo.BrandRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/3 22:01
 * modifiedBy Yancheng Lai 22:01
 **/
@Service
public class BrandServiceImpl implements BrandService{

    BrandDao brandDao;

    public ReturnObject<PageInfo<BrandRetVo>> getAllBrands(Integer page, Integer pageSize){

        PageHelper.startPage(page, pageSize,true,true,null);

        PageInfo<BrandRetVo> returnObject = brandDao.getAllBrands(page, pageSize);

        return new ReturnObject<>(returnObject);
    }
    @Transactional
    public ReturnObject<VoObject> updateBrand(Brand brand, Long shopId, Long id){
            brand.setId(id);
            return brandDao.updateBrand(brand);
    }

    @Transactional
    public ReturnObject<VoObject> revokeBrand(Long shopId,Long id){
        return brandDao.revokeBrand(id);
    }
}
