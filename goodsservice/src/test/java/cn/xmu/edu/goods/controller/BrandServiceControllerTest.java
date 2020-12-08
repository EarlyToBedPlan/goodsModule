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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/6 21:38
 * modifiedBy Yancheng Lai 21:38
 **/
@SpringBootTest(classes = GoodsServiceApplication.class)   //标识本类是一个SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BrandServiceControllerTest {

    @Autowired
    private MockMvc mvc;

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }

    public static final Logger logger = LoggerFactory.getLogger(BrandServiceControllerTest.class);
    @Test
    //200
    public void getBrands1() throws Exception{
        //String token = this.login("13088admin", "123456");

        logger.debug("check for brands.");
        String responseString = this.mvc.perform(get("/brands/brands?page=0&pageSize=0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"data\":{\"total\":49,\"pages\":1,\"pageSize\":49,\"page\":1,\"list\":[{\"id\":71,\"name\":\"戴荣华\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":72,\"name\":\"范敏祺\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":73,\"name\":\"黄卖九\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":74,\"name\":\"李进\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":75,\"name\":\"李菊生\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":76,\"name\":\"李小聪\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":77,\"name\":\"刘伟\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":78,\"name\":\"陆如\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":79,\"name\":\"秦锡麟\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":80,\"name\":\"舒慧娟\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":81,\"name\":\"王怀俊\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":82,\"name\":\"王锡良\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":83,\"name\":\"徐亚凤\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":84,\"name\":\"俞军\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":85,\"name\":\"张松茂\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":86,\"name\":\"赵紫云\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":87,\"name\":\"钟莲生\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":88,\"name\":\"戴玉梅\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":89,\"name\":\"付长敏\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":90,\"name\":\"况坚\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":91,\"name\":\"刘文斌\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":92,\"name\":\"汪平孙\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":93,\"name\":\"王青\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":94,\"name\":\"王秋霞\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":95,\"name\":\"熊婕\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":96,\"name\":\"徐文强\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":97,\"name\":\"占亚雄\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":98,\"name\":\"何炳钦\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":99,\"name\":\"大吉大利\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":100,\"name\":\"中国书院\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":101,\"name\":\"诚德轩\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":102,\"name\":\"九段烧\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":103,\"name\":\"宝瓷林\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":104,\"name\":\"中国集邮总公司\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":105,\"name\":\"金和汇景\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":106,\"name\":\"沐诣堂\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":107,\"name\":\"叶可思\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":108,\"name\":\"王旺金\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":109,\"name\":\"李家正\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":110,\"name\":\"毛了\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":111,\"name\":\"孙星池\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":112,\"name\":\"林祺龙\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":113,\"name\":\"故宫\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":114,\"name\":\"方毅\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":115,\"name\":\"李晓辉\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":116,\"name\":\"揭金平\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":117,\"name\":\"杨曙华\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":118,\"name\":\"方冬生\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":119,\"name\":\"皇家窑火\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"}]},\"errmsg\":\"成功\"}\n";
                JSONAssert.assertEquals(responseString,expectedResponse, true);
    }

    @Test
    //page 1
    public void getBrands2() throws Exception{
        //String token = this.login("13088admin", "123456");

        logger.debug("check for brands.");
        String responseString = this.mvc.perform(get("/brands/brands?page=1&pageSize=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"data\":{\"total\":10,\"pages\":1,\"pageSize\":10,\"page\":1,\"list\":[{\"id\":71,\"name\":\"戴荣华\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":72,\"name\":\"范敏祺\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":73,\"name\":\"黄卖九\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":74,\"name\":\"李进\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":75,\"name\":\"李菊生\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":76,\"name\":\"李小聪\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":77,\"name\":\"刘伟\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":78,\"name\":\"陆如\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":79,\"name\":\"秦锡麟\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":80,\"name\":\"舒慧娟\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"}]},\"errmsg\":\"成功\"}\n";
                JSONAssert.assertEquals(responseString,expectedResponse, true);
    }
    //last page
    @Test
    public void getBrands3() throws Exception{
        //String token = this.login("13088admin", "123456");

        logger.debug("check for brands.");
        String responseString = this.mvc.perform(get("/brands/brands?page=5&pageSize=20"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"data\":{\"total\":9,\"pages\":1,\"pageSize\":9,\"page\":1,\"list\":[{\"id\":111,\"name\":\"孙星池\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":112,\"name\":\"林祺龙\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":113,\"name\":\"故宫\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":114,\"name\":\"方毅\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":115,\"name\":\"李晓辉\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":116,\"name\":\"揭金平\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":117,\"name\":\"杨曙华\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":118,\"name\":\"方冬生\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":119,\"name\":\"皇家窑火\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"}]},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(responseString,expectedResponse, true);
    }

    @Test
    //200
    public void getBrands4() throws Exception{
        //String token = this.login("13088admin", "123456");

        logger.debug("check for brands.");
        String responseString = this.mvc.perform(get("/brands/brands"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"data\":{\"total\":10,\"pages\":1,\"pageSize\":10,\"page\":1,\"list\":[{\"id\":71,\"name\":\"戴荣华\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":72,\"name\":\"范敏祺\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":73,\"name\":\"黄卖九\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":74,\"name\":\"李进\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":75,\"name\":\"李菊生\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":76,\"name\":\"李小聪\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":77,\"name\":\"刘伟\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":78,\"name\":\"陆如\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":79,\"name\":\"秦锡麟\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"},{\"id\":80,\"name\":\"舒慧娟\",\"imageUrl\":null,\"detail\":null,\"gmtCreate\":\"2020-12-07T11:24:47\",\"gmtModified\":\"2020-12-07T11:24:47\"}]},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(responseString,expectedResponse, true);
    }

    @Test
    public void deleteBrandTest1() throws Exception {
        String token=creatTestToken(1L, 0L, 100);


        String responseString = this.mvc.perform(delete("/brands/shops/0/brands/114514")
                .header("authorization",token))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    public void deleteBrandTest2() throws Exception {
        String token=creatTestToken(1L, 0L, 100);

        String responseString = this.mvc.perform(delete("/brands/shops/0/brands/75")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }


}
