package cn.edu.xmu.coupon.controller;

import cn.edu.xmu.coupon.CouponServiceApplication;
import cn.edu.xmu.coupon.model.vo.CouponActivityVo;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Feiyan Liu
 * @date Created at 2020/12/6 21:56
 */
@SpringBootTest(classes = CouponServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UseCouponTest {
    @Autowired
    private MockMvc mvc;

    private static final Logger logger = LoggerFactory.getLogger(CouponServiceApplication.class);

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }

    /**
     * @description: 优惠券非用户的优惠券 使用失败
     * @return:
     * @author: Feiyan Liu
     * @date: Created at 2020/12/6 21:57
     */
    @Test
    public void useCoupon1() throws Exception {
        String token=creatTestToken(2L, 2L, 100);

        String responseString = this.mvc.perform(put("/coupon/coupons/1").header("authorization",token)
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
    /**
     * @description: 优惠券id不存在 使用失败
     * @return:
     * @author: Feiyan Liu
     * @date: Created at 2020/12/6 21:57
     */
    @Test
    public void useCoupon2() throws Exception {
        String token=creatTestToken(2L, -1L, 100);

        String responseString = this.mvc.perform(put("/coupon/coupons/200").header("authorization",token)
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
    /**
     * @description: 使用未生效或过期优惠券 使用失败
     * @return:
     * @author: Feiyan Liu
     * @date: Created at 2020/12/6 21:57
     */
    @Test
    public void useCoupon3() throws Exception {
        String token=creatTestToken(1L, 1L, 100);

        String responseString = this.mvc.perform(put("/coupon/coupons/2").header("authorization",token)
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":905,\"errmsg\":\"优惠卷状态禁止\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
    /**
     * @description: 优惠券已被使用或已失效 使用失败
     * @return:
     * @author: Feiyan Liu
     * @date: Created at 2020/12/6 21:57
     */
    @Test
    public void useCoupon4() throws Exception {
        String token=creatTestToken(1L, 1L, 100);

        String responseString = this.mvc.perform(put("/coupon/coupons/3").header("authorization",token)
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":905,\"errmsg\":\"优惠卷状态禁止\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
    /**
     * @description: 成功
     * @return:
     * @author: Feiyan Liu
     * @date: Created at 2020/12/6 21:57
     */
    @Test
    public void useCoupon5() throws Exception {
        String token=creatTestToken(1L, 1L, 100);

        String responseString = this.mvc.perform(put("/coupon/coupons/1").header("authorization",token)
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

}
