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
 * @author Feiyan Liu
 * @date Created at 2020/12/3 10:32
 */
@SpringBootTest(classes = CouponServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GetInvalidCouponActivitiesTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;
    private static final Logger logger = LoggerFactory.getLogger(CouponServiceApplication.class);

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }

    /**
     * @description: 查看下线的优惠活动列表 departId和路径的店铺id不匹配 无权限查看
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 14:34
     */
    @Test
    public void getInvalidCouponActivities1() throws Exception {
        String token=creatTestToken(1L, 1L, 100);
    String responseString = this.mvc.perform(get("/coupon/shops/2/couponactivities/invalid").header("authorization",token))
                .andExpect(status().isForbidden())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":503,\"errmsg\":\"departId不匹配\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    /**
     * @description:成功查看
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 14:37
     */
    @Test
    public void getInvalidCouponActivities2() throws Exception {
        String token=creatTestToken(1L, 1L, 100);
        String responseString = this.mvc.perform(get("/coupon/shops/1/couponactivities/invalid").header("authorization",token))
                .andExpect(status().isForbidden())
                .andReturn().getResponse().getContentAsString();
        //这里pageInfo比api中多返回了一些数据 不知如何处理 多返回的在字符串的最后面
        String expectedResponse = "{\"errno\":0,\"data\":{\"total\":4,\"list\":[{\"id\":1,\"name\":\"年终大促\",\"imageUrl\":null,\"beginTime\":null,\"endTime\":null,\"quantity\":0,\"couponTime\":null},{\"id\":2,\"name\":\"618\",\"imageUrl\":null,\"beginTime\":null,\"endTime\":null,\"quantity\":0,\"couponTime\":null},{\"id\":3,\"name\":\"双十一\",\"imageUrl\":null,\"beginTime\":null,\"endTime\":null,\"quantity\":0,\"couponTime\":null},{\"id\":4,\"name\":\"开学季\",\"imageUrl\":null,\"beginTime\":null,\"endTime\":null,\"quantity\":0,\"couponTime\":null}],\"pageNum\":1,\"pageSize\":4,\"size\":4,\"startRow\":0,\"endRow\":3,\"pages\":1,\"prePage\":0,\"nextPage\":0,\"isFirstPage\":true,\"isLastPage\":true,\"hasPreviousPage\":false,\"hasNextPage\":false,\"navigatePages\":8,\"navigatepageNums\":[1],\"navigateFirstPage\":1,\"navigateLastPage\":1},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
}