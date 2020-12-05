package cn.edu.xmu.flashsale.service;

import cn.edu.xmu.flashsale.dao.FlashSaleDao;
import cn.edu.xmu.flashsale.model.vo.NewFlashSaleItemVo;
import cn.edu.xmu.flashsale.model.vo.NewFlashSaleVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
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
 * @create 2020-12-03 16:45
 */
@Service
public class FlashSaleService {
    private Logger logger = LoggerFactory.getLogger(FlashSaleService.class);

    @Autowired
    FlashSaleDao flashSaleDao;

    @Transactional
    public ReturnObject<List> getFlashSaleById(Long id) {


        ReturnObject<List> flashSalePos = flashSaleDao.getFlashSaleById(id);
        if (flashSalePos != null) {
            //returnObject = new ReturnObject<FlashSalePo>(flashSalePo);
            return flashSalePos;
        } else {
            logger.debug("findFlashSaleById: Not Found");
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
    }

    @Transactional
    public boolean getFlashSaleInActivities(Long goodsSpuId, LocalDateTime beginTime, LocalDateTime endTime) {
        return flashSaleDao.getFlashSaleInActivities(goodsSpuId, beginTime, endTime);
    }

    @Transactional
    public ReturnObject<List> getCurrentFlashSale(LocalDateTime localDateTime) {

        ReturnObject<List> flashSalePos = flashSaleDao.getCurrentFlashSale(localDateTime);
        if (flashSalePos != null) {
            //returnObject = new ReturnObject<FlashSalePo>(flashSalePo);
            return flashSalePos;
        } else {
            logger.debug("getCurrentFlashSale: Not Found");
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
    }

    @Transactional
    public ReturnObject createNewFlashSale(NewFlashSaleVo vo, Long id) {
        ReturnObject returnObject = flashSaleDao.createNewFlashSaleByVo(vo, id);
        return returnObject;
    }

    /**
     * 修改活动
     *
     * @author LJP_3424
     */
    @Transactional
    public ReturnObject updateFlashSale(NewFlashSaleVo newFlashSaleVo, Long id) {
        ReturnObject<VoObject> retObj = flashSaleDao.updateFlashSale(newFlashSaleVo, id);
        return retObj;
    }

    /**
     * 添加SKU
     *
     * @author LJP_3424
     */
    @Transactional
    public ReturnObject insertSkuIntoPreSale(NewFlashSaleItemVo newFlashSaleItemVo, Long id) {
        ReturnObject<List> retObj = flashSaleDao.insertSkuIntoFlashSale(newFlashSaleItemVo, id);
        return retObj;
    }

    /**
     * 分页查询所有预售活动
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return ReturnObject<PageInfo < VoObject>> 分页返回预售信息
     * @author LJP_3424
     */
    public ReturnObject<PageInfo<VoObject>> selectAllFlashSale(Long id, Integer pageNum, Integer pageSize) {
        return flashSaleDao.selectAllFlashSale(id, pageNum, pageSize);
    }
}
