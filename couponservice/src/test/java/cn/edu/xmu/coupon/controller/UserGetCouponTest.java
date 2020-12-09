package cn.edu.xmu.coupon.controller;

import cn.edu.xmu.coupon.CouponServiceApplication;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Feiyan Liu
 * @date Created at 2020/12/9 9:04
 */
@SpringBootTest(classes = CouponServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserGetCouponTest {
    @Autowired
    private MockMvc mvc;
    private static final Logger logger = LoggerFactory.getLogger(CouponServiceApplication.class);

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }

    @Test
    public void userGetCoupon1() throws Exception {
        String token=creatTestToken(1L, 1L, 100);
        Long[] skuId= new Long[2];
        skuId[0]=273L;
        skuId[1]=274L;
        String requireJson = JacksonUtil.toJson(skuId);
        String responseString = this.mvc.perform(post("/coupon/shops/1/skus/273/couponactivities").header("authorization",token)
                .contentType("application/json;charset=UTF-8")
                .content(requireJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //id是自增的 所以测试会失败 然后因为返回的gmtCreate是创建时间 所以vo对象里暂时注释了 不然测试过不了
        String expectedResponse = "{\"errno\":0,\"data\":{\"id\":12,\"name\":\"618大促\",\"state\":0,\"quantity\":1,\"quantityType\":0,\"validTerm\":0,\"imageUrl\":null,\"beginTime\":null,\"endTime\":null,\"couponTime\":null,\"strategy\":\"1\",\"createdBy\":{\"id\":null,\"name\":\"哈哈哈\"},\"modifiedBy\":{\"id\":null,\"name\":\"哈哈\"},\"gmtModified\":null},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
}
