package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.model.bo.Brand;
import cn.edu.xmu.goods.model.vo.BrandRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;

public interface BrandService {

    public ReturnObject<PageInfo<BrandRetVo>> getAllBrands(Integer page, Integer pageSize);

    public ReturnObject<VoObject> updateBrand(Brand brand, Long shopId, Long id);

    public ReturnObject<VoObject> revokeBrand(Long shopId,Long id);
}
