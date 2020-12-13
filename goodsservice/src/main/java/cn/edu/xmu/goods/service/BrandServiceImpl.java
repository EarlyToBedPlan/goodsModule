package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.dao.BrandDao;
import cn.edu.xmu.goods.dao.GoodsSkuDao;
import cn.edu.xmu.goods.dao.GoodsSpuDao;
import cn.edu.xmu.goods.model.bo.Brand;
import cn.edu.xmu.goods.model.bo.GoodsSpu;
import cn.edu.xmu.goods.model.vo.BrandRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ImgHelper;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;


/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/3 22:01
 * modifiedBy Yancheng Lai 22:01
 **/
@Service
public class BrandServiceImpl implements BrandService{


    public static final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);
    @Autowired
    BrandDao brandDao;

    @Autowired
    GoodsSpuDao goodsSpuDao;

    @Value("${goodsservice.dav.username}")
    private String davUsername;

    @Value("${goodsservice.dav.password}")
    private String davPassword;

    @Value("${goodsservice.dav.baseUrl}")
    private String baseUrl;

    public ReturnObject<PageInfo<VoObject>> getAllBrands(Integer page, Integer pageSize){

        PageHelper.startPage(page, pageSize,true,true,null);

        PageInfo<VoObject> returnObject = brandDao.getAllBrands(page, pageSize);

        return new ReturnObject<>(returnObject);
    }
    @Transactional
    public ReturnObject<VoObject> updateBrand(Brand brand, Long shopId, Long id){
        brand.setId(id);
        logger.info("Service:update Brand id = "+id.toString());
        return brandDao.updateBrand(brand);
    }

    @Transactional
    public ReturnObject<VoObject> revokeBrand(Long id){
        ReturnObject<VoObject> ret =  brandDao.revokeBrand(id);
        if(ret.getCode() == ResponseCode.OK){
            return brandDao.setSpuBrandNull(id);
        }
        return ret;
    }

    @Transactional
    public ReturnObject<VoObject>insertGoodsBrand(Long shopId,Long spuId,Long id)
    {

        return goodsSpuDao.insertGoodsBrand(shopId,spuId,id);

    }

    @Transactional
    public  ReturnObject<VoObject>deleteGoodsBrand(Long shopId,Long spuId,Long id){
        return goodsSpuDao.deleteGoodsBrand(shopId, spuId, id);
    }

    @Transactional
    public ReturnObject uploadBrandImg(Long id, MultipartFile multipartFile){
        ReturnObject<VoObject> brandReturnObject = new ReturnObject<>(brandDao.getBrandById(id).getData());

        if(brandReturnObject.getCode() == ResponseCode.RESOURCE_ID_NOTEXIST) {
            return brandReturnObject;
        }
        Brand brand =(Brand) brandReturnObject.getData();

        ReturnObject returnObject = new ReturnObject();
        try{
            returnObject = ImgHelper.remoteSaveImg(multipartFile,2,davUsername, davPassword,baseUrl);
            if(returnObject.getCode()!=ResponseCode.OK){
                return returnObject;
            }

            String oldFilename = brand.getImageUrl();
            brand.setImageUrl(returnObject.getData().toString());

            ReturnObject updateReturnObject = brandDao.updateBrand(brand);
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
    public ReturnObject<Brand> getBrandById(Long id){
        return brandDao.getBrandById(id);
    }



    @Override
    @Transactional
    public ReturnObject<BrandRetVo> addBrand(Brand brand, Long id) {
        ReturnObject<Brand> ret = brandDao.addBrand(brand);
        return new ReturnObject<BrandRetVo>(ret.getData().createVo());
    }
}
