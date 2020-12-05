package cn.edu.xmu.flashsale.controller;

import cn.edu.xmu.flashsale.model.vo.NewFlashSaleItemVo;
import cn.edu.xmu.flashsale.model.vo.NewFlashSaleVo;
import cn.edu.xmu.flashsale.service.FlashSaleService;
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
import javax.validation.executable.ValidateOnExecution;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

/**
 * @author LJP_3424
 * @create 2020-12-03 16:43
 */
@Api(value = "秒杀活动", tags = "flashsale")
@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class FlashSaleController {
    private static final Logger logger = LoggerFactory.getLogger(FlashSaleController.class);

    @Autowired
    private FlashSaleService flashSaleService;

    @Autowired
    private HttpServletResponse httpServletResponse;

    /**
     * @param id
     * @description:查看秒杀活动
     * @return: java.lang.Object
     * @author: LJP_3424
     */
    @ApiOperation(value = "查询某一时段秒杀活动详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "state", value = "活动所处阶段", required = false)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功")
    })
    //@Audit //认证
    @GetMapping("/timesegments/{id}/flashsales")
    public Object getFlashSale(@PathVariable Long id) {
        if (logger.isDebugEnabled()) {
            logger.debug("FlashSaleInfo: timeSegmentId = " + id);
        }

        ReturnObject<List> returnObject = flashSaleService.getFlashSaleById(id);

        if (returnObject.getCode().equals(ResponseCode.RESOURCE_ID_NOTEXIST)) {
            return Common.getListRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }

    /**
     * 新增秒杀活动
     *
     * @param vo 角色视图
     * @author LJP_3424
     */
    @ApiOperation(value = "新增秒杀活动", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功")
    })
    //@Audit
    @PostMapping("/timesegments/{id}/flashsales")
    public Object insertFlashSale(
            @PathVariable Long id,
            @Validated @RequestBody NewFlashSaleVo vo,
            BindingResult bindingResult) {
        logger.debug("insert insertFlashSale by timeSegmentId:" + id + " and FlashSaleVo: " + vo.toString());
        if (bindingResult.hasErrors()) {
            return Common.processFieldErrors(bindingResult, httpServletResponse);
        }
        ReturnObject returnObject = flashSaleService.createNewFlashSale(vo, id);
        if (returnObject.getCode() == ResponseCode.OK) {
            return ResponseUtil.ok(returnObject.getData());
        } else return ResponseUtil.fail(returnObject.getCode());
    }

    /**
     * @param id
     * @description:查看当前时间段秒杀活动
     * @return: java.lang.Object
     * @author: LJP_3424
     */
    @ApiOperation(value = "获取当前时间段时段秒杀活动详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "state", value = "活动所处阶段", required = false)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功")
    })
    //@Audit //认证
    @GetMapping("/timesegments/current")
    public Object getFlashSale() {

        Calendar now = Calendar.getInstance();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        ReturnObject<List> returnObject = flashSaleService.getCurrentFlashSale(dateTimeNow);
        if (returnObject.getCode().equals(ResponseCode.RESOURCE_ID_NOTEXIST)) {
            return Common.getListRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }

    /**
     * 修改活动
     *
     * @author LJP_3424
     */
    @ApiOperation(value = "修改秒杀活动", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "商铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "商品SPUId", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(paramType = "body", dataType = "NewFlashSaleVo", name = "vo", value = "可修改的活动信息", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    //@Audit
    @PutMapping("/flashsales/{id}")
    public Object updateFlashSale(
            @PathVariable Long id,
            @Validated @RequestBody NewFlashSaleVo vo,
            BindingResult bindingResult) {
        //校验前端数据
        Object returnObject = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject) {
            return returnObject;
        }
        ReturnObject retObject = flashSaleService.updateFlashSale(vo, id);
        if (retObject.getData() != null) {
            return Common.getRetObject(retObject);
        } else {
            return Common.getNullRetObj(new ReturnObject<>(retObject.getCode(), retObject.getErrmsg()), httpServletResponse);
        }
    }

    /**
     * 秒杀活动添加SKU
     *
     * @author LJP_3424
     */
    @ApiOperation(value = "秒杀活动添加SKU", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "商铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "商品SPUId", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(paramType = "body", dataType = "NewFlashSaleVo", name = "vo", value = "可修改的活动信息", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    //@Audit
    @PostMapping("/flashsales/{id}")
    public Object insertNewFlashSaleItem(
            @PathVariable Long id,
            @Validated @RequestBody NewFlashSaleItemVo vo,
            BindingResult bindingResult) {
        //校验前端数据
        Object returnObject = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject) {
            return returnObject;
        }
        ReturnObject retObject = flashSaleService.insertSkuIntoPreSale(vo, id);
        if (retObject.getData() != null) {
            return Common.getListRetObject(retObject);
        } else {
            return Common.getNullRetObj(new ReturnObject<>(retObject.getCode(), retObject.getErrmsg()), httpServletResponse);
        }
    }


    /**
     * @param id
     * @description:获取秒杀活动商品
     * @return: java.lang.Object
     * @author: LJP_3424
     */
    @ApiOperation(value = "获取秒杀活动商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "秒杀活动id", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "page", value = "页码", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页面大小", required = false, dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功")
    })
    @GetMapping("/flashsales/{id}/flashitems")
    public Object selectAllFlashSale(
            @PathVariable(required = true) Long id,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        ReturnObject<PageInfo<VoObject>> returnObject = flashSaleService.selectAllFlashSale(id, page, pageSize);
        return Common.getPageRetObject(returnObject);
    }
}
