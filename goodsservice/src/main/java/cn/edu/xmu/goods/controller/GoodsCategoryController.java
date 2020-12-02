package cn.edu.xmu.goods.controller;

import cn.edu.xmu.goods.model.vo.GoodsCategoryRetVo;
import cn.edu.xmu.goods.service.GoodsCategoryService;
import cn.edu.xmu.ooad.annotation.Audit;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ReturnObject;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author：谢沛辰
 * @Date: 2020.11.30
 * @Description:类别控制器
 */
@Api(value = "商品服务", tags = "goods")
@RestController
@RequestMapping(value = "/categories",produces = "application/json;charset=UTF-8")
public class GoodsCategoryController {
    private  static  final Logger logger = LoggerFactory.getLogger(GoodsCategoryController.class);

    @Autowired
    GoodsCategoryService goodsCategoryService;

    @Audit
    @PostMapping("/categories/{id}/subcategories")
    public Object createCategory(@PathVariable Long id, @RequestBody String name){
        if(logger.isDebugEnabled()){
            logger.debug("create Category: id = "+id);
        }
        ReturnObject<GoodsCategoryRetVo> result=goodsCategoryService.createCategory(id,name);
        return Common.decorateReturnObject(result);
    }

    @Audit
    @GetMapping("/categories/{id}/subcategories")
    public Object findSubCategories(@PathVariable Long id){
        if(logger.isDebugEnabled()){
            logger.debug("select category : pid ="+id);
        }
        ReturnObject<List> result=goodsCategoryService.findSubCategory(id);
        return Common.decorateReturnObject(result);
    }

    @Audit
    @PutMapping("/categories/{id}")
    public Object updateCategory(@PathVariable Long id,@RequestBody String name){
        if(logger.isDebugEnabled()){
            logger.debug("update category : id ="+id);
        }
        ReturnObject<Object> result=goodsCategoryService.updateCategory(id,name);
        return Common.decorateReturnObject(result);
    }

    @Audit
    @DeleteMapping("/categories/{id}")
    public Object deleteCategoey(@PathVariable long id){
        if(logger.isDebugEnabled()){
            logger.debug("update category : id ="+id);
        }
        ReturnObject<Object> result=goodsCategoryService.deleteCategory(id);
        return Common.decorateReturnObject(result);
    }
}
