package cn.edu.xmu.shop.controller;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
* @author Ruzhen Chang
 *
 */

@Api(value = "店铺",tags = "shop")
@RestController
@RequestMapping(value = "/shop",produces = "application/json;charset=UTF-8")

public class ShopController {
    private static final Logger logger =LoggerFactory.getLogger(ShopController.class);


}
