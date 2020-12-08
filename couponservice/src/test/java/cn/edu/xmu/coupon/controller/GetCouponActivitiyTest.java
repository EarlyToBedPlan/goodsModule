package cn.edu.xmu.coupon.controller;

import cn.edu.xmu.coupon.CouponServiceApplication;
import cn.edu.xmu.ooad.util.JwtHelper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/29 12:24
 */
@SpringBootTest(classes = CouponServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GetCouponActivitiyTest {
    @Autowired
    private MockMvc mvc;

    private static final Logger logger = LoggerFactory.getLogger(CouponServiceApplication.class);

    /**
     * @description:成功读取活动详情
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 0:23
     */
    @Test
    public void getCouponActivityById1() throws Exception {
       String token=creatTestToken(1L, 0L, 100);
        String responseString = this.mvc.perform(get("/coupon/shops/0/couponactivities/1").header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"data\":{\"id\":1,\"name\":\"年终大促\",\"state\":1,\"quantity\":0,\"quantityType\":1,\"validTerm\":0,\"imageUrl\":\"img\",\"beginTime\":\"2020-12-02T17:36:52\",\"endTime\":\"2020-12-05T17:36:52\",\"couponTime\":\"2020-12-05T21:06:53\",\"strategy\":\"1\",\"createdBy\":{\"id\":1,\"name\":\"哈哈哈\"},\"modifiedBy\":{\"id\":1,\"name\":\"哈哈\"},\"gmtModified\":null},\"errmsg\":\"成功\"}";
        //String expectedResponse="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
    /**
     * @description:活动id不存在 读取活动详情失败
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 0:23
     */
    @Test
    public void getCouponActivityById2() throws Exception {
        String token=creatTestToken(1L, 0L, 100);
        String responseString = this.mvc.perform(get("/coupon/shops/0/couponactivities/100").header("authorization",token))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);

    }
    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }
    /**
     * @description:shopId和departId不符合 无权限读取活动详情
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 0:23
     */
    @Test
    public void getCouponActivityById3() throws Exception {
        String token=creatTestToken(1L, 11L, 100);
        String responseString = this.mvc.perform(get("/coupon/shops/22/couponactivities/10").header("authorization",token))
                .andExpect(status().isForbidden())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":503,\"errmsg\":\"departId不匹配\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

}
