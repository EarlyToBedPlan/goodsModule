package cn.edu.xmu.presale.controller;

import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ResponseUtil;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.presale.model.bo.PreSale;
import cn.edu.xmu.presale.model.vo.NewPreSaleVo;
import cn.edu.xmu.presale.model.vo.PreSaleStateVo;
import cn.edu.xmu.presale.service.PreSaleService;
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
import java.util.List;

/**
 * @author LJP_3424
 * @create 2020-12-01 15:49
 */
@Api(value = "预售活动", tags = "presale")
@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class PreSaleController {
    private static final Logger logger = LoggerFactory.getLogger(PreSaleController.class);

    @Autowired
    private PreSaleService preSaleService;

    @Autowired
    private HttpServletResponse httpServletResponse;

    /**
     * 查询预售活动所有状态
     *
     * @return Object
     * createdBy: LJP_3424
     */
    @ApiOperation(value = "获得预售活动所有状态")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功")
    })
    @GetMapping("/presales/states")
    public Object getAllPreSalesStates() {
        PreSale.State[] states = PreSale.State.class.getEnumConstants();
        List<PreSaleStateVo> preSaleStateVos = new ArrayList<PreSaleStateVo>();
        for (int i = 0; i < states.length; i++) {
            preSaleStateVos.add(new PreSaleStateVo(states[i]));
        }
        return ResponseUtil.ok(new ReturnObject<List>(preSaleStateVos).getData());
    }


    /**
     * @param shopId
     * @param timeline
     * @param spuId
     * @param page
     * @param pageSize
     * @Description: 筛选查询所有预售活动
     * @return: java.lang.Object
     * @Author: LJP_3424
     * @Date: 2020/12/7 10:36
     */
    @ApiOperation(value = "筛选查询所有预售活动")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "shopId", value = "店铺id", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "timeline", value = "时间", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "spuId", value = "商品ID", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "page", value = "页码", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页面大小", required = false, dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功")
    })
    @GetMapping("/presales")
    public Object selectAllPreSale(
            @RequestParam(required = false) Long shopId,
            @RequestParam(required = false) Byte timeline,
            @RequestParam(required = false) Long spuId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        logger.debug("selectAllPreSale: shopId = " + shopId + " timeline = " + timeline + "spuId = " + spuId + " page = " + page + "  pageSize =" + pageSize);
        ReturnObject<PageInfo<VoObject>> returnObject = preSaleService.selectAllPreSale(shopId, timeline, shopId, page, pageSize);
        if (returnObject.getCode().equals(ResponseCode.RESOURCE_ID_NOTEXIST)) {
            return Common.getPageRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }


    /**
     * @param id
     * @description:查看预售活动
     * @return: java.lang.Object
     * @author: LJP_3424
     */
    @ApiOperation(value = "查询指定商品的历史预售活动")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "商品skuId", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "state", value = "活动所处阶段", required = false)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功")
    })
    //@Audit //认证
    @GetMapping("/shops/{shopId}/spus/{id}/presales")
    public Object getPreSale(
            @PathVariable Long shopId,
            @PathVariable Long id,
            @RequestParam(required = false) Byte state) {
        //
        if (logger.isDebugEnabled()) {
            logger.debug("PreSaleInfo: skuId = " + id + " shopId = " + shopId);
        }

        ReturnObject<List> returnObject = preSaleService.getPreSaleById(id, state);

        if (returnObject.getCode().equals(ResponseCode.RESOURCE_ID_NOTEXIST)) {
            return Common.getListRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }


    /**
     * @param shopId
     * @param id
     * @param vo
     * @param bindingResult
     * @Description:新增预售活动
     * @return: java.lang.Object
     * @Author: LJP_3424
     * @Date: 2020/12/7 12:19
     */
    @ApiOperation(value = "新增预售活动", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "商铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "商品SkuId", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(paramType = "body", dataType = "NewPreSaleVo", name = "vo", value = "可修改的活动信息", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功")
    })
    //@Audit
    @PostMapping("/shops/{shopId}/skus/{id}/presales")
    public Object insertPreSale(
            @PathVariable Long shopId,
            @PathVariable Long id,
            @Validated @RequestBody NewPreSaleVo vo,
            BindingResult bindingResult) {
        logger.debug("insert insertPreSale by shopId:" + shopId + " and skuId: " + id + " and PreSaleVo: " + vo.toString());
        //校验前端数据
        Object returnObject = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject) {
            return returnObject;
        }
        ReturnObject retObject = preSaleService.createNewPreSale(vo, shopId, id);
        if (retObject.getCode() == ResponseCode.OK) {
            return ResponseUtil.ok(retObject.getData());
        } else {
            return ResponseUtil.fail(retObject.getCode());
        }
    }

    /**
     * @param shopId
     * @param id
     * @param vo
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "修改预售活动", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "商铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "活动skuId", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(paramType = "body", dataType = "NewPreSaleVo", name = "vo", value = "可修改的活动信息", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    //@Audit
    @PutMapping("/shops/{shopId}/presales/{id}")
    public Object updatePreSale(@PathVariable Long shopId,
                                @PathVariable Long id,
                                @Validated @RequestBody NewPreSaleVo vo,
                                BindingResult bindingResult) {
        //校验前端数据
        Object returnObject = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject) {
            return returnObject;
        }
        ReturnObject retObject = preSaleService.updatePreSale(vo, id);
        if (retObject.getCode() == ResponseCode.OK) {
            return ResponseUtil.ok(retObject.getData());
        } else {
            return ResponseUtil.fail(retObject.getCode());
        }
    }

    /**
     * 删除优惠活动
     */
    @ApiOperation(value = "删除优惠活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "Token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "shopId", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 906, message = "优惠活动禁止")
    })
    //@Audit // 需要认证
    @DeleteMapping("/shops/{shopId}/presales/{id}")
    public Object deletePreSale(@PathVariable Long id, @PathVariable Long shopId) {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteUser: id = " + id);
        }
        ReturnObject retObject = preSaleService.changePreSaleState(id, PreSale.State.DELETE.getCode());
        if (retObject.getCode() == ResponseCode.OK) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.fail(retObject.getCode());
        }
    }

    /**
     * 上线优惠活动
     */
    @ApiOperation(value = "上线优惠活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "Token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "shopId", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 906, message = "优惠活动禁止")
    })
    //@Audit // 需要认证
    @PutMapping("/shops/{shopId}/presales/{id}/onshelves")
    public Object preSaleOn(@PathVariable Long id, @PathVariable Long shopId) {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteUser: id = " + id);
        }
        ReturnObject retObject = preSaleService.changePreSaleState(id, PreSale.State.ON.getCode());
        if (retObject.getCode() == ResponseCode.OK) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.fail(retObject.getCode());
        }
    }

    /**
     * 下线优惠活动
     */
    @ApiOperation(value = "下线优惠活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "Token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "shopId", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 906, message = "优惠活动禁止")
    })
    //@Audit // 需要认证
    @PutMapping("/shops/{shopId}/presales/{id}/offshelves")
    public Object preSaleOff(@PathVariable Long id, @PathVariable Long shopId) {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteUser: id = " + id);
        }
        ReturnObject retObject = preSaleService.changePreSaleState(id, PreSale.State.OFF.getCode());
        if (retObject.getCode() == ResponseCode.OK) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.fail(retObject.getCode());
        }
    }
}
