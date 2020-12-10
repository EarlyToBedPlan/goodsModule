package cn.edu.xmu.flashsale.service;

import cn.edu.xmu.flashsale.FlashSaleServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author LJP_3424
 * @create 2020-12-10 13:35
 */
@SpringBootTest(classes = FlashSaleServiceApplication.class)
@RunWith(SpringRunner.class)
public class FlashSaleServiceTest {
    @Autowired
    FlashSaleServiceOrderInterface flashSaleService;
    @Test
    public void getPriceTest(){
        Long flashSalePriceByGoodsSkuId = flashSaleService.getFlashSalePriceByGoodsSkuId(1001L);
        System.out.println(flashSalePriceByGoodsSkuId);
    }
}
