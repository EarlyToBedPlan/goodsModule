package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.dao.GoodsCategoryDao;
import cn.edu.xmu.goods.model.bo.GoodsCategory;
import cn.edu.xmu.goods.model.po.GoodsCategoryPo;
import cn.edu.xmu.goods.model.vo.GoodsCategoryRetVo;
import cn.edu.xmu.goods.model.vo.GoodsCategorySimpleVo;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
/**
 * @Author：谢沛辰
 * @Date: 2020.11.30
 * @Description:商品类型业务逻辑
 */
@Service
public class GoodsCategoryService {
    private Logger logger = LoggerFactory.getLogger(GoodsCategoryService.class);

    @Autowired
    GoodsCategoryDao goodsCategoryDao;

    public ReturnObject<List> findSubCategory(long pid){ return goodsCategoryDao.getCategoryByPID(pid); }

    public ReturnObject<GoodsCategoryRetVo> createCategory(long pid, String name){
        GoodsCategory goodsCategory=new GoodsCategory();
        goodsCategory.setPId(pid);
        goodsCategory.setName(name);
        goodsCategory.setGmtGreate(LocalDateTime.now());
        goodsCategory.setGmtModified(LocalDateTime.now());
        ReturnObject<GoodsCategoryPo> media=goodsCategoryDao.insertSubcategory(goodsCategory);
        ReturnObject<GoodsCategoryRetVo> result=null;
        if(media.getCode()==ResponseCode.OK)
        {
            GoodsCategory receiver=new GoodsCategory(media.getData());
            result= new ReturnObject<>(receiver.createVo());
        }
        else
        {
            result=new ReturnObject<>(media.getCode());
        }
        return result;
    }

    public ReturnObject<GoodsCategorySimpleVo> findSimpleCategory(Long id){
        GoodsCategory goodsCategory =  goodsCategoryDao.getCategoryById(id).getData();
        if(goodsCategory == null){
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        GoodsCategorySimpleVo goodsCategorySimpleVo = goodsCategory.createSimpleVo();
        return new ReturnObject<GoodsCategorySimpleVo>(goodsCategorySimpleVo);
    }

    public ReturnObject<Object> updateCategory(long id,String name){
        ReturnObject<GoodsCategory> targetCategory=goodsCategoryDao.getCategoryById(id);
        if(targetCategory.getData()!=null){
            GoodsCategory selected= targetCategory.getData();
            selected.setGmtModified(LocalDateTime.now());
            selected.setName(name);
            return goodsCategoryDao.updateCategory(selected);
        }else{
            return new ReturnObject<Object>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
    }

    public ReturnObject<Object> deleteCategory(long id){
        return goodsCategoryDao.deleteCategory(id);
    }
}
