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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author: Feiyan Liu
 * @date: Created at 2020/12/3 15:04
 */

@SpringBootTest(classes = CouponServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GetCouponSpuTest {
    @Autowired
    private MockMvc mvc;

    private static final Logger logger = LoggerFactory.getLogger(CouponServiceApplication.class);

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }


    /**
     * @description: 查看特定优惠活动的商品列表 成功
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 12:15
     */
    @Test
    public void getCouponSpu1() throws Exception {
    String responseString = this.mvc.perform(get("/coupon/couponactivities/1/spus"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andReturn().getResponse().getContentAsString();

    String expectedResponse= "{\"errno\":0,\"data\":{\"total\":1,\"list\":[{\"id\":1,\"name\":null}],\"pageNum\":1,\"pageSize\":1,\"size\":1,\"startRow\":0,\"endRow\":0,\"pages\":1,\"prePage\":0,\"nextPage\":0,\"isFirstPage\":true,\"isLastPage\":true,\"hasPreviousPage\":false,\"hasNextPage\":false,\"navigatePages\":8,\"navigatepageNums\":[1],\"navigateFirstPage\":1,\"navigateLastPage\":1},\"errmsg\":\"成功\"}";
    JSONAssert.assertEquals(expectedResponse, responseString, true);
}
    /**
     * @description:查看的活动id不存在
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 15:30
     */
    @Test
    public void getCouponSpu2() throws Exception {
        String responseString = this.mvc.perform(get("/coupon/couponactivities/100/spus"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

}
