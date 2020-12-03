package cn.edu.xmu.flashsale.service;

import cn.edu.xmu.flashsale.dao.FlashSaleDao;
import cn.edu.xmu.flashsale.model.vo.NewFlashSaleVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
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
    public ReturnObject<List> getFlashSaleById( Long id) {

        ReturnObject<List> flashSalePos = flashSaleDao.getFlashSaleById(id);
        if(flashSalePos != null){
            //returnObject = new ReturnObject<FlashSalePo>(flashSalePo);
            return flashSalePos;
        }else{
            logger.debug("findFlashSaleById: Not Found");
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
    }

    @Transactional
    public ReturnObject<List> getCurrentFlashSale(LocalDateTime localDateTime) {

        ReturnObject<List> flashSalePos = flashSaleDao.getCurrentFlashSale(localDateTime);
        if(flashSalePos != null){
            //returnObject = new ReturnObject<FlashSalePo>(flashSalePo);
            return flashSalePos;
        }else{
            logger.debug("getCurrentFlashSale: Not Found");
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
    }

    @Transactional
    public ReturnObject createNewFlashSale(NewFlashSaleVo vo, Long id) {
        ReturnObject returnObject = flashSaleDao.createNewFlashSaleByVo(vo,id);
        return returnObject;
    }

    /**
     * 修改活动
     * @author LJP_3424
     */
    @Transactional
    public ReturnObject updateFlashSale(NewFlashSaleVo newFlashSaleVo,Long id) {
        ReturnObject<VoObject> retObj = flashSaleDao.updateFlashSale(newFlashSaleVo,id);
        return retObj;
    }
}
