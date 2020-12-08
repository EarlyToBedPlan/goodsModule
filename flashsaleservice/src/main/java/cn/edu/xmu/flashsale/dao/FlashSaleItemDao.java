package cn.edu.xmu.flashsale.dao;

import cn.edu.xmu.flashsale.mapper.FlashSaleItemPoMapper;
import cn.edu.xmu.flashsale.mapper.FlashSalePoMapper;
import cn.edu.xmu.flashsale.model.bo.FlashSaleItem;
import cn.edu.xmu.flashsale.model.po.FlashSaleItemPo;
import cn.edu.xmu.flashsale.model.po.FlashSaleItemPoExample;
import cn.edu.xmu.flashsale.model.po.FlashSalePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LJP_3424
 * @create 2020-12-08 1:36
 */
@Repository
public class FlashSaleItemDao {
    @Autowired
    FlashSaleItemPoMapper flashSaleItemPoMapper;
    public List<FlashSaleItemPo> getFlashSaleItemPoFromSaleId(Long saleId){
        FlashSaleItemPoExample example = new FlashSaleItemPoExample();
        FlashSaleItemPoExample.Criteria criteria = example.createCriteria();
        criteria.andSaleIdEqualTo(saleId);
        return flashSaleItemPoMapper.selectByExample(example);
    }
}
