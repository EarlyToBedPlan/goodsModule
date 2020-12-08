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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Feiyan Liu
 * @date Created at 2020/12/3 10:32
 */
@SpringBootTest(classes = CouponServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DeleteCouponSkuTest {
    @Autowired
    private MockMvc mvc;

    private static final Logger logger = LoggerFactory.getLogger(CouponServiceApplication.class);

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }
//
//    /**
//     * @description:删除优惠活动sku couponSku ID不存在 失败
//     * @author: Feiyan Liu
//     * @date: Created at 2020/12/3 11:58
//     */
//
//    @Test
//    public void deleteCouponActivity1() throws Exception {
//        String token=creatTestToken(1L, 0L, 100);
//
//        String responseString = this.mvc.perform(delete("/coupon/shops/0/couponactivities/273/skus").header("authorization",token)
//                .contentType("application/json;charset=UTF-8"))
//                .andExpect(status().isNotFound())
//                .andReturn().getResponse().getContentAsString();
//        String expectedResponse="{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
//        JSONAssert.assertEquals(expectedResponse, responseString, true);
//    }
    /**
     * @description:删除优惠活动sku 成功
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 11:58
     */

    @Test
    public void deleteCouponActivity2() throws Exception {
        String token=creatTestToken(1L, 0L, 100);

        String responseString = this.mvc.perform(delete("/coupon/shops/0/couponactivities/1/skus").header("authorization",token)
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    /**
     * @description:删除非本店的优惠商品
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 11:58
     */
    @Test
    public void deleteCouponActivity3() throws Exception {
        String token=creatTestToken(1L, 1L, 100);

        String responseString = this.mvc.perform(delete("/coupon/shops/1/couponactivities/1").header("authorization",token)
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":505,\"errmsg\":\"无权限删除此店的优惠活动\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
}
