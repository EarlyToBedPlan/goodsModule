package cn.xmu.edu.goods.controller;

import cn.edu.xmu.goods.GoodsServiceApplication;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/8 0:43
 * modifiedBy Yancheng Lai 0:43
 **/

@SpringBootTest(classes = GoodsServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GoodsServiceControllerTest4 {
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
    public void deleteSpu1Test() throws Exception {
        String token=creatTestToken(1L, 0L, 100);


        String responseString = this.mvc.perform(delete("/goods/shops/0/spus/300")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
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
    public void insertGoodsBrandTest1() throws Exception {
        String token=creatTestToken(1L, 0L, 100);


        String responseString = this.mvc.perform(post("/goods/shops/0/spus/300/brands/75")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse ="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);

        String responseString2 = this.mvc.perform(get("/goods/spus/300")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse2 = "{\"errno\":0,\"data\":{\"id\":300,\"name\":\"金和汇景•赵紫云•粉彩绣球瓷瓶\",\"brand\":{\"id\":75,\"name\":\"李菊生\",\"imageUrl\":null},\"category\":{\"id\":123,\"name\":\"大师原作\"},\"freightId\":null,\"shop\":null,\"goodsSn\":\"zzy-d0001\",\"detail\":null,\"imageUrl\":\"http://47.52.88.176/file/images/201612/file_58621fe110292.jpg\",\"spec\":null,\"sku\":[{\"id\":300,\"skuSn\":null,\"name\":\"+\",\"originalPrice\":68000,\"price\":null,\"imageUrl\":\"http://47.52.88.176/file/images/201612/file_58621fe110292.jpg\",\"inventory\":1,\"disabled\":4}],\"disabled\":null,\"gmtCreate\":\"2020-12-07T11:24:46\",\"gmtModified\":\"2020-12-07T11:24:46\"},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(expectedResponse2, responseString2, true);
    }

    @Test
    public void insertGoodsBrandTest2() throws Exception {
        String token=creatTestToken(1L, 0L, 100);

        String responseString = this.mvc.perform(post("/goods/shops/0/spus/300/brands/114514")
                .header("authorization",token))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    public void insertGoodsBrandTest3() throws Exception {
        String token=creatTestToken(1L, 0L, 100);

        String responseString = this.mvc.perform(post("/goods/shops/0/spus/114514/brands/75")
                .header("authorization",token))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    public void insertGoodsBrandTest4() throws Exception {
        String token=creatTestToken(1L, 2L, 100);

        String responseString = this.mvc.perform(post("/goods/shops/2/spus/114514/brands/75")
                .header("authorization",token))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":503,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    public void deleteGoodsBrandTest1() throws Exception {
        String token=creatTestToken(1L, 0L, 100);


        String responseString = this.mvc.perform(delete("/goods/shops/0/spus/300/brands/86")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse ="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);

        String responseString2 = this.mvc.perform(get("/goods/spus/300")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse2 = "{\"errno\":0,\"data\":{\"id\":300,\"name\":\"金和汇景•赵紫云•粉彩绣球瓷瓶\",\"brand\":{\"id\":0,\"name\":\"无品牌\",\"imageUrl\":null},\"category\":{\"id\":123,\"name\":\"大师原作\"},\"freightId\":null,\"shop\":null,\"goodsSn\":\"zzy-d0001\",\"detail\":null,\"imageUrl\":\"http://47.52.88.176/file/images/201612/file_58621fe110292.jpg\",\"spec\":null,\"sku\":[{\"id\":300,\"skuSn\":null,\"name\":\"+\",\"originalPrice\":68000,\"price\":null,\"imageUrl\":\"http://47.52.88.176/file/images/201612/file_58621fe110292.jpg\",\"inventory\":1,\"disabled\":4}],\"disabled\":null,\"gmtCreate\":\"2020-12-07T11:24:46\",\"gmtModified\":\"2020-12-07T11:24:46\"},\"errmsg\":\"成功\"}\n";
                JSONAssert.assertEquals(expectedResponse2, responseString2, true);
    }

    @Test
    public void deleteGoodsBrandTest2() throws Exception {
        String token=creatTestToken(1L, 0L, 100);

        String responseString = this.mvc.perform(delete("/goods/shops/0/spus/300/brands/114514")
                .header("authorization",token))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
    @Test
    public void deleteGoodsBrandTest3() throws Exception {
        String token=creatTestToken(1L, 0L, 100);

        String responseString = this.mvc.perform(delete("/goods/shops/0/spus/114514/brands/75")
                .header("authorization",token))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
    @Test
    public void deleteGoodsBrandTest4() throws Exception {
        String token=creatTestToken(1L, 0L, 100);

        String responseString = this.mvc.perform(delete("/goods/shops/0/spus/300/brands/20")
                .header("authorization",token))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    public void updateSkusOnShelvesTest1() throws Exception{
        String token=creatTestToken(1L, 0L, 100);

        String responseString = this.mvc.perform(put("/goods/shops/0/skus/300/offshelves")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);

        String responseString2 = this.mvc.perform(get("/goods/skus/300")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse2 = "{\"errno\":0,\"data\":{\"id\":300,\"name\":\"+\",\"skuSn\":null,\"detail\":null,\"imageUrl\":\"http://47.52.88.176/file/images/201612/file_58621fe110292.jpg\",\"originalPrice\":68000,\"price\":null,\"inventory\":1,\"configuration\":null,\"weight\":20,\"gmtCreate\":\"2020-12-07T11:24:46\",\"gmtModified\":\"2020-12-07T11:24:46\",\"goodsSpu\":{\"id\":300,\"name\":\"金和汇景•赵紫云•粉彩绣球瓷瓶\",\"brand\":null,\"category\":null,\"freightId\":null,\"shop\":null,\"goodsSn\":\"zzy-d0001\",\"detail\":null,\"imageUrl\":\"http://47.52.88.176/file/images/201612/file_58621fe110292.jpg\",\"spec\":null,\"sku\":null,\"disabled\":null,\"gmtCreate\":\"2020-12-07T11:24:46\",\"gmtModified\":\"2020-12-07T11:24:46\"},\"disabled\":1},\"errmsg\":\"成功\"}\n";
                JSONAssert.assertEquals(expectedResponse2, responseString2, true);

        String responseString3 = this.mvc.perform(put("/goods/shops/0/skus/300/onshelves")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse3 ="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse3, responseString3, true);

        String responseString4 = this.mvc.perform(get("/goods/skus/300")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse4 = "{\"errno\":0,\"data\":{\"id\":300,\"name\":\"+\",\"skuSn\":null,\"detail\":null,\"imageUrl\":\"http://47.52.88.176/file/images/201612/file_58621fe110292.jpg\",\"originalPrice\":68000,\"price\":null,\"inventory\":1,\"configuration\":null,\"weight\":20,\"gmtCreate\":\"2020-12-07T11:24:46\",\"gmtModified\":\"2020-12-07T11:24:46\",\"goodsSpu\":{\"id\":300,\"name\":\"金和汇景•赵紫云•粉彩绣球瓷瓶\",\"brand\":null,\"category\":null,\"freightId\":null,\"shop\":null,\"goodsSn\":\"zzy-d0001\",\"detail\":null,\"imageUrl\":\"http://47.52.88.176/file/images/201612/file_58621fe110292.jpg\",\"spec\":null,\"sku\":null,\"disabled\":null,\"gmtCreate\":\"2020-12-07T11:24:46\",\"gmtModified\":\"2020-12-07T11:24:46\"},\"disabled\":0},\"errmsg\":\"成功\"}\n";
                JSONAssert.assertEquals(expectedResponse4, responseString4, true);
    }
    @Test
    public void updateSkusOnShelvesTest2() throws Exception{
        String token=creatTestToken(1L, 0L, 100);

        String responseString = this.mvc.perform(put("/goods/shops/0/skus/114514/offshelves")
                .header("authorization",token))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);

        String responseString3 = this.mvc.perform(put("/goods/shops/0/skus/114514/onshelves")
                .header("authorization",token))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse3 ="{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse3, responseString3, true);

    }

}
