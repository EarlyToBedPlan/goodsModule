package cn.edu.xmu.coupon.controller;

import cn.edu.xmu.coupon.model.bo.CouponActivity;
import cn.edu.xmu.coupon.model.vo.CouponActivityVo;
import cn.edu.xmu.coupon.model.vo.CouponSpuVo;
import cn.edu.xmu.coupon.service.CouponActivityService;
import cn.edu.xmu.ooad.annotation.Audit;
import cn.edu.xmu.ooad.annotation.Depart;
import cn.edu.xmu.ooad.annotation.LoginUser;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ResponseUtil;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageHelper;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/29 11:59
 */
@Api(value = "优惠服务", tags = "coupon")
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/coupon", produces = "application/json;charset=UTF-8")
public class CouponController {
    private static final Logger logger = LoggerFactory.getLogger(CouponController.class);
    @Autowired
    private CouponActivityService couponActivityService;
    @Autowired
    private HttpServletResponse httpServletResponse;

    @ApiOperation(value = "查看优惠活动详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "优惠商品id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @Audit
    @GetMapping("/shops/{shopId}/couponactivities/{id}")
    public Object getCouponActivity(@PathVariable("shopId") Long shopId, @PathVariable("id") Long id, @Depart Long departId) {
        if (shopId.equals(departId)) {
            ReturnObject returnObject = couponActivityService.getCouponActivityById(id);
            if (returnObject.getData() != null) {
                return ResponseUtil.ok(returnObject.getData());
            } else {
                return Common.getNullRetObj(new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg()), httpServletResponse);
            }
        } else {
            return Common.getNullRetObj(new ReturnObject<>(ResponseCode.FIELD_NOTVALID, String.format("departId不匹配")), httpServletResponse);
        }
    }

