package cn.edu.xmu.goods.controller;

import cn.edu.xmu.goods.service.FloatPriceService;
import cn.edu.xmu.ooad.annotation.Audit;
import cn.edu.xmu.ooad.annotation.LoginUser;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @Author：谢沛辰
 * @Date: 2020.12.01
 * @Description:价格浮动控制器
 */
@RestController
@RequestMapping(value="/shops",produces = "application/json;charset+UTF-8")
public class FloatPriceController {

    @Autowired
    FloatPriceService floatPriceService;

    @Audit
    @DeleteMapping("/shops/{shopId}/floatPrices/{id}")
    public Object deleteFloatPrice(@LoginUser Long userId, @PathVariable Long id){
        ReturnObject<Object> result = floatPriceService.logicallyDelete(userId,id);
        return result;
    }
}
