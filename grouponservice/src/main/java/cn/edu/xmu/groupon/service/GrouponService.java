package cn.edu.xmu.groupon.service;

import cn.edu.xmu.groupon.dao.GrouponDao;
import cn.edu.xmu.groupon.model.bo.Groupon;
import cn.edu.xmu.groupon.model.po.GrouponPo;
import cn.edu.xmu.groupon.model.vo.GrouponSimpleVo;
import cn.edu.xmu.groupon.model.vo.NewGrouponVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        List<GrouponPo> grouponPos = grouponDao.selectAllGroupon(shopId, timeline, spuId, pageNum, pageSize);

        return changeListIntoPage(grouponPos,pageNum,pageSize);
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
        List<GrouponPo> grouponPos = grouponDao.selectGroupon(shopId, state, spuId, beginTime, endTime, pageNum, pageSize);
        return changeListIntoPage(grouponPos,pageNum,pageSize);
    }

    @Transactional
    public ReturnObject<List> getGrouponById( Long id, Byte state) {

        try {
            List<GrouponPo> grouponPos = grouponDao.getGrouponById(id, state);
            List<VoObject> ret = new ArrayList<>(grouponPos.size());
            for(GrouponPo grouponPo:grouponPos){
                VoObject voObject = new Groupon(grouponPo).createSimpleVo();
                ret.add(voObject);
            }
            return new ReturnObject<List>(ret);
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
        * @Description: 向dao层传入vo来插入新活动
        * @Param: No such property: code for class: Script1 
        * @return: cn.edu.xmu.ooad.util.ReturnObject 
        * @Author: LJP_3424
        * @Date: 2020/12/5 23:24
    */
    @Transactional
    public ReturnObject createNewGroupon(NewGrouponVo vo,Long shopId ,Long id) {
        try {
            Long grouponId = grouponDao.insertNewGroupon(vo, shopId, id);
            GrouponPo grouponPo = grouponDao.getGrouponPo(grouponId);
            if(grouponPo != null){
                return new ReturnObject<VoObject>(new Groupon(grouponPo).createVo());
            }else {
                return new ReturnObject(ResponseCode.INTERNAL_SERVER_ERR);
            }
        }  catch (DataAccessException e) {
            // 数据库错误
            logger.error("数据库错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("发生了严重的数据库错误：%s", e.getMessage()));
        } catch (Exception e) {
            // 属未知错误
            logger.error("严重错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("发生了严重的未知错误：%s", e.getMessage()));
        }
    }

    boolean checkGroupon(Long id){
        GrouponPo grouponPo = grouponDao.getGrouponPo(id);
        if(grouponPo != null) {
            return true;
        }else{
            return false;
        }
    }
    /**
        * @Description: 传入VO更新团购活动信息
        * @Param: No such property: code for class: Script1 
        * @return: cn.edu.xmu.ooad.util.ReturnObject 
        * @Author: LJP_3424
        * @Date: 2020/12/5 23:24
    */
    @Transactional
    public ReturnObject updateGroupon(NewGrouponVo newGrouponVo, Long shopId, Long id) {
        ReturnObject retObj = null;
        try {
            if(checkGroupon(id) == false) {
                return new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
            }
            GrouponPo po = grouponDao.getGrouponPo(id);
            if (po.getBeginTime().compareTo(LocalDateTime.now()) < 0 && po.getEndTime().compareTo(LocalDateTime.now()) > 0) {
                return new ReturnObject<>(ResponseCode.PRESALE_STATENOTALLOW);
            }
            // 更新并判断
            if(grouponDao.updateGroupon(newGrouponVo,shopId,id) == false){
                return new ReturnObject(ResponseCode.INTERNAL_SERVER_ERR);
            }
            GrouponPo grouponPo = grouponDao.getGrouponPo(id);
            return new ReturnObject<VoObject>(new Groupon(grouponPo).createVo());
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
    
    /**
        * @Description: 删除团购活动
        * @Param: No such property: code for class: Script1 
        * @return: cn.edu.xmu.ooad.util.ReturnObject<java.lang.Object> 
        * @Author: LJP_3424
        * @Date: 2020/12/5 1:06
    */
    @Transactional
    public ReturnObject<Object> changeGrouponState(Long id,byte state) {
        return grouponDao.changeGrouponState(id,state);
    }


    private ReturnObject<PageInfo<VoObject>> changeListIntoPage(List<GrouponPo> grouponPos,Integer pageNum, Integer pageSize){
        List<VoObject> ret = new ArrayList<>(grouponPos.size());
        for(GrouponPo grouponPo:grouponPos){
            VoObject voObject = new Groupon(grouponPo).createSimpleVo();
            ret.add(voObject);
        }
        PageHelper.startPage(pageNum, pageSize);

        PageInfo<VoObject> of = PageInfo.of(ret);
        return new ReturnObject<PageInfo<VoObject>>(of);
    }
}
