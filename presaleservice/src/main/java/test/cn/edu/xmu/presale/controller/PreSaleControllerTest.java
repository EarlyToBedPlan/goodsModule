package test.cn.edu.xmu.presale.controller;

import cn.edu.xmu.ooad.util.JacksonUtil;
import cn.edu.xmu.ooad.util.JwtHelper;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.presale.PreSaleServiceApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author LJP_3424
 * @create 2020-12-01 16:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreSaleServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc //不启动服务器,使用mockMvc进行测试http请求。启动了完整的Spring应用程序上下文，但没有启动服务器
public class PreSaleControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext wac;
    private static final Logger logger = LoggerFactory.getLogger(PreSaleServiceApplication.class);

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();  //构建MockMVC
    }

    /**
     * 无状态测试
     */
    @Test
    public void getPreSaleByIdTest() throws Exception {
        String token = creatTestToken(1L, 0L, 100);
        String response = this.mvc.perform(get("/shops/10001/spus/103/presales").header("authorization", token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"data\":{\"id\":1001,\"name\":\"秋季新款\",\"beginTime\":\"2020-11-30T07:33:49\",\"payTime\":\"2020-12-01T07:33:55\",\"endTime\":\"2020-12-02T07:33:59\",\"state\":1,\"quantity\":123,\"advancePayPrice\":12,\"restPayPrice\":12,\"gmtCreateTime\":\"2020-12-02T00:27:51\",\"gmtModiTime\":\"2020-12-02T07:34:20\"},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, response, true);
    }

    /**
     * 状态测试
     */
    @Test
    public void getPreSaleByIdTest2() throws Exception {
        String token = creatTestToken(1L, 0L, 100);
        String response = this.mvc.perform(get("/shops/10001/spus/103/presales?state=1").header("authorization", token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"data\":{\"id\":1001,\"name\":\"秋季新款\",\"beginTime\":\"2020-11-30T07:33:49\",\"payTime\":\"2020-12-01T07:33:55\",\"endTime\":\"2020-12-02T07:33:59\",\"state\":1,\"quantity\":123,\"advancePayPrice\":12,\"restPayPrice\":12,\"gmtCreateTime\":\"2020-12-02T00:27:51\",\"gmtModiTime\":\"2020-12-02T07:34:20\"},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, response, true);
    }

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }

    /**
     * 状态查询测试
     */
    @Test
    public void getAllPreSalesStateTest() throws Exception {

        String response = this.mvc.perform(get("/presales/states"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"data\":[{\"name\":\"未开始\",\"code\":0},{\"name\":\"预售\",\"code\":1},{\"name\":\"尾款\",\"code\":2},{\"name\":\"结束\",\"code\":3}],\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, response, true);
    }

    /**
     * 预售查询测试
     */
    @Test
    public void getAllPreSales() throws Exception {
        String response = this.mvc.perform(get("/presales"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"data\":{\"total\":3,\"pages\":1,\"pageSize\":3,\"page\":1,\"list\":[{\"id\":1001,\"name\":\"秋季新款\",\"beginTime\":\"2020-10-01T07:33:49\",\"payTime\":\"2020-11-03T07:33:55\",\"endTime\":\"2020-11-18T07:33:59\",\"state\":3,\"quantity\":123,\"advancePayPrice\":12,\"restPayPrice\":12,\"gmtCreateTime\":\"2020-12-02T00:27:51\",\"gmtModiTime\":\"2020-12-02T07:34:20\"},{\"id\":1002,\"name\":\"夏季热销\",\"beginTime\":\"2020-12-01T07:34:47\",\"payTime\":\"2020-12-02T07:34:44\",\"endTime\":\"2020-12-03T07:34:41\",\"state\":1,\"quantity\":111,\"advancePayPrice\":111,\"restPayPrice\":122,\"gmtCreateTime\":\"2020-12-02T00:28:03\",\"gmtModiTime\":\"2020-12-02T10:34:25\"},{\"id\":1003,\"name\":\"冬季保暖\",\"beginTime\":\"2020-12-04T10:41:54\",\"payTime\":\"2020-12-06T10:41:59\",\"endTime\":\"2020-12-07T10:42:02\",\"state\":0,\"quantity\":111,\"advancePayPrice\":112,\"restPayPrice\":123,\"gmtCreateTime\":\"2020-12-02T10:42:24\",\"gmtModiTime\":null}]},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, response, true);
    }

    /**
     * 预售查询测试
     * 主要测试时间段是否正确
     */
    @Test
    public void getAllPreSales2() throws Exception {
        String response = this.mvc.perform(get("/presales?pageSize=2&page=1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"data\":{\"total\":2,\"pages\":1,\"pageSize\":2,\"page\":1,\"list\":[{\"id\":1001,\"name\":\"秋季新款\",\"beginTime\":\"2020-10-01T07:33:49\",\"payTime\":\"2020-11-03T07:33:55\",\"endTime\":\"2020-11-18T07:33:59\",\"state\":3,\"quantity\":123,\"advancePayPrice\":12,\"restPayPrice\":12,\"gmtCreateTime\":\"2020-12-02T00:27:51\",\"gmtModiTime\":\"2020-12-02T07:34:20\"},{\"id\":1002,\"name\":\"夏季热销\",\"beginTime\":\"2020-12-01T07:34:47\",\"payTime\":\"2020-12-02T07:34:44\",\"endTime\":\"2020-12-03T07:34:41\",\"state\":1,\"quantity\":111,\"advancePayPrice\":111,\"restPayPrice\":122,\"gmtCreateTime\":\"2020-12-02T00:28:03\",\"gmtModiTime\":\"2020-12-02T10:34:25\"}]},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, response, true);
    }

    /**
     * 预售新建测试
     */
    @Test
    public void createPreSale() throws Exception {
        String requireJson = "{\n    \"userName\": \"anormalusername3\",\n    \"password\": \"123456aBa!\",\n    \"name\": \"LiangJi\",\n    \"avatar\": \"https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png\",\n    \"mobile\": \"13888888388\",\n    \"email\": \"test@test.com\",\n    \"openId\": \"test\",\n    \"departId\": 1\n}";
        String responseString = this.mvc.perform(post("/privilege/adminusers")
                .contentType("application/json;charset=UTF-8")
                .content(requireJson)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{ \"errno\": 0, \"data\": { \"id\": 1, \"userName\": \"anormalusername3\", \"mobile\": \"8733C04F80C594827F776CD726B85472\", \"email\": \"5643361C11D3299408C7EA82206AFCC7\", \"name\": \"4A3BE008F8DE844B7EE7042E1B7B8842\", \"avatar\": \"https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png\", \"openId\": \"test\", \"departId\": 1,  \"password\": \"2B29D194F1ECBBC839DCC34F572269A3\" }, \"errmsg\": \"成功\" }";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }


}
