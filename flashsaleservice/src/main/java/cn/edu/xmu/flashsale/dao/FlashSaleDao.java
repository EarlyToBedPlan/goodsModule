package cn.edu.xmu.flashsale.dao;

import cn.edu.xmu.flashsale.mapper.FlashSaleItemPoMapper;
import cn.edu.xmu.flashsale.mapper.FlashSalePoMapper;
import cn.edu.xmu.flashsale.mapper.TimeSegmentPoMapper;
import cn.edu.xmu.flashsale.model.bo.FlashSale;
import cn.edu.xmu.flashsale.model.bo.FlashSaleItem;
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
     * @param goodsSpuId
     * @Description: 未提供SPU-->SKUS接口,因此先暂时写一个临时函数
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
     * @param goodsSpuId
     * @param beginTime
     * @param endTime
     * @Description: 通过SPU获取集合内SKU是否参与秒杀活动
     * @return: boolean
     * @Author: LJP_3424
     * @Date: 2020/12/6 1:02
     */
    //! 临时更改,需要重新写
    public FlashSaleItemPo getFlashSaleItemBetweenTimeByGoodsSkuId(Long goodsSkuId, LocalDateTime beginTime, LocalDateTime endTime) {

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
                return flashSaleItemPo;
            }
        }
        return null;
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
/*    public ReturnObject<List> getCurrentFlashSale(LocalDateTime localDateTime) {
        TimeSegmentPoExample timeSegmentPoExample = new TimeSegmentPoExample();
        TimeSegmentPoExample.Criteria criteria = timeSegmentPoExample.createCriteria();
        criteria.andBeginTimeLessThanOrEqualTo(localDateTime);
        criteria.andEndTimeGreaterThanOrEqualTo(localDateTime);
        List<TimeSegmentPo> timeSegmentPos = timeSegmentPoMapper.selectByExample(timeSegmentPoExample);
        return getFlashSaleById(timeSegmentPos.get(0).getId());
    }*/

    /**
     * @param id
     * @Description: 通过商品SKUID 获取商品历史秒杀信息
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
     * @param vo
     * @param id
     * @Description: 通过Vo验证时段冲突后创建新的秒杀
     * @return: cn.edu.xmu.ooad.util.ReturnObject<java.util.List>
     * @Author: LJP_3424
     * @Date: 2020/12/6 1:04
     */
    public Long createNewFlashSaleByVo(NewFlashSaleVo vo, Long id) {
        FlashSalePo flashSalePo = new FlashSalePo();
        flashSalePo.setFlashDate(vo.getFlashDate());
        flashSalePo.setTimeSegId(id);
        flashSalePo.setGmtCreate(LocalDateTime.now());
        flashSalePo.setState(FlashSale.State.OFF.getCode());
        flashSalePoMapper.insert(flashSalePo);
        return flashSalePo.getId();
    }

    public int countFlashSaleByTimeSegmentId(Long id) {
        FlashSalePoExample example = new FlashSalePoExample();
        FlashSalePoExample.Criteria criteria = example.createCriteria();
        criteria.andTimeSegIdEqualTo(id);
        logger.debug("findFlashSaleById: Time" + "SegmentId = " + id);
        List<FlashSalePo> flashSalePos = flashSalePoMapper.selectByExample(example);
        return flashSalePos.size();
    }

    public FlashSalePo getFlashSaleByFlashSaleId(Long flashSaleId) {
        return flashSalePoMapper.selectByPrimaryKey(flashSaleId);
    }

    /**
     * @param flashSaleVo
     * @param id
     * @Description: 修改秒杀信息
     * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject>
     * @Author: LJP_3424
     * @Date: 2020/12/6 1:04
     */
    public boolean updateFlashSale(NewFlashSaleVo flashSaleVo, Long id) {
        FlashSalePo flashSalePo = new FlashSalePo();
        flashSalePo.setFlashDate(flashSaleVo.getFlashDate());
        flashSalePo.setGmtModified(LocalDateTime.now());
        flashSalePo.setId(id);
        int ret = flashSalePoMapper.updateByPrimaryKeySelective(flashSalePo);
        if (ret == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param null
     * @Description: 向秒杀活动中添加SKU
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
        flashSaleItemPo.setGmtCreate(LocalDateTime.now());

        flashSaleItemPoMapper.insert(flashSaleItemPo);

        flashSaleItemPos.add(flashSaleItemPo);

        return new ReturnObject<>(flashSaleItemPos);
    }


    /**
     * @param id
     * @param pageNum
     * @param pageSize
     * @Description: 分页查询所有秒杀信息
     * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo < cn.edu.xmu.ooad.model.VoObject>>
     * @Author: LJP_3424
     * @Date: 2020/12/6 1:05
     */

    public List<FlashSaleItemPo> selectAllFlashSale(Long id, Integer pageNum, Integer pageSize) {
        FlashSaleItemPoExample flashSaleItemPoExample = new FlashSaleItemPoExample();
        FlashSaleItemPoExample.Criteria criteriaItem = flashSaleItemPoExample.createCriteria();
        criteriaItem.andSaleIdEqualTo(id);
        PageHelper.startPage(pageNum, pageSize);
        List<FlashSaleItemPo> flashSaleItemPos = flashSaleItemPoMapper.selectByExample(flashSaleItemPoExample);
        return flashSaleItemPos;
    }

    public List<FlashSalePo> getFlashSalesByTimeSegmentId(Long timeSegmentId) {
        FlashSalePoExample flashSalePoExample = new FlashSalePoExample();
        FlashSalePoExample.Criteria criteria = flashSalePoExample.createCriteria();
        criteria.andTimeSegIdEqualTo(timeSegmentId);
        return flashSalePoMapper.selectByExample(flashSalePoExample);
    }

    public ReturnObject deleteFlashSale(Long id){
        int ret = flashSalePoMapper.deleteByPrimaryKey(id);
        if(ret == 0){
            return new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        return new ReturnObject(ResponseCode.OK);
    }

}
