package cn.edu.xmu.groupon.service;

import cn.edu.xmu.groupon.dao.GrouponDao;
import cn.edu.xmu.groupon.model.vo.NewGrouponVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LJP_3424
 * @create 2020-12-02 01:26
 */
@Service
public class GrouponService {
    private Logger logger = LoggerFactory.getLogger(GrouponService.class);

    @Autowired
    GrouponDao grouponDao;


    /**
     * 分页查询所有团购活动
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return ReturnObject<PageInfo < VoObject>> 分页返回团购信息
     * @author LJP_3424
     */
    public ReturnObject<PageInfo<VoObject>> selectAllGroupon(Long shopId, Byte timeline, Long spuId, Integer pageNum, Integer pageSize) {
        return grouponDao.selectAllGroupon(shopId, timeline, spuId, pageNum, pageSize);
    }


    public boolean getGrouponInActivities(Long goodsSpuId, LocalDateTime beginTime, LocalDateTime endTime) {
        return grouponDao.getGrouponInActivities(goodsSpuId, beginTime, endTime);
    }

    /**
     * 分页查询所有团购(包括下线的)
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return ReturnObject<PageInfo < VoObject>> 分页返回团购信息
     * @author LJP_3424
     */
    public ReturnObject<PageInfo<VoObject>> selectGroupon(Long shopId, Byte state, Long spuId, LocalDateTime beginTime, LocalDateTime endTime, Integer pageNum, Integer pageSize) {
        return grouponDao.selectGroupon(shopId, state, spuId, beginTime, endTime, pageNum, pageSize);
    }

    @Transactional
    public ReturnObject<List> getGrouponById(Long shopId, Long id, Byte state) {

        ReturnObject<List> grouponPos = grouponDao.getGrouponById(shopId, id, state);
        if (grouponPos != null) {
            //returnObject = new ReturnObject<GrouponPo>(grouponPo);
            return grouponPos;
        } else {
            logger.debug("findGrouponById: Not Found");
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
    }

    @Transactional
    public ReturnObject createNewGroupon(NewGrouponVo vo, Long shopId, Long id) {
        ReturnObject returnObject = grouponDao.insertNewGroupon(vo, shopId, id);
        return returnObject;
    }

    /**
     * 修改活动
     *
     * @author LJP_3424
     */
    @Transactional
    public ReturnObject updateGroupon(NewGrouponVo newGrouponVo, Long shopId, Long id) {
        ReturnObject<VoObject> retObj = grouponDao.updateGroupon(newGrouponVo, shopId, id);
        return retObj;
    }

    @Transactional
    public ReturnObject<Object> deleteGroupon(Long shopId, Long id) {
        return grouponDao.changeGrouponState(shopId, id);
    }

}
