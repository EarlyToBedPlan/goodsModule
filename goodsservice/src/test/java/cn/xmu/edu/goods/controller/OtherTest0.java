//package cn.xmu.edu.goods.controller;
//
//import cn.edu.xmu.goods.GoodsServiceApplication;
//import org.junit.jupiter.api.Test;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * @author Yancheng Lai
// * createdBy Yancheng Lai 2020/12/8 23:30
// * modifiedBy Yancheng Lai 23:30
// **/
//@SpringBootTest(classes = GoodsServiceApplication.class)   //标识本类是一个SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//public class OtherTest0 {
//    @Autowired
//    private MockMvc mvc;
//
//
//    @Test
//    //200
//    public void getBrands2() throws Exception{
//        //String token = this.login("13088admin", "123456");
//
//        //logger.debug("check for brands.");
//        String responseString = this.mvc.perform(get("/goods/test3"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse().getContentAsString();
//        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
//        JSONAssert.assertEquals(responseString,expectedResponse, true);
//    }
//
//
//}
