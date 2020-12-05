package cn.edu.xmu.flashsale.dao;

import cn.edu.xmu.flashsale.mapper.FlashSaleItemPoMapper;
import cn.edu.xmu.flashsale.mapper.FlashSalePoMapper;
import cn.edu.xmu.flashsale.mapper.TimeSegmentPoMapper;
import cn.edu.xmu.flashsale.model.bo.FlashSale;
import cn.edu.xmu.flashsale.model.bo.FlashSaleRetItem;
import cn.edu.xmu.flashsale.model.po.*;
import cn.edu.xmu.flashsale.model.vo.*;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

/**
 * @Description: 未提供SPU-->SKUS接口,因此先暂时写一个临时函数
 *
 * @param goodsSpuId
 * @return: java.util.List<java.lang.Long>
 * @Author: LJP_3424
 * @Date: 2020/12/6 1:01
 */
    private List<Long> goodsSpuIdsToSkuIds(Long goodsSpuId) {
        List<Long> longs = new ArrayList<Long>();
        longs.add(1003L);
        longs.add(1004L);
        longs.add(1005L);
        return longs;
    }

/**
 * @Description: 通过SPU获取集合内SKU是否参与秒杀活动
 *
 * @param goodsSpuId
 * @param beginTime
 * @param endTime
 * @return: boolean
 * @Author: LJP_3424
 * @Date: 2020/12/6 1:02
 */
    public boolean getFlashSaleInActivities(Long goodsSpuId, LocalDateTime beginTime, LocalDateTime endTime) {
        // 等待接口
        List<Long> goodsSkuIds = goodsSpuIdsToSkuIds(goodsSpuId);

        // SKUId-->SaleId-->timeSegmentId-->beginTime&&endTime 耗时较长
        /* 取出所有的 timeSegmentID --> beginTime && endTime
         * SkuId-->SaleId-->-->timeSegmentId--Map-->beginTime&&endTime 减少数据库访问次数
         */
        TimeSegmentPoExample timeSegmentPoExample = new TimeSegmentPoExample();
        List<TimeSegmentPo> timeSegmentPos = timeSegmentPoMapper.selectByExample(timeSegmentPoExample);
        FlashSaleItemPoExample flashSaleItemPoExample = new FlashSaleItemPoExample();
        FlashSaleItemPoExample.Criteria criteriaItem = flashSaleItemPoExample.createCriteria();
        // List 转Map
        Map<Long, TimeSegmentPo> mappedTimeSegmentPo = timeSegmentPos.stream().collect(Collectors.toMap(TimeSegmentPo::getId, (p) -> p));
        for (Long goodsSkuId : goodsSkuIds) {
            criteriaItem.andGoodsSkuIdEqualTo(goodsSkuId);
            List<FlashSaleItemPo> flashSaleItemPos = flashSaleItemPoMapper.selectByExample(flashSaleItemPoExample);
            for (FlashSaleItemPo flashSaleItemPo : flashSaleItemPos) {
                FlashSalePo flashSalePo = flashSalePoMapper.selectByPrimaryKey(flashSaleItemPo.getSaleId());
                LocalDateTime timeSegmentBeginTime = mappedTimeSegmentPo.get(flashSalePo.getTimeSegId()).getBeginTime();
                LocalDateTime timeSegmentEndTime = mappedTimeSegmentPo.get(flashSalePo.getTimeSegId()).getEndTime();
                LocalDateTime flashSalePoBeginTime = LocalDateTime.of(flashSalePo.getFlashDate().getYear(),
                        flashSalePo.getFlashDate().getMonth(), flashSalePo.getFlashDate().getDayOfMonth(),
                        timeSegmentBeginTime.getHour(), timeSegmentBeginTime.getMinute(), timeSegmentBeginTime.getSecond());
                LocalDateTime flashSalePoEndTime = LocalDateTime.of(flashSalePo.getFlashDate().getYear(),
                        flashSalePo.getFlashDate().getMonth(), flashSalePo.getFlashDate().getDayOfMonth(),
                        timeSegmentEndTime.getHour(), timeSegmentEndTime.getMinute(), timeSegmentEndTime.getSecond());
                if (flashSalePoBeginTime.compareTo(endTime) < 0 && flashSalePoEndTime.compareTo(beginTime) > 0) {
                    return true;
                }
            }
        }
        return false;
    }
