package cn.xmu.edu.goods.controller;

import cn.edu.xmu.goods.GoodsServiceApplication;
import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
import cn.edu.xmu.ooad.util.JacksonUtil;
import cn.edu.xmu.ooad.util.JwtHelper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/7 23:46
 * modifiedBy Yancheng Lai 23:46
 **/
@SpringBootTest(classes = GoodsServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GoodsServiceControllerTest3 {
    @Autowired
    private MockMvc mvc;

    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceControllerTest3.class);

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }
//    @Test
//    public void postSkuIntoSpuTest3() throws Exception {
//        String token=creatTestToken(1L, 0L, 100);
//        GoodsSkuRetVo vo=new GoodsSkuRetVo();
//        vo.setName("-");
//        vo.setOriginalPrice(114514L);
//        vo.setWeight( 200l);
//        vo.setDetail("附加sku");
//
//        String requireJson = JacksonUtil.toJson(vo);
//        logger.info(requireJson);
//        String responseString = this.mvc.perform(post("/goods/shops/0/spus/300/skus")
//                //.header("authorization",token)
//                .contentType("application/json;charset=UTF-8")
//                .content(requireJson))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse().getContentAsString();
//
//        String expectedResponse= "{\"errno\":505,\"errmsg\":\"创建优惠活动失败，商品非用户店铺的商品\"}";
//        JSONAssert.assertEquals(expectedResponse, responseString, true);
//    }
}
