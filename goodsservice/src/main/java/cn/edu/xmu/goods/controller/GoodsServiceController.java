package cn.edu.xmu.goods.controller;

import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.bo.GoodsSpu;
import cn.edu.xmu.goods.model.vo.*;
import cn.edu.xmu.goods.service.BrandService;
import cn.edu.xmu.goods.service.GoodsSkuService;
import cn.edu.xmu.goods.service.GoodsSpuService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品服务控制器
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 18:40
 * modifiedBy Yancheng Lai 18:40
 **/

@Api(value = "商品服务", tags = "cn/edu/xmu/goods")
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/goods", produces = "application/json;charset=UTF-8")


public class GoodsServiceController {


    @Autowired
    private HttpServletResponse httpServletResponse;

    private  static  final Logger logger = LoggerFactory.getLogger(GoodsServiceController.class);

    @Autowired
    GoodsSpuService goodsSpuService;

    @Autowired
    GoodsSkuService goodsSkuService;

    @Autowired
    BrandService brandService;


    /**
     * @Description: 获取指定id的sku
     * @Param: [id]
     * @return: java.lang.Object
     * @Author: Yancheng Lai
     * @Date: 2020/12/2 18:45
     */
    @ApiOperation(value = "用id获得某一SKU")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", required = true, dataType="Long", paramType="path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @GetMapping("/skus/{id}")
    public Object getSkuById(@PathVariable Long id){
        logger.debug("controller: get Sku by id: "+ id);
        ReturnObject<GoodsSkuRetVo> returnObject =  goodsSkuService.getSkuById(id);
        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.decorateReturnObject(returnObject);
        }
        return new ResponseEntity(
                ResponseUtil.fail(ResponseCode.OK, "该ID对应的SKU不存在"),
                HttpStatus.NOT_FOUND);
    }


    /**
    * @Description: 获取指定id的spu
    * @Param: [id]
    * @return: java.lang.Object
    * @Author: Yancheng Lai
    * @Date: 2020/12/2 18:45
    */
    @ApiOperation(value = "用id获得某一SPU")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", required = true, dataType="Long", paramType="path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @GetMapping("/spus/{id}")
    public Object getSpuBySpuId(@PathVariable Long id){
        ReturnObject<GoodsSpuRetVo> returnObject =  goodsSpuService.findSpuById(id);
        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.decorateReturnObject(returnObject);
        }
        return new ResponseEntity(
                ResponseUtil.fail(ResponseCode.OK, "该ID对应的SPU不存在"),
                HttpStatus.NOT_FOUND);
    }
    /**
    * @Description: 分页查询SKU
    * @Param: [shopId, skuSn, spuId, spuSn, page, pageSize]
    * @return: java.lang.Object
    * @Author: Yancheng Lai
    * @Date: 2020/12/2 21:56
    */

    @ApiOperation(value = "分页查询SKU")
    @ApiImplicitParams({
            @ApiImplicitParam(name="shopId", required = false, dataType="Integer", paramType="query"),
            @ApiImplicitParam(name="skuSn", required = false, dataType="String", paramType="query"),
            @ApiImplicitParam(name="spuId", required = false, dataType="Integer", paramType="query"),
            @ApiImplicitParam(name="spuSn", required = false, dataType="String", paramType="query"),
            @ApiImplicitParam(name="page", required = false, dataType="Integer", paramType="query"),
            @ApiImplicitParam(name="pageSize", required = false, dataType="Integer", paramType="query"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作对象不存在"),
            @ApiResponse(code = 503, message = "字段不合法")
    })
    @GetMapping("/skus")
    public Object getSku(
            @PathVariable("shopId") Long shopId,
            @PathVariable("skuSn") String skuSn,
            @PathVariable("spuId") Long spuId,
            @PathVariable("spuSn") String spuSn,
            @PathVariable("page") Integer page,
            @PathVariable("pageSize") Integer pageSize
    ){
            Object retObject = null;
            if(page <= 0 || pageSize <= 0) {
                retObject = Common.getNullRetObj(new ReturnObject<>(ResponseCode.FIELD_NOTVALID), httpServletResponse);
            } else {
                ReturnObject<PageInfo<GoodsSkuRetVo>> returnObject = goodsSkuService.findAllSkus(
                        shopId, skuSn, spuId,spuSn, page, pageSize);
                logger.debug("findSkus: getSkus = " + returnObject);
                ResponseCode code = returnObject.getCode();
                switch (code){
                    case OK:
                        PageInfo<GoodsSkuRetVo> objs = returnObject.getData();
                        if (objs != null){
                            List<Object> voObjs = new ArrayList<>(objs.getList().size());
                            for (Object data : objs.getList()) {
                                if (data instanceof VoObject) {
                                    voObjs.add(((VoObject)data).createVo());
                                }
                            }

                            Map<String, Object> ret = new HashMap<>();
                            ret.put("list", voObjs);
                            ret.put("total", objs.getTotal());
                            ret.put("page", objs.getPageNum());
                            ret.put("pageSize", objs.getPageSize());
                            ret.put("pages", objs.getPages());
                            return ResponseUtil.ok(ret);
                        }else{
                            return ResponseUtil.ok();
                        }
                    default:
                        return ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg());
                }
            }

            return retObject;

    }

    /**
    * @Description: 逻辑删除 SKU
    * @Param: [shopId, id]
    * @return: java.lang.Object
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 15:24
    */
    @ApiOperation(value = "逻辑删除SKU")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name="id", value="角色id", required = true, dataType="Long", paramType="path"),
            @ApiImplicitParam(name="shopId", value="部门id", required = true, dataType="Long", paramType="path")

    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在"),
            @ApiResponse(code = 503, message = "字段不合法")
    })
    @Audit
    @DeleteMapping("/shops/{shopId}/skus/{id}")
    public Object revokeSku(@PathVariable Long shopId, @PathVariable Long id){
        return Common.decorateReturnObject(goodsSkuService.revokeSku(shopId, id));
    }

    /**
    * @Description: 新增SKU进SPU
    * @Param: [vo, bindingResult, userId, shopId, id]
    * @return: java.lang.Object
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 15:25
    */

    @ApiOperation(value = "新增Sku进Spu", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "SkuVo", name = "vo", value = "可修改的SKU信息", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "skuId", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在"),
            @ApiResponse(code = 503, message = "字段不合法")
    })
    //@Audit
    @PostMapping("/shops/{shopId}/spus/{id}/skus")
    public Object insertSku(@Validated @RequestBody GoodsSkuPostVo vo, BindingResult bindingResult,
                             @PathVariable("shopId") Long shopId,
                             @PathVariable("id") Long id) {

        Object returnObject = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject) {
            logger.info("validate fail");
            return returnObject;
        }
        GoodsSku goodsSku = new GoodsSku(vo);
        goodsSku.setDisabled((byte)0);
        goodsSku.setGmtCreate(LocalDateTime.now());
        goodsSku.setGmtModified(LocalDateTime.now());
        ReturnObject<GoodsSkuSimpleRetVo> retObject = goodsSkuService.insertGoodsSku(goodsSku, shopId, id);
        if (retObject.getData() != null) {
            httpServletResponse.setStatus(HttpStatus.CREATED.value());
            //return Common.getRetObject(retObject);
            ResponseCode code = retObject.getCode();
            switch (code){
                case OK:
                    GoodsSkuSimpleRetVo data = retObject.getData();
                    if (data != null){
                        return ResponseUtil.ok(data);
                    }else{
                        return ResponseUtil.ok();
                    }
                default:
                    return ResponseUtil.fail(retObject.getCode(), retObject.getErrmsg());
            }

        } else {
            return Common.getNullRetObj(new ReturnObject<>(retObject.getCode(), retObject.getErrmsg()), httpServletResponse);
        }
    }

    /**
    * @Description: 修改SKU信息
    * @Param: [id, vo, shopId, bindingResult, userId, httpServletResponse]
    * @return: java.lang.Object
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 19:38
    */

    @ApiOperation(value = "修改SKU信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="Token", required = true, dataType="String", paramType="header"),
            @ApiImplicitParam(name="shopId", required = true, dataType="Long", paramType="path"),
            @ApiImplicitParam(name="id", required = true, dataType="Long", paramType="path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @Audit
    @PutMapping("/shops/{shopId}/skus/{id}")
    public Object updateSkus(@PathVariable Long id,
                             @Validated @RequestBody GoodsSkuPostVo vo,
                             BindingResult bindingResult,
                             @PathVariable Long shopId,
                             HttpServletResponse httpServletResponse){

        Object o = Common.processFieldErrors(bindingResult, httpServletResponse);
        if(o != null){
            return o;
        }

        GoodsSku goodsSku = new GoodsSku(vo);
        ReturnObject<VoObject> returnObject = goodsSkuService.updateSku(goodsSku, shopId, id);

        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.decorateReturnObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }

    /**
    * @Description: 新增SPU
    * @Param: [vo, bindingResult, userId, shopId, id]
    * @return: java.lang.Object
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 19:38
    */

    @ApiOperation(value = "新增Spu", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "RoleVo", name = "vo", value = "可修改的SKU信息", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "店铺id", value = "skuId", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在"),
            @ApiResponse(code = 503, message = "字段不合法")
    })
    @Audit
    @PostMapping("/shops/{id}/spus")
    public Object insertSku(@Validated @RequestBody GoodsSpuPostVo vo, BindingResult bindingResult,
                             @PathVariable("id") Long id) {

        Object returnObject = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject) {
            logger.debug("validate fail");
            return returnObject;
        }
        GoodsSpu goodsSpu = new GoodsSpu(vo);
        goodsSpu.setDisabled((byte)0);
        goodsSpu.setGmtCreate(LocalDateTime.now());
        goodsSpu.setGmtModified(LocalDateTime.now());
        ReturnObject<GoodsSpuRetVo> retObject = goodsSpuService.insertGoodsSpu(goodsSpu, id);
        if (retObject.getData() != null) {
            httpServletResponse.setStatus(HttpStatus.CREATED.value());
            return Common.decorateReturnObject(retObject);
        } else {
            return Common.getNullRetObj(new ReturnObject<>(retObject.getCode(), retObject.getErrmsg()), httpServletResponse);
        }
    }


    @ApiOperation(value = "修改SPU信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="Token", required = true, dataType="String", paramType="header"),
            @ApiImplicitParam(name="shopId", required = true, dataType="Long", paramType="path"),
            @ApiImplicitParam(name="id", required = true, dataType="Long", paramType="path"),
            @ApiImplicitParam(paramType = "body", dataType = "RoleVo", name = "vo", value = "可修改的Spi信息", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @Audit
    @PutMapping("/shops/{shopId}/spus/{id}")

    public Object modifyGoodsSpu(@PathVariable Long id,
                             @Validated @RequestBody GoodsSpuPostVo vo,
                             BindingResult bindingResult,
                             @PathVariable Long shopId,
                             HttpServletResponse httpServletResponse){

        Object o = Common.processFieldErrors(bindingResult, httpServletResponse);
        if(o != null){
            return o;
        }

        GoodsSpu goodsSpu = new GoodsSpu(vo);
        ReturnObject<VoObject> returnObject = goodsSpuService.updateSpu(goodsSpu, shopId, id);

        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.decorateReturnObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }

    /** 
    * @Description: 获取SPU所有状态
    * @Param: [id] 
    * @return: cn.edu.xmu.ooad.util.ReturnObject<java.util.List<cn.edu.xmu.goods.model.bo.GoodsSpu.State>> 
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 20:56
    */
    @ApiOperation(value = "获取SPU的所有状态")
    @ApiImplicitParams({
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @GetMapping("/skus/states")
    public  Object getSpuState() {
        ReturnObject<List<StateVo>> returnObject = goodsSkuService.findSkuStates();
        return Common.decorateReturnObject(returnObject);
    }

    /**
    * @Description: 获取share里的详细信息
    * @Param: [sid, id]
    * @return: java.lang.Object
    * @Author: Yancheng Lai
    * @Date: 2020/12/3 21:01
    */
    @ApiOperation(value = "获取share的id的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", required = true, dataType="String", paramType="path"),
            @ApiImplicitParam(name="sid", required = true, dataType="String", paramType="path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @Audit
    @GetMapping("/share/{sid}/skus/{id}")
    public Object getSkuByShare(@PathVariable Long sid,@PathVariable Long id){
        ReturnObject<GoodsSkuRetVo> returnObject =  goodsSkuService.getSkuById(id);
        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.decorateReturnObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }


    @ApiOperation(value = "逻辑删除SPU")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name="id", value="spuId", required = true, dataType="Long", paramType="path"),
            @ApiImplicitParam(name="shopId", value="商店id", required = true, dataType="Long", paramType="path")

    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在"),
            @ApiResponse(code = 503, message = "字段不合法")
    })
    @Audit
    @DeleteMapping("/shops/{shopId}/spus/{id}")
    public Object revokeSpu(@PathVariable Long shopId, @PathVariable Long id){
        return Common.decorateReturnObject(goodsSpuService.revokeSpu(shopId, id));
    }



    @ApiOperation(value = "商品上架")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="Token", required = true, dataType="String", paramType="header"),
            @ApiImplicitParam(name="shopId", required = true, dataType="Long", paramType="path"),
            @ApiImplicitParam(name="id", required = true, dataType="Long", paramType="path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @Audit
    @PutMapping("/shops/{shopId}/skus/{id}/onshelves")
    public Object updateSkuOnshelves(@PathVariable Long id,
                             @PathVariable Long shopId,
                             HttpServletResponse httpServletResponse){

        ReturnObject<VoObject> returnObject = goodsSkuService.updateSkuOnShelves(shopId, id);

        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.decorateReturnObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }

    @ApiOperation(value = "商品下架")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="Token", required = true, dataType="String", paramType="header"),
            @ApiImplicitParam(name="shopId", required = true, dataType="Long", paramType="path"),
            @ApiImplicitParam(name="id", required = true, dataType="Long", paramType="path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @Audit
    @PutMapping("/shops/{shopId}/skus/{id}/offshelves")
    public Object updateSkuOffshelves(@PathVariable Long id,
                                     @PathVariable Long shopId,
                                     HttpServletResponse httpServletResponse){

        ReturnObject<VoObject> returnObject = goodsSkuService.updateSkuOffShelves(shopId, id);

        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.decorateReturnObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }
    /**
    * @Description:  SPU新增品牌 tested
    * @Param: [shopId, id, skuId]
    * @return: java.lang.Object
    * @Author: Yancheng Lai
    * @Date: 2020/12/4 13:49
    */

    @ApiOperation(value = "品牌进Spu", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "品牌Id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "spuId", value = "spuId", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在"),
            @ApiResponse(code = 503, message = "字段不合法")
    })
    @Audit
    @PostMapping("/shops/{shopId}/spus/{spuId}/brands/{id}")
    public Object addSpuBrand(
                             @PathVariable("shopId") Long shopId,
                             @PathVariable("id") Long id,
                             @PathVariable("spuId") Long spuId  ) {


        ReturnObject<VoObject> retObject = brandService.insertGoodsBrand(shopId, spuId,id);
        if (retObject.getData() != null) {
            httpServletResponse.setStatus(HttpStatus.CREATED.value());
            return Common.decorateReturnObject(retObject);
        } else {
            return Common.getNullRetObj(new ReturnObject<>(retObject.getCode(), retObject.getErrmsg()), httpServletResponse);
        }
    }

    /**
    * @Description: SPU删除品牌
    * @Param: [shopId, id, spuId]
    * @return: java.lang.Object
    * @Author: Yancheng Lai
    * @Date: 2020/12/4 14:02
    */

    @ApiOperation(value = "Spu删除品牌", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "品牌Id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "spuId", value = "spuId", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在"),
            @ApiResponse(code = 503, message = "字段不合法")
    })
    @Audit
    @DeleteMapping("/shops/{shopId}/spus/{spuId}/brands/{id}")
    public Object removeSpuBrand(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id,
            @PathVariable("spuId") Long spuId  ) {


        ReturnObject<VoObject> retObject = brandService.deleteGoodsBrand(shopId, spuId,id);
        if (retObject.getData() != null) {
            httpServletResponse.setStatus(HttpStatus.CREATED.value());
            return Common.decorateReturnObject(retObject);
        } else {
            return Common.getNullRetObj(new ReturnObject<>(retObject.getCode(), retObject.getErrmsg()), httpServletResponse);
        }
    }

    @ApiOperation(value = "SKU上传图片",  produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "formData", dataType = "file", name = "img", value ="文件", required = true),
            @ApiImplicitParam(paramType = "path",dataType = "Long",name = "shopId",value = "店铺id"),
            @ApiImplicitParam(paramType = "path",dataType = "Long",name = "id",value = "skuid"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 506, message = "该目录文件夹没有写入的权限"),
            @ApiResponse(code = 508, message = "图片格式不正确"),
            @ApiResponse(code = 509, message = "图片大小超限")
    })
    @Audit
    @PostMapping("/shops/{shopId}/skus/{id}/uploadImg")
    public Object uploadSkuImg(@RequestParam("img") MultipartFile multipartFile,
                               @PathVariable("shopId") Long shopId,
                               @PathVariable("id") Long id){
        if(goodsSkuService.checkSkuIdInShop(shopId,id)==false){
            return Common.decorateReturnObject(new ReturnObject(ResponseCode.RESOURCE_ID_OUTSCOPE));
        }
        ReturnObject returnObject = goodsSkuService.uploadSkuImg(id, shopId,multipartFile);
        return Common.decorateReturnObject(returnObject);
    }



    @ApiOperation(value = "SPU上传图片",  produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "formData", dataType = "file", name = "img", value ="文件", required = true),
            @ApiImplicitParam(paramType = "path",dataType = "Long",name = "shopId",value = "店铺id"),
            @ApiImplicitParam(paramType = "path",dataType = "Long",name = "id",value = "spuid"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 506, message = "该目录文件夹没有写入的权限"),
            @ApiResponse(code = 508, message = "图片格式不正确"),
            @ApiResponse(code = 509, message = "图片大小超限")
    })
    @Audit
    @PostMapping("/shops/{shopId}/spus/{id}/uploadImg")
    public Object uploadSpuImg(@RequestParam("img") MultipartFile multipartFile,
                               @PathVariable("shopId") Long shopId,
                               @PathVariable("id") Long id){

        if(goodsSpuService.checkSpuIdInShop(shopId,id)==false){
            return Common.decorateReturnObject(new ReturnObject(ResponseCode.RESOURCE_ID_OUTSCOPE));
        }
        ReturnObject returnObject = goodsSpuService.uploadSpuImg(id, multipartFile);
        return Common.decorateReturnObject(returnObject);
    }


    @ApiOperation(value = "品牌上传图片",  produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "formData", dataType = "file", name = "img", value ="文件", required = true),
            @ApiImplicitParam(paramType = "path",dataType = "Long",name = "shopId",value = "店铺id"),
            @ApiImplicitParam(paramType = "path",dataType = "Long",name = "id",value = "品牌id"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 506, message = "该目录文件夹没有写入的权限"),
            @ApiResponse(code = 508, message = "图片格式不正确"),
            @ApiResponse(code = 509, message = "图片大小超限")
    })
    @Audit
    @PostMapping("/shops/{shopId}/brands/{id}/uploadImg")
    public Object uploadBrandImg(@RequestParam("img") MultipartFile multipartFile,
                               @PathVariable("shopId") Long shopId,
                               @PathVariable("id") Long id){
        ReturnObject returnObject = brandService.uploadBrandImg(id, multipartFile);
        return Common.decorateReturnObject(returnObject);
    }

    /**
     * @Description:  SPU新增种类
    * @Param: [shopId, id, skuId]
     * @return: java.lang.Object
    * @Author: Yancheng Lai
    * @Date: 2020/12/4 13:49
     */

    @ApiOperation(value = "spu增加种类", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "种类Id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "spuId", value = "spuId", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在"),
            @ApiResponse(code = 503, message = "字段不合法")
    })
    @Audit
    @PostMapping("/shops/{shopId}/spus/{spuId}/categories/{id}")
    public Object addSpuCategory(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id,
            @PathVariable("spuId") Long spuId  ) {


        ReturnObject<VoObject> retObject = goodsSpuService.addSpuCategory(shopId,  spuId,id);
        if (retObject.getData() != null) {
            httpServletResponse.setStatus(HttpStatus.CREATED.value());
            return Common.decorateReturnObject(retObject);
        } else {
            return Common.getNullRetObj(new ReturnObject<>(retObject.getCode(), retObject.getErrmsg()), httpServletResponse);
        }
    }

    /**
     * @Description: SPU删除种类
     * @Param: [shopId, id, spuId]
     * @return: java.lang.Object
     * @Author: Yancheng Lai
     * @Date: 2020/12/4 14:02
     */

    @ApiOperation(value = "Spu删除种类", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "种类Id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "spuId", value = "spuId", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在"),
            @ApiResponse(code = 503, message = "字段不合法")
    })
    @Audit
    @DeleteMapping("/shops/{shopId}/spus/{spuId}/categories/{id}")
    public Object removeSpuCategory(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id,
            @PathVariable("spuId") Long spuId  ) {


        ReturnObject<VoObject> retObject = goodsSpuService.removeSpuCategory(shopId, spuId,id);
        if (retObject.getData() != null) {
            httpServletResponse.setStatus(HttpStatus.CREATED.value());
            return Common.decorateReturnObject(retObject);
        } else {
            return Common.getNullRetObj(new ReturnObject<>(retObject.getCode(), retObject.getErrmsg()), httpServletResponse);
        }
    }

}