    @ApiOperation(value = "管理员新建己方优惠活动")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "优惠商品id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(paramType = "body", dataType = "CouponActivityVo", name = "vo", value = "优惠活动信息", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @Audit
    @PostMapping("/shops/{shopId}/spus/{id}/couponactivities")
    public Object addCouponActivity(@Validated @RequestBody CouponActivityVo vo, BindingResult bindingResult,
                                    @PathVariable Long id, @PathVariable Long shopId) {
        Object errors = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != errors) {
            return errors;
        }
        CouponActivity couponActivity = vo.createCouponActivity();
        //couponActivity.setCreatorId(userId);
        ReturnObject returnObject = couponActivityService.createCouponActivity(shopId, id, couponActivity);
        if (returnObject.getData() != null) {
            return ResponseUtil.ok(returnObject.getData());
        } else {
            return Common.getNullRetObj(new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg()), httpServletResponse);
        }
    }

    /**
     * @description:查看上线的列表 可以根据shopId查看 也可以指定状态查看
     * @author: Feiyan Liu
     * @date: Created at 2020/12/2 15:08
     */
    @ApiOperation(value = "查看上线的优惠活动列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "timeline", value = "活动时间", required = false, dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @GetMapping("/couponactivities")
    public Object getOnlineCouponActivity(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize,
                                          @RequestParam(required = false) Long shopId, @RequestParam(required = false) Integer state) {
        page = (page == null) ? 1 : page;
        pageSize = (pageSize == null) ? 10 : pageSize;
        ReturnObject<PageInfo<VoObject>> returnObject = couponActivityService.getCouponActivities(shopId, state, page, pageSize);
        return ResponseUtil.ok(returnObject.getData());
    }

//    @ApiOperation(value = "查看本店下线的优惠活动列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
//            @ApiImplicitParam(name = "id", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
//    })
//    @ApiResponses({
//            @ApiResponse(code = 0, message = "成功"),
//            @ApiResponse(code = 504, message = "操作id不存在")
//    })
//    @Audit
//    @GetMapping("/shops/{id}/couponactivities/invalid")
//    public Object getOfflineCouponActivity(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize,
//                                           @PathVariable Long id, @Depart Long departId) {
//        page = (page == null) ? 1 : page;
//        if (id.equals(departId)) {
//            pageSize = (pageSize == null) ? 10 : pageSize;
//            List<VoObject> returnObject = couponActivityService.getInvalidCouponActivities(page, pageSize, id);
//            PageHelper.startPage(page, pageSize);
//            return ResponseUtil.ok(new ReturnObject(getPageInfoReturnObject(new PageInfo(returnObject))).getData());
//        } else {
//            return Common.getNullRetObj(new ReturnObject<>(ResponseCode.FIELD_NOTVALID, String.format("departId不匹配")), httpServletResponse);
//        }
//    }

    @ApiOperation(value = "查看优惠活动中的商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @GetMapping("/couponactivities/{id}/spus")
    public Object getCouponSpu(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize,
                               @PathVariable Long id) {
        page = (page == null) ? 1 : page;
        pageSize = (pageSize == null) ? 10 : pageSize;
        ReturnObject<PageInfo<VoObject>> returnObject = couponActivityService.getCouponSpu(id, page, pageSize);
        if (returnObject.getData() != null)
            return ResponseUtil.ok(returnObject.getData());
        else {
            return Common.getNullRetObj(new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg()), httpServletResponse);
        }
    }

    @ApiOperation(value = "管理员修改己方优惠活动")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "优惠商品id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @Audit
    @PutMapping("/shops/{shopId}/couponactivities/{id}")
    public Object updateCouponActivity(@PathVariable Long shopId, @PathVariable Long id, @Depart Long departId,
                                       @Validated @RequestBody CouponActivityVo vo, BindingResult bindingResult, @LoginUser Long userId) {
        Object errors = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != errors) {
            return errors;
        }
        CouponActivity couponActivity = vo.createCouponActivity();
        couponActivity.setId(id);
        couponActivity.setShopId(shopId);
        couponActivity.setGmtModified(LocalDateTime.now());
        //couponActivity.setModifiedBy(userId);
        ReturnObject returnObject = couponActivityService.updateCouponActivity(couponActivity);
        if (returnObject.getData() != null) {
            return ResponseUtil.ok(returnObject.getData());
        } else {
            return Common.getNullRetObj(new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg()), httpServletResponse);
        }
    }

    @ApiOperation(value = "管理员下线己方优惠活动")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "优惠活动id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @Audit
    @DeleteMapping("/shops/{shopId}/couponactivities/{id}")
    public Object updateCouponActivity(@PathVariable Long shopId, @PathVariable Long id, @Depart Long departId) {
        if (shopId.equals(departId)) {
            ReturnObject returnObject = couponActivityService.deleteCouponActivity(id);
            if (returnObject.getData() != null) {
                return Common.getRetObject(returnObject);
            } else {
                return Common.getNullRetObj(new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg()), httpServletResponse);
            }
        } else {
            return Common.getNullRetObj(new ReturnObject<>(ResponseCode.FIELD_NOTVALID, String.format("departId不匹配")), httpServletResponse);
        }
    }

    @ApiOperation(value = "管理员为己方优惠活动新增限定范围")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "优惠活动id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @Audit
    @PostMapping("/shops/{shopId}/couponactivities/{id}/spus")
    public Object addCouponSpu(@PathVariable Long shopId, @PathVariable Long id, @Depart Long departId,
                               @Validated @RequestBody CouponSpuVo vo, BindingResult bindingResult,
                               @LoginUser Long userId) {
        Object errors = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != errors) {
            return errors;
        }
        if (shopId.equals(departId)) {
            ReturnObject returnObject = couponActivityService.addCouponSpu(shopId, vo.getId(), id);
            if (returnObject.getData() != null) {
                return Common.getRetObject(returnObject);
            } else {
                return Common.getNullRetObj(new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg()), httpServletResponse);
            }
        } else {
            return Common.getNullRetObj(new ReturnObject<>(ResponseCode.FIELD_NOTVALID, String.format("部门id不匹配：" + shopId)), httpServletResponse);
        }
    }

    @ApiOperation(value = "管理员删除己方优惠活动的某限定范围")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "优惠活动id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @Audit
    @DeleteMapping("/shops/{shopId}/couponactivities/{id}/spus")
    public Object deleteCouponSpu(@PathVariable Long shopId, @PathVariable Long id, @Depart Long departId) {
        if (shopId.equals(departId)) {
            ReturnObject returnObject = couponActivityService.deleteCouponSpu(id);
            if (returnObject.getData() != null) {
                return Common.getRetObject(returnObject);
            } else {
                return Common.getNullRetObj(new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg()), httpServletResponse);
            }
        } else {
            return Common.getNullRetObj(new ReturnObject<>(ResponseCode.FIELD_NOTVALID, String.format("departId不匹配")), httpServletResponse);
        }
    }

    @ApiOperation(value = "买家查看优惠券列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "优惠券状态", value = "state", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @Audit
    @GetMapping("/coupons")
    public Object getCouponByUserId(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer state,
                                    @LoginUser Long id) {
        page = (page == null) ? 1 : page;
        pageSize = (pageSize == null) ? 10 : pageSize;
        ReturnObject<PageInfo<VoObject>> returnObject = couponActivityService.getCouponByUserId(id, state, page, pageSize);
        if (returnObject.getData() != null)
            return ResponseUtil.ok(returnObject.getData());
        else {
            return Common.getNullRetObj(new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg()), httpServletResponse);
        }
    }
}
