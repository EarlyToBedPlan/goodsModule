package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.model.bo.Brand;
import cn.edu.xmu.goods.model.vo.BrandRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

public interface BrandService {

    public ReturnObject<PageInfo<VoObject>> getAllBrands(Integer page, Integer pageSize);

    public ReturnObject<VoObject> updateBrand(Brand brand, Long shopId, Long id);

    public ReturnObject<VoObject> revokeBrand(Long id);

    ReturnObject<VoObject>insertGoodsBrand(Long spuId,Long id);

    public ReturnObject uploadBrandImg(Long id, MultipartFile multipartFile);

    public ReturnObject<Brand> getBrandById(Long id);

    ReturnObject<VoObject>deleteGoodsBrand(Long spuId,Long id);
}
