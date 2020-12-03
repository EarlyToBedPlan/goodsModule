package cn.edu.xmu.goods.controller;

import cn.edu.xmu.goods.model.vo.FloatPriceRetVo;
import cn.edu.xmu.goods.model.vo.FloatPriceVo;
import cn.edu.xmu.goods.service.FloatPriceService;
import cn.edu.xmu.ooad.annotation.Audit;
import cn.edu.xmu.ooad.annotation.LoginUser;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    FloatPriceService floatPriceService;

    @Audit
    @DeleteMapping("/shops/{shopId}/floatPrices/{id}")
    public Object deleteFloatPrice(@LoginUser Long userId, @PathVariable Long id){
        ReturnObject<Object> result = floatPriceService.logicallyDelete(userId,id);
        return Common.decorateReturnObject(result);
    }

    @Audit
    @PostMapping("/shops/{shopId}/skus/{id}/floatPrices")
    public Object createFloatPrice(@LoginUser Long userId, @PathVariable Long id, @RequestBody FloatPriceVo floatPriceVo){
        ReturnObject<FloatPriceRetVo> result =floatPriceService.createFloatPrice(id,floatPriceVo,userId);
        return Common.decorateReturnObject(result);
    }
}
