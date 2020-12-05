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
     * @param id
     * @description:查看预售活动
     * @return: java.lang.Object
     * @author: LJP_3424
     */
    @ApiOperation(value = "查询指定商品的历史预售活动")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "Integer", paramType = "path"),
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
            @RequestParam(required = false, defaultValue = "-1") Byte state) {
        if (logger.isDebugEnabled()) {
            logger.debug("PreSaleInfo: spuId = " + id + " shopId = " + shopId);
        }

        ReturnObject<List> returnObject = preSaleService.getPreSaleById(shopId, id, state);

        if (returnObject.getCode().equals(ResponseCode.RESOURCE_ID_NOTEXIST)) {
            return Common.getListRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }

    /**
     * @param id
     * @description:查看所有预售活动
     * @return: java.lang.Object
     * @author: LJP_3424
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
            @RequestParam(required = false, defaultValue = "0") Long shopId,
            @RequestParam(required = false, defaultValue = "-1") Byte timeline,
            @RequestParam(required = false, defaultValue = "0") Long spuId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        logger.debug("selectAllPreSale: shopId = " + shopId + " timeline = " + timeline + "spuId = " + spuId + " page = " + page + "  pageSize =" + pageSize);
        ReturnObject<PageInfo<VoObject>> returnObject = preSaleService.selectAllPreSale(shopId, timeline, shopId, page, pageSize);
        return Common.getPageRetObject(returnObject);
    }

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
     * 新增预售活动
     *
     * @param vo 角色视图
     * @author LJP_3424
     */
    @ApiOperation(value = "新增预售活动", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "商铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "商品SPUId", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(paramType = "body", dataType = "NewPreSaleVo", name = "vo", value = "可修改的活动信息", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功")
    })
    //@Audit
    @PostMapping("/shops/{shopId}/spus/{id}/presales")
    public Object insertPreSale(
            @PathVariable Long shopId,
            @PathVariable Long id,
            @Validated @RequestBody NewPreSaleVo vo,
            BindingResult bindingResult) {
        logger.debug("insert insertPreSale by shopId:" + shopId + " and spuId: " + id + " and PreSaleVo: " + vo.toString());
        if (bindingResult.hasErrors()) {
            return Common.processFieldErrors(bindingResult, httpServletResponse);
        }
        ReturnObject returnObject = preSaleService.createNewPreSale(vo, shopId, id);
        if (returnObject.getCode() == ResponseCode.OK) {
            return ResponseUtil.ok(returnObject.getData());
        } else return ResponseUtil.fail(returnObject.getCode());
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
    @DeleteMapping("/shops/{shopId}/spus/{id}/presales")
    public Object deletePreSale(@PathVariable Long id, @PathVariable Long shopId) {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteUser: id = " + id);
        }
        ReturnObject returnObject = preSaleService.deletePreSale(shopId, id);
        return Common.decorateReturnObject(returnObject);
    }

    /**
     * 修改活动
     *
     * @author LJP_3424
     */
    @ApiOperation(value = "新增预售活动", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "商铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "商品SPUId", required = true, dataType = "Integer", paramType = "path"),
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
        /*Object returnObject = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject) {
            return returnObject;
        }*/
        ReturnObject retObject = preSaleService.updatePreSale(vo, shopId, id);
        if (retObject.getData() != null) {
            return Common.getRetObject(retObject);
        } else {
            return Common.getNullRetObj(new ReturnObject<>(retObject.getCode(), retObject.getErrmsg()), httpServletResponse);
        }
    }


}
