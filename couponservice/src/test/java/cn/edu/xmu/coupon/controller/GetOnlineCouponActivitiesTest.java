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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Feiyan Liu
 * @date Created at 2020/12/3 10:32
 */
@SpringBootTest(classes = CouponServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GetOnlineCouponActivitiesTest {
    @Autowired
    private MockMvc mvc;

    private static final Logger logger = LoggerFactory.getLogger(CouponServiceApplication.class);


    /**
     * @description: 查看上线的优惠活动列表
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 12:15
     */
    @Test
    public void getCouponActivities1() throws Exception {
    String responseString = this.mvc.perform(get("/coupon/couponactivities"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andReturn().getResponse().getContentAsString();

    String expectedResponse= "{\"errno\":0,\"data\":{\"code\":\"OK\",\"errmsg\":\"成功\",\"data\":{\"total\":4,\"list\":[{\"id\":1,\"name\":\"年终大促\",\"imageUrl\":null,\"beginTime\":null,\"endTime\":null,\"quantity\":0,\"couponTime\":null},{\"id\":2,\"name\":\"618\",\"imageUrl\":null,\"beginTime\":null,\"endTime\":null,\"quantity\":0,\"couponTime\":null},{\"id\":3,\"name\":\"双十一\",\"imageUrl\":null,\"beginTime\":null,\"endTime\":null,\"quantity\":0,\"couponTime\":null},{\"id\":4,\"name\":\"开学季\",\"imageUrl\":null,\"beginTime\":null,\"endTime\":null,\"quantity\":0,\"couponTime\":null}],\"pageNum\":1,\"pageSize\":4,\"size\":4,\"startRow\":0,\"endRow\":3,\"pages\":1,\"prePage\":0,\"nextPage\":0,\"isFirstPage\":true,\"isLastPage\":true,\"hasPreviousPage\":false,\"hasNextPage\":false,\"navigatePages\":8,\"navigatepageNums\":[1],\"navigateFirstPage\":1,\"navigateLastPage\":1}},\"errmsg\":\"成功\"}";
    JSONAssert.assertEquals(expectedResponse, responseString, true);
}

}
