package cn.xmu.edu.goods.controller;

import cn.edu.xmu.goods.GoodsServiceApplication;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/11 16:27
 * modifiedBy Yancheng Lai 16:27
 **/
@SpringBootTest(classes = GoodsServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class delSkuSpuTest {
    @Autowired
    private MockMvc mvc;

    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceControllerTest4.class);

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }
    @Test
    public void deleteSkuTest1() throws Exception {
        String token=creatTestToken(1L, 0L, 100);


        String responseString = this.mvc.perform(delete("/goods/shops/0/skus/300")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);

        String responseString1 = this.mvc.perform(get("/goods/skus/300")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse1="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse1, responseString1, true);
    }

    @Test
    public void deleteSkuTest2() throws Exception {
        String token=creatTestToken(1L, 0L, 100);


        String responseString = this.mvc.perform(delete("/goods/shops/0/skus/114514")
                .header("authorization",token))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    public void deleteSkuTest3() throws Exception {
        String token=creatTestToken(1L, 2L, 100);


        String responseString = this.mvc.perform(delete("/goods/shops/2/skus/300")
                .header("authorization",token))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    public void deleteSpu1Test() throws Exception {
        String token=creatTestToken(1L, 0L, 100);


        String responseString = this.mvc.perform(delete("/goods/shops/0/spus/300")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);

        String responseString1 = this.mvc.perform(get("/goods/spus/300")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse1="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse1, responseString1, true);
    }

    @Test
    public void deleteSpu2Test() throws Exception {
        String token=creatTestToken(1L, 0L, 100);


        String responseString = this.mvc.perform(delete("/goods/shops/0/spus/114514")
                .header("authorization",token))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
    @Test
    public void deleteSpu3Test() throws Exception {
        String token=creatTestToken(1L, 2L, 100);


        String responseString = this.mvc.perform(delete("/goods/shops/2/spus/300")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
}
