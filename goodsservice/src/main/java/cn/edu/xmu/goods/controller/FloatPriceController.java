package cn.edu.xmu.goods.controller;

import cn.edu.xmu.goods.model.vo.FloatPriceRetVo;
import cn.edu.xmu.goods.model.vo.FloatPriceVo;
import cn.edu.xmu.goods.service.FloatPriceServiceImpl;
import cn.edu.xmu.ooad.annotation.Audit;
import cn.edu.xmu.ooad.annotation.LoginUser;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ReturnObject;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author：谢沛辰
 * @Date: 2020.12.01
 * @Description:价格浮动控制器
 */
@RestController
@RequestMapping(value="/shops",produces = "application/json;charset+UTF-8")
public class FloatPriceController {
    private  static  final Logger logger = LoggerFactory.getLogger(FloatPriceController.class);

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    FloatPriceServiceImpl floatPriceService;

    /**
     * @Author：谢沛辰
     * @Date: 2020/12/8
      * @Param: null
     * @Return:
     * @Description:删除价格浮动
     */
    @ApiOperation(value = "失效价格浮动")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization",required = true,dataType = "String",paramType = "header"),
            @ApiImplicitParam(name="shopId",required = true,dataType = "int",paramType = "path"),
            @ApiImplicitParam(name="id",required = true,dataType = "int",paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=504,message = "操作id不存在")
    })
    @Audit
    @DeleteMapping("/shops/{shopId}/floatPrices/{id}")
    public Object deleteFloatPrice(@LoginUser Long userId, @PathVariable("id") Long id){
        ReturnObject<Object> result = floatPriceService.logicallyDelete(userId,id);
        return Common.decorateReturnObject(result);
    }

    /**
     * @Author：谢沛辰
     * @Date: 2020/12/8
      * @Param: null
     * @Return:
     * @Description:创建价格浮动
     */
    @ApiOperation(value = "新增价格浮动")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization",required = true,dataType = "String",paramType = "header"),
            @ApiImplicitParam(name="shopId",required = true,dataType = "int",paramType = "path"),
            @ApiImplicitParam(name="id",required = true,dataType = "int",paramType = "path"),
            @ApiImplicitParam(name="floatPriceVo",required = true,dataType = "FloatPriceVo",paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
    })
    @Audit
    @PostMapping("/shops/{shopId}/skus/{id}/floatPrices")
    public Object createFloatPrice(@LoginUser Long userId, @PathVariable("id") Long id, @RequestBody FloatPriceVo floatPriceVo,BindingResult bindingResult){
        Object errors = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != errors) {
            return errors;
        }
        ReturnObject<FloatPriceRetVo> result =floatPriceService.createFloatPrice(id,floatPriceVo,userId);
        return Common.decorateReturnObject(result);
    }
}
