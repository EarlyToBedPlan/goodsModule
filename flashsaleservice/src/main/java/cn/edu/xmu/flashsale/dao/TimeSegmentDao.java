package cn.edu.xmu.flashsale.dao;

import cn.edu.xmu.flashsale.mapper.TimeSegmentPoMapper;
import cn.edu.xmu.flashsale.model.po.TimeSegmentPo;
import cn.edu.xmu.flashsale.model.po.TimeSegmentPoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LJP_3424
 * @create 2020-12-08 1:00
 */
@Repository
public class TimeSegmentDao {
    @Autowired
    private TimeSegmentPoMapper timeSegmentPoMapper;

    public List<TimeSegmentPo> getTimeSegmentPoByTime(LocalDateTime localDateTime){
        /*TimeSegmentPoExample timeSegmentPoExample = new TimeSegmentPoExample();
        TimeSegmentPoExample.Criteria criteria = timeSegmentPoExample.createCriteria();
        criteria.andEndTimeGreaterThanOrEqualTo(localDateTime);
        criteria.andBeginTimeLessThanOrEqualTo(localDateTime);
        List<TimeSegmentPo> timeSegmentPos = timeSegmentPoMapper.selectByExample(timeSegmentPoExample);
        return timeSegmentPos;*/
        TimeSegmentPo timeSegmentPo = new TimeSegmentPo();
        timeSegmentPo.setId(1099L);
        List<TimeSegmentPo> timeSegmentPos = new ArrayList<>(5);
        timeSegmentPos.add(timeSegmentPo);
        timeSegmentPo = new TimeSegmentPo();
        timeSegmentPo.setId(1100L);
        timeSegmentPos.add(timeSegmentPo);
        return timeSegmentPos;
    }
    public LocalDateTime getBeginTimeByTimeSegmentId(Long id){
        LocalDateTime beginTime = timeSegmentPoMapper.selectByPrimaryKey(id).getBeginTime();
        return beginTime;
    }
}
