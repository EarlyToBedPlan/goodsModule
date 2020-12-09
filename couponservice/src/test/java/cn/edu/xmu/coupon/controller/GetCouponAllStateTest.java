package cn.edu.xmu.coupon.controller;

import cn.edu.xmu.coupon.CouponServiceApplication;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Feiyan Liu
 * @date Created at 2020/12/9 10:31
 */
@SpringBootTest(classes = CouponServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GetCouponAllStateTest {
    @Autowired
    private MockMvc mvc;

    private static final Logger logger = LoggerFactory.getLogger(CouponServiceApplication.class);

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }
    /**
     * @description:成功读取活动详情
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 0:23
     */
    @Test
    public void getCouponAllState1() throws Exception {
        String token=creatTestToken(1L, 0L, 100);
        String responseString = this.mvc.perform(get("/coupon/coupons/states").header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":0,\"data\":[{\"code\":0,\"name\":\"未领取\"},{\"code\":1,\"name\":\"已领取\"},{\"code\":2,\"name\":\"已使用\"},{\"code\":3,\"name\":\"失效\"}],\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
}
