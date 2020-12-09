package cn.xmu.edu.goods.controller;

import cn.edu.xmu.goods.GoodsServiceApplication;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/5 11:11
 * modifiedBy Yancheng Lai 11:11
 **/
@SpringBootTest(classes = GoodsServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional

public class GoodsServiceControllerTest {
    @Autowired
    private MockMvc mvc;

    /** 
    * @Description: 测试获取SPU全部状态 
    * @Param: [] 
    * @return: void 
    * @Author: Yancheng Lai
    * @Date: 2020/12/5 13:01
    */
    @Test
    public void getAllSkuStates() throws Exception{
        //String token = this.login("13088admin", "123456");
        String responseString = this.mvc.perform(get("/goods/skus/states"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"data\":[{\"code\":0,\"name\":\"未上架\"},{\"code\":4,\"name\":\"上架\"},{\"code\":6,\"name\":\"已删除\"}],\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }


}
