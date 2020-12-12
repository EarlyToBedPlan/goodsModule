package cn.edu.xmu.flashsale.dao;

import cn.edu.xmu.flashsale.mapper.FlashSaleItemPoMapper;
import cn.edu.xmu.flashsale.mapper.FlashSalePoMapper;
import cn.edu.xmu.flashsale.model.bo.FlashSaleItem;
import cn.edu.xmu.flashsale.model.po.FlashSaleItemPo;
import cn.edu.xmu.flashsale.model.po.FlashSaleItemPoExample;
import cn.edu.xmu.flashsale.model.po.FlashSalePo;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    public List<FlashSaleItemPo> getFlashSaleItemPoFromSaleId(Long saleId) {
        FlashSaleItemPoExample example = new FlashSaleItemPoExample();
        FlashSaleItemPoExample.Criteria criteria = example.createCriteria();
        criteria.andSaleIdEqualTo(saleId);
        return flashSaleItemPoMapper.selectByExample(example);
    }

    public ReturnObject deleteFlashSaleItem(Long fid, Long skuId) {
        FlashSaleItemPoExample example = new FlashSaleItemPoExample();
        FlashSaleItemPoExample.Criteria criteria = example.createCriteria();
        criteria.andSaleIdEqualTo(fid);
        if(skuId != null) {
            criteria.andGoodsSkuIdEqualTo(skuId);
        }
        List<FlashSaleItemPo> flashSaleItemPos = flashSaleItemPoMapper.selectByExample(example);
        if(flashSaleItemPos.size() == 0){
            return new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        try {
            for (FlashSaleItemPo flashSaleItemPo : flashSaleItemPos) {
                flashSaleItemPoMapper.deleteByPrimaryKey(flashSaleItemPo.getId());
            }
        } catch (DataAccessException e) {
            // 数据库错误
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("发生了严重的数据库错误：%s", e.getMessage()));
        } catch (Exception e) {
            // 属未知错误
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("发生了严重的未知错误：%s", e.getMessage()));
        }
        return new ReturnObject(ResponseCode.OK);
    }
}
