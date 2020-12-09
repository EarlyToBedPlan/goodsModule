package cn.edu.xmu.goods;

import cn.edu.xmu.goods.model.vo.FloatPriceVo;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = GoodsServiceApplication.class)
@AutoConfigureMockMvc
@Transactional
public class GoodsCategoryControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceApplication.class);

    @Autowired
    private MockMvc mvc;

    @Test
    public void createCategorySuccess() throws Exception{
        String token=creatTestToken(1L, 0L, 100);
        String contentJson=JacksonUtil.toJson("川普宝宝");
        String responseString=this.mvc.perform(post("/categories/122/subcategories").header("authorization",token).contentType("application/json;charset=UTF-8").content(contentJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.print(responseString);
    }


    @Test
    public void deleteCategorySuccess() throws Exception{
        String token=creatTestToken(1L, 0L, 100);
        String responseString=this.mvc.perform(delete("/categories/{id}").header("authorization",token))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"code\":\"OK\",\"errmsg\":\"成功\",\"data\":null}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }

    @Test
    public void deleteCategoryError() throws Exception{
        String token=creatTestToken(1L, 0L, 100);
        String responseString=this.mvc.perform(delete("/categories/{id}").header("authorization",token))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"code\":\"RESOURCE_ID_NOTEXIST\",\"errmsg\":\"操作的资源id不存在\",\"data\":null}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }

    @Test
    public void updateCategorySuccess() throws Exception{
        String token=creatTestToken(1L, 0L, 100);
        String contentJson= JacksonUtil.toJson("Honor") ;
        String responseString=this.mvc.perform(put("/categories/132").header("authorization",token).contentType("application/json;charset=UTF-8").content(contentJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"code\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }

    @Test
    public void updateCategoryError1() throws Exception{
        String token=creatTestToken(1L, 0L, 100);
        String contentJson= JacksonUtil.toJson("Honor") ;
        String responseString=this.mvc.perform(put("/categories/150").header("authorization",token).contentType("application/json;charset=UTF-8").content(contentJson))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"code\":\"RESOURCE_ID_NOTEXIST\",\"errmsg\":\"操作的资源id不存在\",\"data\":null}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }

    @Test
    public void getCategorySuccess() throws Exception{
        String token=creatTestToken(1L, 0L, 100);
        String responseString=this.mvc.perform(get("/categories/122/subcategories").header("auhtorizatin",token))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.print(responseString);
    }

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }
}
