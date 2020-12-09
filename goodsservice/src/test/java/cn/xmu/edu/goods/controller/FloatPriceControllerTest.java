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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = GoodsServiceApplication.class)
@AutoConfigureMockMvc
@Transactional
public class FloatPriceControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceApplication.class);

    @Autowired
    private MockMvc mvc;

    @Test
    public void createFloatPriceSuccess() throws Exception{
        String token=creatTestToken(1L, 0L, 100);
        FloatPriceVo floatPriceVo=new FloatPriceVo();
        floatPriceVo.setActivityPrice((long) 100);
        floatPriceVo.setBeginTime("2021-01-01 00:00:00");
        floatPriceVo.setEndTime("2021-01-30 00:00:00");
        floatPriceVo.setQuantity(10000);
        String contentJson= JacksonUtil.toJson(floatPriceVo);
        String responseString=this.mvc.perform(post("/shops/1/skus/{id}/floatPrices").header("authorization",token).contentType("application/json;charset=UTF-8").content(contentJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.print(responseString);
    }

    @Test
    public void createFloatPriceError() throws Exception{
        String token=creatTestToken(1L, 0L, 100);
        FloatPriceVo floatPriceVo=new FloatPriceVo();
        floatPriceVo.setActivityPrice((long) 100);
        floatPriceVo.setBeginTime("2021-01-01 00:00:00");
        floatPriceVo.setEndTime("2021-01-30 00:00:00");
        floatPriceVo.setQuantity(10000);
        String contentJson= JacksonUtil.toJson(floatPriceVo);
        String responseString=this.mvc.perform(post("/shops/{shopId}/skus/{id}/floatPrices").header("authorization",token).contentType("application/json;charset=UTF-8").content(contentJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"code\":\"SKUPRICE_CONFLICT\",\"errmsg\":\"商品浮动价格时间冲突\",\"data\":null}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }

    @Test
    public void deleteFloatPriceSuccess() throws  Exception{
        String responseString=this.mvc.perform(delete("/shops/{shopId}/floatPrices/{id}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"code\":\"OK\",\"errmsg\":\"成功\",\"data\":null}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }

    @Test
    public void deleteFloatPriceError() throws Exception{
        String responseString=this.mvc.perform(delete("/shops/{*shopId*}/floatPrices/{*id*}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{\"code\":\"RESOURCE_ID_NOTEXIST\",\"errmsg\":\"操作的资源id不存在\",\"data\":null}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }
}
