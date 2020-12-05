package cn.edu.xmu.groupon.dao;

import cn.edu.xmu.groupon.mapper.GrouponPoMapper;
import cn.edu.xmu.groupon.model.bo.Groupon;
import cn.edu.xmu.groupon.model.po.GrouponPo;
import cn.edu.xmu.groupon.model.po.GrouponPoExample;
import cn.edu.xmu.groupon.model.vo.NewGrouponVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author LJP_3424
 * @create 2020-12-01 9:59
 */
@Repository
public class GrouponDao implements InitializingBean {

    @Autowired
    private GrouponPoMapper grouponPoMapper;

    private static final Logger logger = LoggerFactory.getLogger(GrouponDao.class);


    /**
     *
     * @param goodsSpuId
     * @param beginTime
     * @param endTime
     * @return
     */
    public boolean getGrouponInActivities(Long goodsSpuId, LocalDateTime beginTime, LocalDateTime endTime) {
        GrouponPoExample example = new GrouponPoExample();
        GrouponPoExample.Criteria criteria1 = example.createCriteria();
        criteria1.andEndTimeGreaterThan(beginTime);
        criteria1.andBeginTimeLessThan(endTime);
        criteria1.andGoodsSpuIdEqualTo(goodsSpuId);
        // 这里使用select,实际上自己写count可以得到更高的效率
        List<GrouponPo> grouponPos = grouponPoMapper.selectByExample(example);
        //返回为空则不存在,返回false ,不为空说明查询到了
        if (grouponPos.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }



    /**
        * @Description: 分页筛选查询团购活动
        * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo<cn.edu.xmu.ooad.model.VoObject>>
        * @Author: LJP_3424
        * @Date: 2020/12/5 23:19
    */
    public ReturnObject<PageInfo<VoObject>> selectAllGroupon(Long shopId, Byte timeline, Long spuId, Integer pageNum, Integer pageSize) {
        GrouponPoExample example = new GrouponPoExample();
        GrouponPoExample.Criteria criteria = example.createCriteria();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime tomorrow;
        LocalDate localDate = LocalDate.now();
        switch (timeline) {
            case 0:
                criteria.andBeginTimeGreaterThan(LocalDateTime.now());
                break;
            case 1:
                // 明天
                tomorrow = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN).minusDays(-1);
                criteria.andBeginTimeBetween(tomorrow, tomorrow.minusDays(-1));
                break;
            case 2:
                criteria.andBeginTimeLessThanOrEqualTo(localDateTime);
                criteria.andEndTimeGreaterThan(localDateTime);
                break;
            case 3:
                criteria.andEndTimeLessThan(localDateTime);
                break;
        }
        if (shopId != 0) criteria.andShopIdEqualTo(shopId);
        if (spuId != 0) criteria.andGoodsSpuIdEqualTo(spuId);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        logger.debug("page = " + pageNum + "pageSize = " + pageSize);
        List<GrouponPo> grouponPos = null;
        try {
            //不加限定条件查询所有
            grouponPos = grouponPoMapper.selectByExample(example);
            List<VoObject> ret = new ArrayList<>(grouponPos.size());
            for (GrouponPo po : grouponPos) {
                Groupon groupon = new Groupon(po);
                ret.add(groupon);
            }
            PageInfo<VoObject> grouponPage = PageInfo.of(ret);
            return new ReturnObject<PageInfo<VoObject>>(grouponPage);
        } catch (DataAccessException e) {
            logger.error("selectAllGroupon: DataAccessException:" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        } catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
    }

    /**
        * @Description: 分页查询团购活动
        * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo<cn.edu.xmu.ooad.model.VoObject>>
        * @Author: LJP_3424
        * @Date: 2020/12/5 23:20
    */
    public ReturnObject<PageInfo<VoObject>> selectGroupon(Long shopId, Byte state, Long spuId, LocalDateTime beginTime, LocalDateTime endTime, Integer pageNum, Integer pageSize) {
        GrouponPoExample example = new GrouponPoExample();
        GrouponPoExample.Criteria criteria = example.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        if (state != -1) criteria.andStateEqualTo(state);
        if (spuId != 0) criteria.andGoodsSpuIdEqualTo(spuId);
        if (beginTime != null)
            criteria.andBeginTimeGreaterThanOrEqualTo(beginTime);
        if (endTime != null) criteria.andEndTimeLessThanOrEqualTo(endTime);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<GrouponPo> grouponPos = null;
        try {
            //不加限定条件查询所有
            grouponPos = grouponPoMapper.selectByExample(example);
            List<VoObject> ret = new ArrayList<>(grouponPos.size());
            for (GrouponPo po : grouponPos) {
                Groupon groupon = new Groupon(po);
                ret.add(groupon);
            }
            PageInfo<VoObject> grouponPage = PageInfo.of(ret);
            return new ReturnObject<PageInfo<VoObject>>(grouponPage);
        } catch (DataAccessException e) {
            logger.error("selectAllGroupon: DataAccessException:" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        } catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
    }

    /**
        * @Description: 根绝ID查询团购活动
        * @Param: No such property: code for class: Script1
        * @return: cn.edu.xmu.ooad.util.ReturnObject<java.util.List>
        * @Author: LJP_3424
        * @Date: 2020/12/5 23:21
    */
    public ReturnObject<List> getGrouponById(Long shopId, Long id, Byte state) {
        GrouponPo grouponPo;
        GrouponPoExample example = new GrouponPoExample();
        GrouponPoExample.Criteria criteria = example.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        criteria.andGoodsSpuIdEqualTo(id);
        if (state != -1) criteria.andStateEqualTo(state);
        logger.debug("findGrouponById: Id = " + id + ";Shop Id = " + shopId + ";state = " + state);
        List<GrouponPo> grouponPos = grouponPoMapper.selectByExample(example);

        if (grouponPos.size() != 0) {
            return new ReturnObject<>(grouponPos);
        } else {
            return null;
        }
    }

    /**
        * @Description: vo创建Po后插入
        * @return: cn.edu.xmu.ooad.util.ReturnObject<java.util.List>
        * @Author: LJP_3424
        * @Date: 2020/12/5 23:22
    */
    public ReturnObject<List> insertNewGroupon(NewGrouponVo vo, Long shopId, Long id) {
        //logger.debug(String.valueOf(bloomFilter.includeByBloomFilter("mobileBloomFilter","FAED5EEF1C8562B02110BCA3F9165CBE")));
        //by default,email/mobile are both needed
        GrouponPo grouponPo = new GrouponPo();
        grouponPo.setBeginTime(vo.getBeginTime());
        grouponPo.setEndTime(vo.getEndTime());
        grouponPo.setStrategy(vo.getStrategy());
        grouponPo.setGoodsSpuId(id);
        grouponPo.setShopId(shopId);
        grouponPo.setGmtCreated(LocalDateTime.now());
        grouponPo.setState((byte) 1);
        try {
            // 如何让ret返回主键呀！
            int ret = grouponPoMapper.insertSelective(grouponPo);
            return getGrouponById(shopId, id, (byte) -1);
        } catch (Exception e) {
            return new ReturnObject(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }

    /**
        * @Description: 修改团购活动信息
        * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject>
        * @Author: LJP_3424
        * @Date: 2020/12/5 23:22
    */
    public ReturnObject<VoObject> updateGroupon(NewGrouponVo grouponVo, Long shopId, Long id) {
        GrouponPo po = grouponPoMapper.selectByPrimaryKey(id);
        if (po.getBeginTime().compareTo(LocalDateTime.now()) < 0 && po.getEndTime().compareTo(LocalDateTime.now()) > 0) {
            return new ReturnObject<>(ResponseCode.GROUPON_STATENOTALLOW);
        }
        po.setStrategy(grouponVo.getStrategy());
        po.setBeginTime(grouponVo.getBeginTime());
        po.setEndTime(grouponVo.getEndTime());
        po.setGmtModified(LocalDateTime.now());
        ReturnObject<VoObject> retObj = null;
        try {
            int ret = grouponPoMapper.updateByPrimaryKeySelective(po);
            if (ret != 0) {
                Groupon groupon = new Groupon(grouponPoMapper.selectByPrimaryKey(id));
                retObj = new ReturnObject<>(groupon);
            } else {
                retObj = new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            }
        } catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
        return retObj;
    }
    /**
        * @Description:更改团购活动状态来逻辑删除团购活动
        * @return: cn.edu.xmu.ooad.util.ReturnObject<java.lang.Object>
        * @Author: LJP_3424
        * @Date: 2020/12/5 23:22
    */
    public ReturnObject<Object> changeGrouponState(Long shopId, Long id) {
        GrouponPo po = grouponPoMapper.selectByPrimaryKey(id);
        if (po == null) {
            logger.info("活动不存在或已被删除：id = " + id);
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        if (po.getBeginTime().compareTo(LocalDateTime.now()) < 0 && po.getEndTime().compareTo(LocalDateTime.now()) > 0) {
            return new ReturnObject<>(ResponseCode.GROUPON_STATENOTALLOW);
        }
        po.setState((byte) 4);
        ReturnObject<Object> retObj = new ReturnObject<>(ResponseCode.OK);
        int ret;
        try {
            ret = grouponPoMapper.updateByPrimaryKey(po);
            if (ret == 0) {
                logger.info("活动不存在或已被删除：id = " + id);
                retObj = new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            }
        } catch (DataAccessException e) {
            // 数据库错误
            logger.error("数据库错误：" + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("发生了严重的数据库错误：%s", e.getMessage()));
        } catch (Exception e) {
            // 属未知错误
            logger.error("严重错误：" + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("发生了严重的未知错误：%s", e.getMessage()));
        }
        return retObj;
    }
}
