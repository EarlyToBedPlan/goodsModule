package cn.edu.xmu.groupon.controller;

import cn.edu.xmu.groupon.model.bo.Groupon;
import cn.edu.xmu.groupon.model.vo.GrouponStateVo;
import cn.edu.xmu.groupon.model.vo.NewGrouponVo;
import cn.edu.xmu.groupon.service.GrouponService;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LJP_3424
 * @create 2020-12-02 21:06
 */
@Api(value = "团购活动", tags = "groupon")
@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class GrouponController {
    private static final Logger logger = LoggerFactory.getLogger(GrouponController.class);

    @Autowired
    private GrouponService grouponService;

    @Autowired
    private HttpServletResponse httpServletResponse;


    /**
     * 查询团购活动所有状态
     *
     * @return Object
     * createdBy: LJP_3424
     */
    @ApiOperation(value = "获得团购活动所有状态")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功")
    })
    @GetMapping("/groupons/states")
    public Object getAllGrouponsStates() {
        Groupon.State[] states = Groupon.State.class.getEnumConstants();
        List<GrouponStateVo> grouponStateVos = new ArrayList<GrouponStateVo>();
        for (int i = 0; i < states.length; i++) {
            grouponStateVos.add(new GrouponStateVo(states[i]));
        }
        return ResponseUtil.ok(new ReturnObject<List>(grouponStateVos).getData());
    }


    /**
     * @param id
     * @description:查看所有团购活动
     * @return: java.lang.Object
     * @author: LJP_3424
     */
    @ApiOperation(value = "筛选查询所有团购活动")
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
    @GetMapping("/groupons")
    public Object selectAllGroupon(
            @RequestParam(required = false, defaultValue = "0") Long shopId,
            @RequestParam(required = false, defaultValue = "-1") Byte timeline,
            @RequestParam(required = false, defaultValue = "0") Long spuId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        logger.debug("selectAllGroupon: shopId = " + shopId + " timeline = " + timeline + "spuId = " + spuId + " page = " + page + "  pageSize =" + pageSize);
        ReturnObject<PageInfo<VoObject>> returnObject = grouponService.selectAllGroupon(shopId, timeline, shopId, page, pageSize);
        return Common.getPageRetObject(returnObject);
    }

    /**
     * @param id
     * @description:查看所有团购(包括下线的)
     * @return: java.lang.Object
     * @author: LJP_3424
     */
    @ApiOperation(value = "筛选查询所有团购,包括下线的")

    @ApiResponses({
            @ApiResponse(code = 0, message = "成功")
    })
    @GetMapping("/shops/{id}/groupons")
    public Object selectAllGroupon(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "-1") Byte state,
            @RequestParam(required = false, defaultValue = "0") Long spuId,
            @RequestParam(required = false, defaultValue = "2000-01-01 18:00:00") String beginTime,
            @RequestParam(required = false, defaultValue = "2099-01-01 18:00:00") String endTime,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ReturnObject<PageInfo<VoObject>> returnObject = grouponService.selectGroupon(id, state, spuId, LocalDateTime.parse(beginTime, df), LocalDateTime.parse(endTime, df), page, pageSize);
        return Common.getPageRetObject(returnObject);
    }

    /**
     * @param id
     * @description:查看团购活动
     * @return: java.lang.Object
     * @author: LJP_3424
     */
    @ApiOperation(value = "查询指定商品的历史团购活动")
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
    @GetMapping("/shops/{shopId}/spus/{id}/groupons")
    public Object getGroupon(
            @PathVariable Long shopId,
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "-1") Byte state) {
        if (logger.isDebugEnabled()) {
            logger.debug("GrouponInfo: spuId = " + id + " shopId = " + shopId);
        }

        ReturnObject<List> returnObject = grouponService.getGrouponById(shopId, id, state);

        if (returnObject.getCode().equals(ResponseCode.RESOURCE_ID_NOTEXIST)) {
            return Common.getListRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }


    /**
     * 新增团购活动
     *
     * @param vo 角色视图
     * @author LJP_3424
     */
    @ApiOperation(value = "新增团购活动", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "商铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "商品SPUId", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(paramType = "body", dataType = "NewGrouponVo", name = "vo", value = "可修改的活动信息", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功")
    })
    //@Audit
    @PostMapping("/shops/{shopId}/spus/{id}/groupons")
    public Object insertGroupon(
            @PathVariable Long shopId,
            @PathVariable Long id,
            @Validated @RequestBody NewGrouponVo vo,
            BindingResult bindingResult) {
        logger.debug("insert insertGroupon by shopId:" + shopId + " and spuId: " + id + " and GrouponVo: " + vo.toString());
        if (bindingResult.hasErrors()) {
            return Common.processFieldErrors(bindingResult, httpServletResponse);
        }
        ReturnObject returnObject = grouponService.createNewGroupon(vo, shopId, id);
        if (returnObject.getCode() == ResponseCode.OK) {
            return ResponseUtil.ok(returnObject.getData());
        } else return ResponseUtil.fail(returnObject.getCode());
    }

    @PutMapping("/shops/{shopId}/groupons/{id}")
    public Object updateGroupon(@PathVariable Long shopId,
                                @PathVariable Long id,
                                @Validated @RequestBody NewGrouponVo vo,
                                BindingResult bindingResult) {
        //校验前端数据
        Object returnObject = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject) {
            return returnObject;
        }
        ReturnObject retObject = grouponService.updateGroupon(vo, shopId, id);
        if (retObject.getData() != null) {
            return Common.getRetObject(retObject);
        } else {
            return Common.getNullRetObj(new ReturnObject<>(retObject.getCode(), retObject.getErrmsg()), httpServletResponse);
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
    @DeleteMapping("/shops/{shopId}/groupons/{id}")
    public Object deleteGroupon(@PathVariable Long id, @PathVariable Long shopId) {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteUser: id = " + id);
        }
        ReturnObject returnObject = grouponService.deleteGroupon(shopId, id);
        return Common.decorateReturnObject(returnObject);
    }
}
