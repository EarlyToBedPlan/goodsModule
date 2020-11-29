package cn.edu.xmu.coupon.controller;

import cn.edu.xmu.coupon.service.CouponActivityService;
import cn.edu.xmu.ooad.annotation.Audit;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/29 11:59
 */
@Api(value = "优惠服务", tags = "coupon")
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/coupon",produces = "application/json;charset=UTF-8")
public class CouponController {
    private  static  final Logger logger = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private CouponActivityService couponActivityService;

    /**
     * @description:查看优惠活动详情
     * @param id
     * @return: java.lang.Object
     * @author: Feiyan Liu
     * @date: Created at 2020/11/29 12:18
     */
    @ApiOperation(value = "赋予用户权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name="shopId", value="店铺id", required = true, dataType="Integer", paramType="path"),
            @ApiImplicitParam(name="id", value="优惠活动id", required = true, dataType="Integer", paramType="path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @Audit
    @GetMapping("/shops/{shopId}/couponactivities/{id}")
    public Object getCouponActivity(@PathVariable Long shopId,@PathVariable Long id)
    {

        ReturnObject<VoObject> returnObject =  couponActivityService.getCouponActivityById(shopId,id);
        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.getRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }




}
