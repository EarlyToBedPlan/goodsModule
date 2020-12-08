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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/7 18:09
 * modifiedBy Yancheng Lai 18:09
 **/
@SpringBootTest(classes = GoodsServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GoodsServiceControllerTest2 {
    @Autowired
    private MockMvc mvc;

    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceControllerTest2.class);

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }

    //404
    @Test
    public void getSpuByIdTest1() throws Exception{
        //String token = this.login("13088admin", "123456");
        logger.debug("check for spu1000007");
        String responseString = this.mvc.perform(get("/goods/spus/1000007"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"errmsg\":\"该ID对应的SPU不存在\"}";
        JSONAssert.assertEquals(responseString,expectedResponse, true);
    }

    //404
    @Test
    public void getSpuByIdTest2() throws Exception{
        //String token = this.login("13088admin", "123456");
        logger.debug("check for spu300");
        String responseString = this.mvc.perform(get("/goods/spus/300"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":\"0\",\"errmsg\":\"成功\",\"data\":{\"id\":300,\"name\":\"金和汇景•赵紫云•粉彩绣球瓷瓶\",\"brand\":{\"id\":86,\"name\":\"赵紫云\",\"imageUrl\":null},\"category\":{\"id\":123,\"name\":\"大师原作\"},\"freightId\":null,\"shop\":null,\"goodsSn\":\"zzy-d0001\",\"detail\":null,\"imageUrl\":\"http://47.52.88.176/file/images/201612/file_58621fe110292.jpg\",\"spec\":null,\"sku\":[{\"id\":300,\"skuSn\":null,\"name\":\"+\",\"originalPrice\":68000,\"price\":null,\"imageUrl\":\"http://47.52.88.176/file/images/201612/file_58621fe110292.jpg\",\"inventory\":1,\"disabled\":4}],\"disabled\":null,\"gmtCreate\":\"2020-12-07T11:24:46\",\"gmtModified\":\"2020-12-07T11:24:46\"}}\n";
        JSONAssert.assertEquals(responseString,expectedResponse, true);
    }

    //share
    @Test
    public void getSpuByIdTest3() throws Exception{
        String token = creatTestToken((long)1,(long)2, 100);
        logger.debug("check for spu300");
        String responseString = this.mvc.perform(get("/goods/share/1/skus/300").header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":\"0\",\"errmsg\":\"成功\",\"data\":{\"id\":300,\"name\":\"+\",\"skuSn\":null,\"detail\":null,\"imageUrl\":\"http://47.52.88.176/file/images/201612/file_58621fe110292.jpg\",\"originalPrice\":68000,\"price\":null,\"inventory\":1,\"configuration\":null,\"weight\":20,\"gmtCreate\":\"2020-12-07T11:24:46\",\"gmtModified\":\"2020-12-07T11:24:46\",\"goodsSpu\":{\"id\":300,\"name\":\"金和汇景•赵紫云•粉彩绣球瓷瓶\",\"brand\":null,\"category\":null,\"freightId\":null,\"shop\":null,\"goodsSn\":\"zzy-d0001\",\"detail\":null,\"imageUrl\":\"http://47.52.88.176/file/images/201612/file_58621fe110292.jpg\",\"spec\":null,\"sku\":null,\"disabled\":null,\"gmtCreate\":\"2020-12-07T11:24:46\",\"gmtModified\":\"2020-12-07T11:24:46\"},\"disabled\":4}}\n";
                JSONAssert.assertEquals(responseString,expectedResponse, true);
    }

}
