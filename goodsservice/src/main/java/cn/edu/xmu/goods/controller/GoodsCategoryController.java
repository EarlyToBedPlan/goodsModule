package cn.edu.xmu.goods.controller;

import cn.edu.xmu.goods.model.vo.GoodsCategoryRetVo;
import cn.edu.xmu.goods.service.GoodsCategoryServiceImpl;
import cn.edu.xmu.ooad.annotation.Audit;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ReturnObject;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author：谢沛辰
 * @Date: 2020.11.30
 * @Description:类别控制器
 */
@Api(value = "商品服务", tags = "goods")
@RestController
@RequestMapping(value = "",produces = "application/json;charset=UTF-8")
public class GoodsCategoryController {
    private  static  final Logger logger = LoggerFactory.getLogger(GoodsCategoryController.class);

    @Autowired
    GoodsCategoryServiceImpl goodsCategoryService;

    /**
     * @Author：谢沛辰
     * @Date: 2020/12/8
      * @Param: null
     * @Return:
     * @Description:创建商品子分类
     */
    //@Audit
    @ApiOperation(value = "新增商品类目")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization",required = true,dataType = "String",paramType = "header"),
            @ApiImplicitParam(name="id",required = true,dataType = "int",paramType = "path"),
            @ApiImplicitParam(name = "name",required = true,dataType = "String",paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=504,message = "操作id不存在")
    })
    @PostMapping("/categories/{id}/subcategories")
    public Object createCategory(@PathVariable Long id,@Validated @RequestBody String name){
        if(logger.isDebugEnabled()){
            logger.debug("create Category: id = "+id);
        }
        ReturnObject<GoodsCategoryRetVo> result=goodsCategoryService.createCategory(id,name);
        return Common.decorateReturnObject(result);
    }

    /**
     * @Author：谢沛辰
     * @Date: 2020/12/8
      * @Param: null
     * @Return:
     * @Description:查找商品子分类
     */
    @ApiOperation(value = "查询商品分类关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization",required = true,dataType = "String",paramType = "header"),
            @ApiImplicitParam(name="id",required = true,dataType = "int",paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=504,message = "操作id不存在")
    })
    @Audit
    @GetMapping("/categories/{id}/subcategories")
    public Object findSubCategories(@PathVariable Long id){
        if(logger.isDebugEnabled()){
            logger.debug("select category : pid ="+id);
        }
        ReturnObject<List> result=goodsCategoryService.findSubCategory(id);
        return Common.decorateReturnObject(result);
    }

    /**
     * @Author：谢沛辰
     * @Date: 2020/12/8
      * @Param: null
     * @Return:
     * @Description:修改商品类别
     */
    @ApiOperation(value = "新增商品类目")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization",required = true,dataType = "String",paramType = "header"),
            @ApiImplicitParam(name="id",required = true,dataType = "int",paramType = "path"),
            @ApiImplicitParam(name = "name",required = true,dataType = "String",paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=504,message = "操作id不存在")
    })
    @Audit
    @PutMapping("/categories/{id}")
    public Object updateCategory(@PathVariable Long id,@Validated @RequestBody String name){
        if(logger.isDebugEnabled()){
            logger.debug("update category : id ="+id);
        }
        ReturnObject<Object> result=goodsCategoryService.updateCategory(id,name);
        return Common.decorateReturnObject(result);
    }

    /**
     * @Author：谢沛辰
     * @Date: 2020/12/8
      * @Param: null
     * @Return:
     * @Description:删除商品类别
     */
    @ApiOperation(value = "删除商品类目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization",required = true,dataType = "String",paramType = "header"),
            @ApiImplicitParam(name="id",required = true,dataType = "int",paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=504,message = "操作id不存在")
    })
    @Audit
    @DeleteMapping("/categories/{id}")
    public Object deleteCategoey(@PathVariable long id){
        if(logger.isDebugEnabled()){
            logger.debug("delete category : id ="+id);
        }
        ReturnObject<Object> result=goodsCategoryService.deleteCategory(id);
        return Common.decorateReturnObject(result);
    }
}
