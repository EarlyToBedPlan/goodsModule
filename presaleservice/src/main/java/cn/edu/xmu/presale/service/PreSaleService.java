package cn.edu.xmu.presale.service;

import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.presale.dao.PreSaleDao;
import cn.edu.xmu.presale.model.bo.NewPreSale;
import cn.edu.xmu.presale.model.bo.PreSale;
import cn.edu.xmu.presale.model.vo.NewPreSaleVo;
import cn.edu.xmu.presale.model.vo.PreSaleVo;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LJP_3424
 * @create 2020-12-01 14:25
 */
@Service
public class PreSaleService {
    private Logger logger = LoggerFactory.getLogger(PreSaleService.class);

    @Autowired
    PreSaleDao preSaleDao;

    @Transactional
    public boolean getPreSaleInActivities(Long goodsSpuId, LocalDateTime beginTime, LocalDateTime endTime) {
        return preSaleDao.getPreSaleInActivities(goodsSpuId, beginTime, endTime);
    }

    @Transactional
    public ReturnObject<List> getPreSaleById(Long shopId, Long id, Byte state) {

        ReturnObject<List> preSalePos = preSaleDao.getPreSaleById(shopId, id, state);
        if (preSalePos != null) {
            //returnObject = new ReturnObject<PreSalePo>(preSalePo);
            return preSalePos;
        } else {
            logger.debug("findPreSaleById: Not Found");
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
    }

    /**
     * 分页查询所有预售活动
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return ReturnObject<PageInfo < VoObject>> 分页返回预售信息
     * @author LJP_3424
     */
    public ReturnObject<PageInfo<VoObject>> selectAllPreSale(Long shopId, Byte timeline, Long spuId, Integer pageNum, Integer pageSize) {
        return preSaleDao.selectAllPreSale(shopId, timeline, spuId, pageNum, pageSize);
    }


    @Transactional
    public ReturnObject createNewPreSale(NewPreSaleVo vo, Long shopId, Long id) {
        ReturnObject returnObject = preSaleDao.createNewPreSaleByVo(vo, shopId, id);
        return returnObject;
    }

    @Transactional
    public ReturnObject<Object> deletePreSale(Long shopId, Long id) {
        return preSaleDao.changePreSaleState(shopId, id);
    }

    /**
     * 修改活动
     *
     * @author LJP_3424
     */
    @Transactional
    public ReturnObject updatePreSale(NewPreSaleVo newPreSaleVo, Long shopId, Long id) {
        ReturnObject<VoObject> retObj = preSaleDao.updatePreSale(newPreSaleVo, shopId, id);
        return retObj;
    }

}
