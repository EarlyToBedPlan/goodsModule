package cn.xmu.edu.goods.controller;

import cn.edu.xmu.goods.GoodsServiceApplication;
import cn.edu.xmu.goods.model.vo.GoodsSkuPostVo;
import cn.edu.xmu.goods.model.vo.GoodsSpuPostVo;
import cn.edu.xmu.ooad.util.JacksonUtil;
import cn.edu.xmu.ooad.util.JwtHelper;
import com.alibaba.fastjson.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/8 8:59
 * modifiedBy Yancheng Lai 8:59
 **/
@SpringBootTest(classes = GoodsServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class postputskusputest {
    @Autowired
    private MockMvc mvc;

    private static final Logger logger = LoggerFactory.getLogger(postputskusputest.class);

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }

    @Test
    public void postSkuTest1() throws Exception {
        GoodsSkuPostVo goodsSkuPostVo = new GoodsSkuPostVo();
        goodsSkuPostVo.setDetail("Detail set");
        goodsSkuPostVo.setConfiguration("conf");
        goodsSkuPostVo.setName("name");
        goodsSkuPostVo.setInventory(114514);
        goodsSkuPostVo.setOriginalPrice(114514l);
        goodsSkuPostVo.setSn("s_114514");
        goodsSkuPostVo.setWeight(80l);
        String token = creatTestToken(1L, 2L, 100);
        String brandJson = JacksonUtil.toJson(goodsSkuPostVo);
        String expectedResponse = "";
        String responseString = null;
        try {
            responseString = this.mvc.perform(post("/goods/shops/2/spus/300/skus").header("authorization", token).contentType("application/json;charset=UTF-8").content(brandJson))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType("application/json;charset=UTF-8"))
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        try {
            JSONAssert.assertEquals(expectedResponse, responseString, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void putSkuTest1() throws Exception {
        GoodsSkuPostVo goodsSkuPostVo = new GoodsSkuPostVo();
        goodsSkuPostVo.setDetail("Detail set");
        goodsSkuPostVo.setConfiguration("conf");
        goodsSkuPostVo.setName("name");
        goodsSkuPostVo.setInventory(114514);
        goodsSkuPostVo.setOriginalPrice(114514l);
        goodsSkuPostVo.setSn("s_114514");
        goodsSkuPostVo.setWeight(80l);
        String token = creatTestToken(1L, 2L, 100);
        String brandJson = JacksonUtil.toJson(goodsSkuPostVo);
        String expectedResponse = "";
        String responseString = null;
        try {
            responseString = this.mvc.perform(put("/goods/shops/2/skus/300")
                    .header("authorization", token).contentType("application/json;charset=UTF-8").content(brandJson))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json;charset=UTF-8"))
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        try {
            JSONAssert.assertEquals(expectedResponse, responseString, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String responseString1 = this.mvc.perform(get("/goods/skus/300"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse1 = "{\"sn\":\"s_114514\",\"name\":\"name\",\"originalPrice\":114514,\"configuration\":\"conf\",\"weight\":80,\"imageUrl\":null,\"inventory\":114514,\"detail\":\"Detail set\"}\n";
        JSONAssert.assertEquals(responseString1,expectedResponse1, true);




    }

    @Test
    public void postSpuTest1() throws Exception {
        GoodsSpuPostVo goodsSpuPostVo = new GoodsSpuPostVo();
        goodsSpuPostVo.setDescription("DETAIL");
        goodsSpuPostVo.setName("NAME");
        goodsSpuPostVo.setSpecs("{\"id\":0,\"name\":\"string\",\"specItems\":[{\"id\":114514,\"name\":\"string\"},{\"id\":1,\"name\":\"String\"}]}");
        String token = creatTestToken(1L, 2L, 100);
        String brandJson = JacksonUtil.toJson(goodsSpuPostVo);
        String expectedResponse = "";
        String responseString = null;
        try {
            responseString = this.mvc.perform(post("/goods/shops/2/spus").header("authorization", token).contentType("application/json;charset=UTF-8").content(brandJson))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType("application/json;charset=UTF-8"))
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        try {
            JSONAssert.assertEquals(expectedResponse, responseString, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Test
    public void putSpuTest1() throws Exception {
        GoodsSpuPostVo goodsSpuPostVo = new GoodsSpuPostVo();
        goodsSpuPostVo.setDescription("DETAIL");
        goodsSpuPostVo.setName("NAME");
        goodsSpuPostVo.setSpecs("{\"id\":0,\"name\":\"string\",\"specItems\":[{\"id\":114514,\"name\":\"string\"},{\"id\":1,\"name\":\"String\"}]}");
        String token = creatTestToken(1L, 0L, 100);
        String brandJson = JacksonUtil.toJson(goodsSpuPostVo);
        String expectedResponse = "";
        String responseString = null;
        responseString = this.mvc.perform(put("/goods/shops/0/spus/300").header("authorization", token).contentType("application/json;charset=UTF-8").content(brandJson))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json;charset=UTF-8"))
                    .andReturn().getResponse().getContentAsString();

        expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        try {
            JSONAssert.assertEquals(expectedResponse, responseString, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String responseString1 = this.mvc.perform(get("/goods/spus/300"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse1 = "";
        JSONAssert.assertEquals(responseString1,expectedResponse1, true);


    }


}
