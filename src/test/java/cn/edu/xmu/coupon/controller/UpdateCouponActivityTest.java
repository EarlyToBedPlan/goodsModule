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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Feiyan Liu
 * @date Created at 2020/12/3 10:32
 */
@SpringBootTest(classes = CouponServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UpdateCouponActivityTest {
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
     * @description:修改优惠活动 成功
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 11:58
     */

    @Test
    public void addCouponActivity1() throws Exception {
        String token=creatTestToken(1L, 1L, 100);
        CouponActivityVo vo=new CouponActivityVo();
        vo.setName("618大促");
        vo.setQuantity(1);
        vo.setQuantityType((byte) 0);
        vo.setStrategy("1");
        vo.setValidTerm((byte) 0);

        String requireJson = JacksonUtil.toJson(vo);

        String responseString = this.mvc.perform(put("/coupon/shops/1/couponactivities/1").header("authorization",token)
                .contentType("application/json;charset=UTF-8")
                .content(requireJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
       String expectedResponse="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    /**
     * @description:修改非本店的优惠活动
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 11:58
     */
    @Test
    public void addCouponActivity2() throws Exception {
        String token=creatTestToken(1L, 2L, 100);
        CouponActivityVo vo=new CouponActivityVo();
        vo.setName("618大促");
        vo.setQuantity(1);
        vo.setQuantityType((byte) 0);
        vo.setStrategy("1");
        vo.setValidTerm((byte) 0);

        String requireJson = JacksonUtil.toJson(vo);

        String responseString = this.mvc.perform(put("/coupon/shops/2/couponactivities/1").header("authorization",token)
                .contentType("application/json;charset=UTF-8")
                .content(requireJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse= "{\"errno\":505,\"errmsg\":\"无权限修改此店的优惠活动\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
}
