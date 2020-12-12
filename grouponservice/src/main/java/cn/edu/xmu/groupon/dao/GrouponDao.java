package cn.edu.xmu.groupon.dao;

import cn.edu.xmu.groupon.mapper.GrouponPoMapper;
import cn.edu.xmu.groupon.model.bo.Groupon;
import cn.edu.xmu.groupon.model.po.GrouponPo;
import cn.edu.xmu.groupon.model.po.GrouponPoExample;
import cn.edu.xmu.groupon.model.vo.GrouponSimpleVo;
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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
     * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo < cn.edu.xmu.ooad.model.VoObject>>
     * @Author: LJP_3424
     * @Date: 2020/12/5 23:19
     */
    public List<GrouponPo> selectAllGroupon(Long shopId, Byte timeline, Long spuId, Integer pageNum, Integer pageSize) {
        GrouponPoExample example = new GrouponPoExample();
        GrouponPoExample.Criteria criteria = example.createCriteria();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime tomorrow;
        if (timeline != null) {
            switch (timeline) {
                case 0:
                    //
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
        }

        if (shopId != null) criteria.andShopIdEqualTo(shopId);
        if (spuId != null) criteria.andGoodsSpuIdEqualTo(spuId);
        return selectByExample(example, pageNum, pageSize);
    }

    private List<GrouponPo> selectByExample(GrouponPoExample example, Integer pageNum, Integer pageSize) {
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        logger.debug("page = " + pageNum + "pageSize = " + pageSize);
        List<GrouponPo> grouponPos = null;
        try {
            //不加限定条件查询所有
            grouponPos = grouponPoMapper.selectByExample(example);
        } catch (DataAccessException e) {
            logger.error("selectAllGroupon: DataAccessException:" + e.getMessage());
            //return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        } catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            //return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
        return grouponPos;
    }

    /**
     * @Description: 分页查询团购活动
     * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo < cn.edu.xmu.ooad.model.VoObject>>
     * @Author: LJP_3424
     * @Date: 2020/12/5 23:20
     */
    public List<GrouponPo> selectGroupon(Long shopId, Byte state, Long spuId, LocalDateTime beginTime, LocalDateTime endTime, Integer pageNum, Integer pageSize) {
        GrouponPoExample example = new GrouponPoExample();
        GrouponPoExample.Criteria criteria = example.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        if (state != null) criteria.andStateEqualTo(state);
        if (spuId != null) criteria.andGoodsSpuIdEqualTo(spuId);
        if (beginTime != null) criteria.andBeginTimeGreaterThanOrEqualTo(beginTime);
        if (endTime != null) criteria.andEndTimeLessThanOrEqualTo(endTime);
        List<GrouponPo> grouponPos = null;
        try {
            grouponPos = selectByExample(example, pageNum, pageSize);
        } catch (DataAccessException e) {
            logger.error("selectAllGroupon: DataAccessException:" + e.getMessage());
            //return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        } catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            //return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
        return grouponPos;
    }

    /**
     * @Description: 根绝ID查询团购活动
     * @Param: No such property: code for class: Script1
     * @return: cn.edu.xmu.ooad.util.ReturnObject<java.util.List>
     * @Author: LJP_3424
     * @Date: 2020/12/5 23:21
     */
    public List<GrouponPo> getGrouponById(Long id, Byte state) {
        GrouponPoExample example = new GrouponPoExample();
        GrouponPoExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsSpuIdEqualTo(id);
        if (state != null) criteria.andStateEqualTo(state);
        logger.debug("find Groupon By Id: Id = " + id + ";state = " + state);
        List<GrouponPo> grouponPos = null;
        grouponPos = grouponPoMapper.selectByExample(example);
        return grouponPos;
    }

    /**
     * @Description: vo创建Po后插入
     * @return: cn.edu.xmu.ooad.util.ReturnObject<java.util.List>
     * @Author: LJP_3424
     * @Date: 2020/12/5 23:22
     */
    public Long insertNewGroupon(NewGrouponVo vo, Long shopId, Long id) {


        GrouponPo grouponPo = vo.createGrouponPo();
        grouponPo.setGoodsSpuId(id);
        grouponPo.setShopId(id);
        grouponPo.setGmtCreate(LocalDateTime.now());
        grouponPo.setState(Groupon.State.ON.getCode());
        grouponPoMapper.insertSelective(grouponPo);
        return grouponPo.getId();
    }

    public GrouponPo getGrouponPo(Long id) {
        return grouponPoMapper.selectByPrimaryKey(id);
    }


    /**
     * @Description: 修改团购活动信息
     * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.ooad.model.VoObject>
     * @Author: LJP_3424
     * @Date: 2020/12/5 23:22
     */
    public boolean updateGroupon(NewGrouponVo grouponVo, Long shopId, Long id) {
        GrouponPo po = grouponPoMapper.selectByPrimaryKey(id);
        po.setStrategy(grouponVo.getStrategy());
        po.setBeginTime(grouponVo.getBeginTime());
        po.setEndTime(grouponVo.getEndTime());
        po.setGmtModified(LocalDateTime.now());
        ReturnObject<VoObject> retObj = null;
        int ret = grouponPoMapper.updateByPrimaryKeySelective(po);
        if (ret != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description:更改团购活动状态来逻辑删除团购活动
     * @return: cn.edu.xmu.ooad.util.ReturnObject<java.lang.Object>
     * @Author: LJP_3424
     * @Date: 2020/12/5 23:22
     */
    public ReturnObject<Object> changeGrouponState(Long id,byte state) {
        GrouponPo po = grouponPoMapper.selectByPrimaryKey(id);

        // 不存在或删除态拒绝
        if (po == null || po.getState() == Groupon.State.DELETE.getCode()) {
            logger.info("活动不存在或已被删除：id = " + id);
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        // 状态重复或者 上线态转删除
        if (po.getState() == state || (state == Groupon.State.DELETE.getCode() && po.getState() == Groupon.State.ON.getCode())) {
            return new ReturnObject<>(ResponseCode.PRESALE_STATENOTALLOW);
        }
        // po.setState(state);
        // 这里采用部分更新,防止数据更改
        GrouponPo grouponPo = new GrouponPo();
        grouponPo.setId(po.getId());
        grouponPo.setState(state);
        ReturnObject<Object> retObj = new ReturnObject<>(ResponseCode.OK);
        int ret = 0;
        try {
            ret = grouponPoMapper.updateByPrimaryKeySelective(grouponPo);
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
        if (ret == 0) {
            logger.info("活动不存在或已被删除：id = " + id);
            retObj = new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        return retObj;
    }
}
