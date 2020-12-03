package cn.edu.xmu.flashsale.dao;

import cn.edu.xmu.flashsale.mapper.FlashSaleItemPoMapper;
import cn.edu.xmu.flashsale.mapper.FlashSalePoMapper;
import cn.edu.xmu.flashsale.mapper.TimeSegmentPoMapper;
import cn.edu.xmu.flashsale.model.bo.FlashSaleItem;
import cn.edu.xmu.flashsale.model.po.*;
import cn.edu.xmu.flashsale.model.vo.FlashSaleDataVo;
import cn.edu.xmu.flashsale.model.vo.NewFlashSaleVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LJP_3424
 * @create 2020-12-03 16:44
 */
@Repository
public class FlashSaleDao implements InitializingBean {

    @Autowired
    private FlashSalePoMapper flashSalePoMapper;

    @Autowired
    private FlashSaleItemPoMapper flashSaleItemPoMapper;

    @Autowired
    private TimeSegmentPoMapper timeSegmentPoMapper;
    private static final Logger logger = LoggerFactory.getLogger(FlashSaleDao.class);


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public ReturnObject<List>   getCurrentFlashSale(LocalDateTime localDateTime) {

        TimeSegmentPoExample timeSegmentPoExample = new TimeSegmentPoExample();
        TimeSegmentPoExample.Criteria criteria = timeSegmentPoExample.createCriteria();
        criteria.andBeginTimeLessThanOrEqualTo(localDateTime);
        criteria.andEndTimeGreaterThanOrEqualTo(localDateTime);
        List<TimeSegmentPo> timeSegmentPos = timeSegmentPoMapper.selectByExample(timeSegmentPoExample);
        if(timeSegmentPos.size() == 0){
            return null;
        }else{
            return getFlashSaleById(timeSegmentPos.get(0).getId());
        }
    }

    public ReturnObject<List> getFlashSaleById(Long id) {
        FlashSalePoExample example = new FlashSalePoExample();
        FlashSalePoExample.Criteria criteria = example.createCriteria();
        criteria.andTimeSegIdEqualTo(id);
        logger.debug("findFlashSaleById: TimeSegmentId = " + id);
        List<FlashSalePo> flashSalePos = flashSalePoMapper.selectByExample(example);
        List<FlashSaleDataVo> flashSaleDataVos = new ArrayList<FlashSaleDataVo>();
        for(FlashSalePo po:flashSalePos){
            FlashSaleItemPoExample flashSaleItemPoExample = new FlashSaleItemPoExample();
            FlashSaleItemPoExample.Criteria criteria_item = flashSaleItemPoExample.createCriteria();
            criteria_item.andSaleIdEqualTo(po.getId());
            List<FlashSaleItemPo> flashSaleItemPos = flashSaleItemPoMapper.selectByExample(flashSaleItemPoExample);
            for(FlashSaleItemPo itemPo:flashSaleItemPos){
                FlashSaleDataVo vo = new FlashSaleDataVo(itemPo);
                flashSaleDataVos.add(vo);
            }
        }
        if (flashSalePos.size() != 0) {
            return new ReturnObject<>(flashSaleDataVos);
        } else {
            return null;
        }
    }


    /**
     * 由vo创建newUser检查重复后插入
     *
     * @param vo vo对象
     * @return ReturnObject
     * createdBy: LJP_3424
     */
    public ReturnObject<List> createNewFlashSaleByVo(NewFlashSaleVo vo, Long id) {
        //logger.debug(String.valueOf(bloomFilter.includeByBloomFilter("mobileBloomFilter","FAED5EEF1C8562B02110BCA3F9165CBE")));
        //by default,email/mobile are both needed
        FlashSalePoExample example = new FlashSalePoExample();
        FlashSalePoExample.Criteria criteria = example.createCriteria();
        criteria.andTimeSegIdEqualTo(id);
        logger.debug("findFlashSaleById: TimeSegmentId = " + id);
        List<FlashSalePo> flashSalePos = flashSalePoMapper.selectByExample(example);
        if(flashSalePos.size() != 0)return new ReturnObject(ResponseCode.TIMESEG_CONFLICT);
        FlashSalePo flashSalePo = new FlashSalePo();
        ReturnObject returnObject;
        flashSalePo.setFlashDate(vo.getFlashDate());
        flashSalePo.setTimeSegId(id);
        flashSalePo.setGmtCreated(LocalDateTime.now());
        try {
            flashSalePoMapper.insert(flashSalePo);
            return getFlashSaleById(id);
        } catch (Exception e) {
            return new ReturnObject(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }
    /**
     * 修改一个预售活动信息
     *
     * @author LJP_3424
     */
    public ReturnObject<VoObject> updateFlashSale(NewFlashSaleVo flashSaleVo, Long id) {
        FlashSalePo po = flashSalePoMapper.selectByPrimaryKey(id);
        TimeSegmentPo timeSegmentPo = timeSegmentPoMapper.selectByPrimaryKey(po.getTimeSegId());

        if (timeSegmentPo.getBeginTime().compareTo(LocalDateTime.now()) < 0) {
            return new ReturnObject<>(ResponseCode.TIMESEG_CONFLICT);
        }
        po.setGmtModified(LocalDateTime.now());
        ReturnObject<VoObject> retObj = null;
        try {
            int ret = flashSalePoMapper.updateByPrimaryKey(po);
            if (ret != 0) {
                FlashSale flashSale = new FlashSale(flashSalePoMapper.selectByPrimaryKey(id));
                retObj = new ReturnObject<>(flashSale);
            }
        } catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
        return retObj;
    }
}
