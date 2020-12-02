package cn.edu.xmu.goods.controller;

import cn.edu.xmu.goods.model.vo.GoodsSpuRetVo;
import cn.edu.xmu.goods.service.GoodsSkuService;
import cn.edu.xmu.goods.service.GoodsSpuService;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品服务控制器
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 18:40
 * modifiedBy Yancheng Lai 18:40
 **/

@Api(value = "商品服务", tags = "goods")
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")


public class GoodsServiceController {

    private  static  final Logger logger = LoggerFactory.getLogger(GoodsServiceController.class);

    @Autowired
    GoodsSpuService goodsSpuService;

    @Autowired
    GoodsSkuService goodsSkuService;
    /** 
    * @Description: 获取指定id的spu
    * @Param: [id]
    * @return: java.lang.Object 
    * @Author: Yancheng Lai
    * @Date: 2020/12/2 18:45
    */
    @ApiOperation(value = "用id获得某一SPU")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", required = true, dataType="String", paramType="path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @GetMapping("/spus/{id}")
    public Object getSpuBySpuId(@PathVariable Long id){
        ReturnObject<GoodsSpuRetVo> returnObject =  goodsSpuService.findSpuById(id);
        if (returnObject.getCode() == ResponseCode.OK) {
            return returnObject;
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }


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
            @ApiResponse(code = 504, message = "操作对象不存在")
    })
    @GetMapping("/skus")
    public Object getSku(
            @RequestParam("ShopId") Integer shopId, @RequestParam("skuSn")String skuSn,
            @RequestParam("spuId") Integer spuId, @RequestParam("spuSn")String spuSn,
            @RequestParam("page") Integer page, @RequestParam("skuSn")Integer pageSize
    ){
            return Common.decorateReturnObject(new ReturnObject());
    }
}
