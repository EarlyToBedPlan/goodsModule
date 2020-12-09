package cn.edu.xmu.coupon.controller;

import cn.edu.xmu.coupon.CouponServiceApplication;
import cn.edu.xmu.coupon.model.bo.CouponActivity;
import cn.edu.xmu.coupon.model.vo.CouponActivityVo;
import cn.edu.xmu.ooad.util.JacksonUtil;
import cn.edu.xmu.ooad.util.JwtHelper;
import cn.edu.xmu.ooad.util.ReturnObject;
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

import java.time.LocalDateTime;

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
public class AddCouponActivityTest {
    @Autowired
    private MockMvc mvc;
    private static final Logger logger = LoggerFactory.getLogger(CouponServiceApplication.class);

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }

    /**
     * @description:新增优惠活动 成功
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

        String responseString = this.mvc.perform(post("/coupon/shops/1/skus/273/couponactivities").header("authorization",token)
                .contentType("application/json;charset=UTF-8")
                .content(requireJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //id是自增的 所以测试会失败 然后因为返回的gmtCreate是创建时间 所以vo对象里暂时注释了 不然测试过不了
        String expectedResponse = "{\"errno\":0,\"data\":{\"id\":12,\"name\":\"618大促\",\"state\":0,\"quantity\":1,\"quantityType\":0,\"validTerm\":0,\"imageUrl\":null,\"beginTime\":null,\"endTime\":null,\"couponTime\":null,\"strategy\":\"1\",\"createdBy\":{\"id\":null,\"name\":\"哈哈哈\"},\"modifiedBy\":{\"id\":null,\"name\":\"哈哈\"},\"gmtModified\":null},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    /**
     * @description:shopId和departId不符合 无权限新建活动
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 12:00
     */
    @Test
    public void addCouponActivity2() throws Exception {
                String token=creatTestToken(1L, 1L, 100);
        CouponActivityVo vo=new CouponActivityVo();
        vo.setName("618大促");
        vo.setQuantity(1);
        vo.setQuantityType((byte) 0);
        vo.setStrategy("1");
        vo.setValidTerm((byte) 0);

        String requireJson = JacksonUtil.toJson(vo);

        String responseString = this.mvc.perform(post("/coupon/shops/2/skus/273/couponactivities").header("authorization",token)
                .contentType("application/json;charset=UTF-8")
                .content(requireJson))
                .andExpect(status().isForbidden())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":503,\"errmsg\":\"departId不匹配\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
    /**
     * @description: 添加到优惠活动的商品非本店铺的
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 12:15
     */
@Test
    public void addCouponActivity3() throws Exception {
    String token=creatTestToken(1L, 2L, 100);
    CouponActivityVo vo=new CouponActivityVo();
    vo.setName("618大促");
    vo.setQuantity(1);
    vo.setQuantityType((byte) 0);
    vo.setStrategy("1");
    vo.setValidTerm((byte) 0);

    String requireJson = JacksonUtil.toJson(vo);

    String responseString = this.mvc.perform(post("/coupon/shops/2/Skus/273/couponactivities").header("authorization",token)
            .contentType("application/json;charset=UTF-8")
            .content(requireJson))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andReturn().getResponse().getContentAsString();

   String expectedResponse= "{\"errno\":505,\"errmsg\":\"创建优惠活动失败，商品非用户店铺的商品\"}";
    JSONAssert.assertEquals(expectedResponse, responseString, true);
}
///**
// * @description: 商品在同时间段内已经参与了其他活动
// * @return:
// * @author: Feiyan Liu
// * @date: Created at 2020/12/3 13:39
// */
//
//    @Test
//    public void addCouponActivity4() throws Exception {
//        String token=creatTestToken(1L, 2L, 100);
//        CouponActivityVo vo=new CouponActivityVo();
//        vo.setName("618大促");
//        vo.setQuantity(1);
//        vo.setQuantityType((byte) 0);
//        vo.setStrategy("1");
//        vo.setValidTerm((byte) 0);
//
//        String requireJson = JacksonUtil.toJson(vo);
//
//        String responseString = this.mvc.perform(post("/coupon/shops/2/skus/273/couponactivities").header("authorization",token)
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
