package cn.edu.xmu.goods.controller;

import cn.edu.xmu.goods.model.bo.Brand;
import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.vo.BrandRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSpuRetVo;
import cn.edu.xmu.goods.service.BrandService;
import cn.edu.xmu.ooad.annotation.Audit;
import cn.edu.xmu.ooad.annotation.LoginUser;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ResponseUtil;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/3 21:21
 * modifiedBy Yancheng Lai 21:21
 **/

@Api(value = "品牌服务", tags = "brands")
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/brands", produces = "application/json;charset=UTF-8")

public class BrandController {

    @Autowired
    BrandService brandService;

    public static final Logger logger = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private HttpServletResponse httpServletResponse;

    @ApiOperation(value = "获得Brand列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page", required = true, dataType="String", paramType="query",defaultValue = "1"),
            @ApiImplicitParam(name="pageSize", required = true, dataType="String", paramType="query",defaultValue = "10"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 404,message = "资源不存在")
    })
    @GetMapping("/brands")
    public Object getBrands(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize){
        logger.debug("Test for brands");
        page = (page == null)?1:page;
        pageSize = (pageSize == null)?10:pageSize;


        ReturnObject<PageInfo<VoObject>> returnObject = brandService.getAllBrands(page, pageSize);
        return Common.getPageRetObject(returnObject);



    }

    @ApiOperation(value = "修改品牌信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="Token", required = true, dataType="String", paramType="header"),
            @ApiImplicitParam(name="shopId", required = true, dataType="Long", paramType="path"),
            @ApiImplicitParam(name="id", required = true, dataType="Long", paramType="path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @Audit
    @PutMapping("/shops/{shopId}/brands/{id}")
    public Object changePriv(@PathVariable Long id,
                             @Validated @RequestBody BrandRetVo vo,
                             BindingResult bindingResult,
                             @PathVariable Long shopId,
                             HttpServletResponse httpServletResponse){

        Object o = Common.processFieldErrors(bindingResult, httpServletResponse);
        if(o != null){
            return o;
        }

        Brand brand = new Brand(vo);
        ReturnObject<VoObject> returnObject = brandService.updateBrand(brand, shopId, id);

        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.getRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }

    /** 
    * @Description: 这里用的是物理删除
    * @Param: [shopId, id] 
    * @return: java.lang.Object 
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 22:40
    */

    @ApiOperation(value = "删除品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name="id", value="品牌Id", required = true, dataType="Long", paramType="path"),
            @ApiImplicitParam(name="shopId", value="商店id", required = true, dataType="Long", paramType="path")

    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在"),
            @ApiResponse(code = 503, message = "字段不合法")
    })
    @Audit
    @DeleteMapping("/shops/{shopId}/brands/{id}")
    public Object revokeSpu(@PathVariable Long shopId, @PathVariable Long id){
        return Common.decorateReturnObject(brandService.revokeBrand(id));
    }


}
