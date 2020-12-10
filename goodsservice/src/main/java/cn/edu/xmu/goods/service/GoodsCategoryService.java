package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.model.vo.GoodsCategoryRetVo;
import cn.edu.xmu.goods.model.vo.GoodsCategorySimpleVo;
import cn.edu.xmu.ooad.util.ReturnObject;

import java.util.List;

public interface GoodsCategoryService {
    public ReturnObject<List> findSubCategory(long pid);
    public ReturnObject<GoodsCategoryRetVo> createCategory(long pid, String name);
    public ReturnObject<Object> updateCategory(long id,String name);
    public ReturnObject<Object> deleteCategory(long id);
    public ReturnObject<GoodsCategorySimpleVo> findCategorySimpleVoById(Long categoryId);
}
