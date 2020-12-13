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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/13 11:26
 * modifiedBy Yancheng Lai 11:26
 **/
@SpringBootTest(classes = GoodsServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class uploadimgControllerTest {
    @Autowired
    private MockMvc mvc;


    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        return token;
    }

    @Test
    public void uploadFileSuccess() throws Exception{
        String token = creatTestToken(1L,0L,100);
        File file = new File("."+File.separator + "src" + File.separator + "test" + File.separator+ "resources" + File.separator+"index1.jpg");
        MockMultipartFile firstFile = new MockMultipartFile("img", "index1.jpg" , "multipart/form-data", new FileInputStream(file));
        String responseString = mvc.perform(MockMvcRequestBuilders
                .multipart("/goods/shops/0/skus/300/uploadImg")
                .file(firstFile)
                .header("authorization", token)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

}