/*
*
 * @Description:
 *
 * @return: void
 * @Author: LJP_3424
 * @Date: 2020/12/6 1:02
    public void insertTimeSegment() {
        TimeSegmentPo timeSegmentPo = new TimeSegmentPo();
        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2020, 1, 1, 0, 29, 59);
        for (int i = 1; i <= 24; i++) {
            for (int j = 1; j <= 2; j++) {
                timeSegmentPo.setId((long) (i * 100 + j));
                timeSegmentPo.setBeginTime(beginTime);
                timeSegmentPo.setGmtCreated(LocalDateTime.now());
                timeSegmentPo.setEndTime(endTime);
                beginTime = beginTime.plusMinutes(30);
                endTime = endTime.plusMinutes(30);
                timeSegmentPoMapper.insert(timeSegmentPo);
            }
        }
    }
*/
/**
 * @Description: 获取当前时段的秒杀活动
 * @param localDateTime
 * @return: cn.edu.xmu.ooad.util.ReturnObject<java.util.List>
 * @Author: LJP_3424
 * @Date: 2020/12/6 1:03
 */
    public ReturnObject<List> getCurrentFlashSale(LocalDateTime localDateTime) {

        TimeSegmentPoExample timeSegmentPoExample = new TimeSegmentPoExample();
        TimeSegmentPoExample.Criteria criteria = timeSegmentPoExample.createCriteria();
        criteria.andBeginTimeLessThanOrEqualTo(localDateTime);
        criteria.andEndTimeGreaterThanOrEqualTo(localDateTime);
        List<TimeSegmentPo> timeSegmentPos = timeSegmentPoMapper.selectByExample(timeSegmentPoExample);
        if (timeSegmentPos.size() == 0) {
            return null;
        } else {
            return getFlashSaleById(timeSegmentPos.get(0).getId());
        }
    }

