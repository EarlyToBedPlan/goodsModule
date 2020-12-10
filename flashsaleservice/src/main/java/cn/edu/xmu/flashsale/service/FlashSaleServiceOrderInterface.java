package cn.edu.xmu.flashsale.service;

import cn.edu.xmu.flashsale.dao.FlashSaleDao;
import cn.edu.xmu.flashsale.model.bo.FlashSale;
import cn.edu.xmu.flashsale.model.po.FlashSaleItemPo;
import cn.edu.xmu.flashsale.model.po.FlashSalePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-10 13:02
 */
@Service
public class FlashSaleServiceOrderInterface implements FlashSaleService {
    @Autowired
    FlashSaleDao flashSaleDao;
    Long getFlashSalePriceByGoodsSkuId(Long goodsSkuId){
        FlashSaleItemPo goodsFlashSaleItemPo = flashSaleDao.getFlashSaleItemBetweenTimeByGoodsSkuId(goodsSkuId, LocalDateTime.now(), LocalDateTime.now());
        if(goodsFlashSaleItemPo != null){
            return goodsFlashSaleItemPo.getPrice();
        }else{
            return null;
        }
    }
}