/**
 * @Description: 通过商品SKUID 获取商品历史秒杀信息
 *
 * @param id
 * @return: cn.edu.xmu.ooad.util.ReturnObject<java.util.List>
 * @Author: LJP_3424
 * @Date: 2020/12/6 1:03
 */
    public ReturnObject<List> getFlashSaleById(Long id) {
        FlashSalePoExample example = new FlashSalePoExample();
        FlashSalePoExample.Criteria criteria = example.createCriteria();
        criteria.andTimeSegIdEqualTo(id);
        logger.debug("findFlashSaleById: TimeSegmentId = " + id);
        List<FlashSalePo> flashSalePos = flashSalePoMapper.selectByExample(example);
        List<FlashSaleDataVo> flashSaleDataVos = new ArrayList<FlashSaleDataVo>();
        for (FlashSalePo po : flashSalePos) {
            FlashSaleItemPoExample flashSaleItemPoExample = new FlashSaleItemPoExample();
            FlashSaleItemPoExample.Criteria criteria_item = flashSaleItemPoExample.createCriteria();
            criteria_item.andSaleIdEqualTo(po.getId());
            List<FlashSaleItemPo> flashSaleItemPos = flashSaleItemPoMapper.selectByExample(flashSaleItemPoExample);
            for (FlashSaleItemPo itemPo : flashSaleItemPos) {
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
 * @Description: 通过Vo验证时段冲突后创建新的秒杀
 *
 * @param vo
 * @param id
 * @return: cn.edu.xmu.ooad.util.ReturnObject<java.util.List>
 * @Author: LJP_3424
 * @Date: 2020/12/6 1:04
 */
    public ReturnObject<List> createNewFlashSaleByVo(NewFlashSaleVo vo, Long id) {
        //logger.debug(String.valueOf(bloomFilter.includeByBloomFilter("mobileBloomFilter","FAED5EEF1C8562B02110BCA3F9165CBE")));
        //by default,email/mobile are both needed
        FlashSalePoExample example = new FlashSalePoExample();
        FlashSalePoExample.Criteria criteria = example.createCriteria();
        criteria.andTimeSegIdEqualTo(id);
        logger.debug("findFlashSaleById: TimeSegmentId = " + id);
        List<FlashSalePo> flashSalePos = flashSalePoMapper.selectByExample(example);
        if (flashSalePos.size() != 0) return new ReturnObject(ResponseCode.TIMESEG_CONFLICT);
        FlashSalePo flashSalePo = new FlashSalePo();
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
 * @Description: 修改秒杀信息
 *
 * @param flashSaleVo
 * @param id
 * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject>
 * @Author: LJP_3424
 * @Date: 2020/12/6 1:04
 */
    public ReturnObject<VoObject> updateFlashSale(NewFlashSaleVo flashSaleVo, Long id) {
        FlashSalePo po = flashSalePoMapper.selectByPrimaryKey(id);
        if (po == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        TimeSegmentPo timeSegmentPo = timeSegmentPoMapper.selectByPrimaryKey(po.getTimeSegId());
        if (timeSegmentPo == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        if (timeSegmentPo.getBeginTime().compareTo(LocalDateTime.now()) < 0) {
            return new ReturnObject<>(ResponseCode.TIMESEG_CONFLICT);
        }
        ReturnObject<VoObject> retObj = null;
        po.setFlashDate(flashSaleVo.getFlashDate());
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

 /**
  * @Description:  向秒杀活动中添加SKU
  *
  * @param null
  * @return:
  * @Author: LJP_3424
  * @Date: 2020/12/6 1:05
  */
    public ReturnObject<List> insertSkuIntoFlashSale(NewFlashSaleItemVo newFlashSaleItemVo, Long id) {
        Integer flashMaxSize = 5;
        FlashSaleItemPoExample flashSaleItemPoExample = new FlashSaleItemPoExample();
        FlashSaleItemPoExample.Criteria criteria = flashSaleItemPoExample.createCriteria();
        criteria.andSaleIdEqualTo(id);
        List<FlashSaleItemPo> flashSaleItemPos = flashSaleItemPoMapper.selectByExample(flashSaleItemPoExample);
        FlashSalePo flashSalePo = flashSalePoMapper.selectByPrimaryKey(id);
        if (flashSalePo == null) {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        TimeSegmentPo timeSegmentPo = timeSegmentPoMapper.selectByPrimaryKey(flashSalePo.getTimeSegId());
        if (timeSegmentPo == null) {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        // 判断秒杀活动是否开始
        if (timeSegmentPo.getBeginTime().compareTo(LocalDateTime.now()) < 0) {
            return new ReturnObject<>(ResponseCode.TIMESEG_CONFLICT);
        }
        if (flashSaleItemPos.size() > flashMaxSize - 1) {
            return new ReturnObject<>(ResponseCode.FLASHSALE_OUTLIMIT);
        }
        FlashSaleItemPo flashSaleItemPo = new FlashSaleItemPo();
        flashSaleItemPo.setQuantity(newFlashSaleItemVo.getQuantity());
        flashSaleItemPo.setSaleId(id);
        flashSaleItemPo.setGoodsSkuId(newFlashSaleItemVo.getSkuId());
        flashSaleItemPo.setPrice(newFlashSaleItemVo.getPrice());
        flashSaleItemPo.setGmtCreated(LocalDateTime.now());

        flashSaleItemPoMapper.insert(flashSaleItemPo);

        flashSaleItemPos.add(flashSaleItemPo);

        return new ReturnObject<>(flashSaleItemPos);
    }


/**
 * @Description: 分页查询所有秒杀信息
 *
 * @param id
 * @param pageNum
 * @param pageSize
 * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo<cn.edu.xmu.ooad.model.VoObject>>
 * @Author: LJP_3424
 * @Date: 2020/12/6 1:05
 */

    public ReturnObject<PageInfo<VoObject>> selectAllFlashSale(Long id, Integer pageNum, Integer pageSize) {
        FlashSaleItemPoExample flashSaleItemPoExample = new FlashSaleItemPoExample();
        FlashSaleItemPoExample.Criteria criteriaItem = flashSaleItemPoExample.createCriteria();
        criteriaItem.andSaleIdEqualTo(id);
        List<FlashSaleItemPo> flashSaleItemPos = flashSaleItemPoMapper.selectByExample(flashSaleItemPoExample);
        //分页查询
        List<VoObject> ret = new ArrayList<>(flashSaleItemPos.size());
        PageHelper.startPage(pageNum, pageSize);
        logger.debug("page = " + pageNum + "pageSize = " + pageSize);
        try {
            for (FlashSaleItemPo po : flashSaleItemPos) {
                ret.add(new FlashSaleRetItem(po));
            }
            PageInfo<VoObject> flashSalePage = PageInfo.of(ret);
            return new ReturnObject<PageInfo<VoObject>>(flashSalePage);
        } catch (DataAccessException e) {
            logger.error("selectAllFlashSale: DataAccessException:" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        } catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
    }
}
