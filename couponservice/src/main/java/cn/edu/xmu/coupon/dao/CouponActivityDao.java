package cn.edu.xmu.coupon.dao;

import cn.edu.xmu.coupon.mapper.CouponActivityPoMapper;
import cn.edu.xmu.coupon.model.bo.CouponActivity;
import cn.edu.xmu.coupon.model.po.CouponActivityPo;
import cn.edu.xmu.coupon.model.po.CouponActivityPoExample;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

//import cn.edu.xmu.goods.mapper.GoodsSpuPoMapper;
//import cn.edu.xmu.goods.model.po.GoodsSpuPo;
//import cn.edu.xmu.goods.model.po.GoodsSpuPoExample;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/29 9:54
 */
@Repository
@Mapper
public class CouponActivityDao implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(CouponActivityDao.class);
    @Autowired
    private CouponSpuDao couponSpuDao;
    @Autowired
    private CouponActivityPoMapper couponActivityMapper;
    /**
     * 是否初始化，生成signature和加密
     */
    @Value("${couponservice.initialization}")
    private Boolean initialization;

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    /**
     * @param id
     * @description: 根据活动id获取优惠活动详情
     * @return: cn.edu.xmu.ooad.util.ReturnObject<cn.edu.xmu.coupon.model.bo.CouponActivity>
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 17:04
     */
    public CouponActivityPo getCouponActivityById(Long id) {
        CouponActivityPo po = new CouponActivityPo();
        try {
            po = couponActivityMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            StringBuilder message = new StringBuilder().append("getCouponActivityById: ").append(e.getMessage());
            logger.debug(message.toString());
        }
        return po;
    }

    /**
     * @param couponActivity
     * @description:插入新的优惠活动
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 3:49
     */
    public CouponActivityPo addCouponActivity(CouponActivity couponActivity, Long spuId) {
        CouponActivityPo couponActivityPo = couponActivity.createPo();
        try {
            couponActivityMapper.insert(couponActivityPo);
            //插入成功
            logger.debug("insertCouponActivity: insert couponActivity = " + couponActivityPo.toString());
        } catch (Exception e) {
            couponActivityPo = null;
            logger.error("严重错误：" + e.getMessage());
        }
        return couponActivityPo;
    }

    /**
     * @param
     * @description:查看优惠活动列表
     * @return: com.github.pagehelper.PageInfo<cn.edu.xmu.coupon.model.po.CouponActivityPo>
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 9:10
     */
    public List<CouponActivityPo> getCouponActivity(Long shopId, CouponActivity.State state) {
        CouponActivityPoExample example = new CouponActivityPoExample();
        CouponActivityPoExample.Criteria criteria = example.createCriteria();
        if (shopId != null)
            criteria.andShopIdEqualTo(shopId);
        if (state != null)
            criteria.andStateEqualTo((byte) state.getCode());
        List<CouponActivityPo> couponActivityPos = null;
        try {
            couponActivityPos = couponActivityMapper.selectByExample(example);
            logger.debug("getOnlineCouponActivity: retCouponActivities" + couponActivityPos);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return couponActivityPos;
    }

    public int checkStateByTime(CouponActivity.State state, LocalDateTime beginTime, LocalDateTime endTime) {
        if (beginTime.isAfter(LocalDateTime.now()))
            return CouponActivity.State.WAITING.getCode();
        else if (beginTime.isBefore(LocalDateTime.now()) && endTime.isAfter(LocalDateTime.now()))
            return CouponActivity.State.ONLINE.getCode();
        else
            return CouponActivity.State.OFFLINE.getCode();
    }

    /**
     * @param shopId
     * @description:查看本店的已下线优惠活动列表
     * @return: com.github.pagehelper.PageInfo<cn.edu.xmu.coupon.model.bo.CouponActivity>
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 16:54
     */
    public List<CouponActivityPo> getInvalidCouponActivity(Long shopId) {
        CouponActivityPoExample example = new CouponActivityPoExample();
        CouponActivityPoExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo((byte) CouponActivity.State.INVALID.getCode());
        criteria.andShopIdEqualTo(shopId);
        List<CouponActivityPo> couponActivityPos = null;
        try {
            couponActivityPos = couponActivityMapper.selectByExample(example);
            logger.debug("getOfflineCouponActivity: retCouponActivities" + couponActivityPos);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return couponActivityPos;
    }
//    public boolean checkSpu(Long id)
//    {
//        if(getSpuById(id)==null)
//            return true;
//        else return false;
//    }
    /**
     * @description: 根据spuId获取spu具体信息
     * @param id
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 17:16
     */
//    public GoodsSpuPo getSpuById(Long id) {
//        GoodsSpuPo po=null;
//        try {
//            po=spuMapper.selectByPrimaryKey(id);
//        } catch (Exception e) {
//            logger.error("严重错误：" + e.getMessage());
//        }
//        return po;
//    }

    /**
     * @param couponActivity
     * @description: 更新优惠活动图片
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/1 13:37
     */
    public ReturnObject updateCouponActivityImg(CouponActivity couponActivity) {
        ReturnObject returnObject = new ReturnObject();
        CouponActivityPo couponActivityPo = new CouponActivityPo();
        couponActivityPo.setImageUrl(couponActivity.getImg());
        couponActivityPo.setId(couponActivity.getId());
        try {
            int ret = couponActivityMapper.updateByPrimaryKeySelective(couponActivityPo);
            if (ret == 0) {
                logger.debug("updateCouponActivityImg: update fail. user id: " + couponActivity.getId());
                returnObject = new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
            } else {
                logger.debug("updateCouponActivityImg: update user success : " + couponActivity.toString());
                returnObject = new ReturnObject();
            }
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return returnObject;
    }

    /**
     * @param couponActivity
     * @description: 修改优惠活动
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/1 14:55
     */
    public ReturnObject updateCouponActivity(CouponActivity couponActivity) {
        CouponActivityPo couponActivityPo = new CouponActivityPo();
        couponActivityPo.setId(couponActivity.getId());
        couponActivityPo.setName(couponActivity.getName());
        couponActivityPo.setQuantity(couponActivity.getQuantity());
        couponActivityPo.setBeginTime(couponActivity.getBeginTime());
        couponActivityPo.setEndTime(couponActivity.getEndTime());
        couponActivityPo.setStrategy(couponActivity.getStrategy());
        ReturnObject returnObject = null;
        try {
            int ret = couponActivityMapper.updateByPrimaryKeySelective(couponActivityPo);
            if (ret == 0) {
                logger.debug("updateCouponActivity: update fail. user id: " + couponActivity.getId());
                returnObject = new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
            } else {
                logger.debug("updateCouponActivity: update user success : " + couponActivity.toString());
                returnObject = new ReturnObject();
            }
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return returnObject;
    }

    /**
     * @param id
     * @description: 下线优惠活动（修改状态，逻辑删除）
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/2 10:35
     */
    public ReturnObject deleteCouponActivity(Long id) {
        ReturnObject returnObject = null;
        CouponActivityPo po = new CouponActivityPo();
        po.setId(id);
        po.setState((byte) CouponActivity.State.OFFLINE.getCode());
        try {
            int ret = couponActivityMapper.updateByPrimaryKeySelective(po);
            if (ret == 0) {
                logger.debug("deleteCouponActivity: delete fail. user id: " + id);
                returnObject = new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
            } else {
                logger.debug("deleteCouponActivity: delete user success id: " + id);
                returnObject = new ReturnObject();
            }
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return returnObject;
    }

    public Long getShopId(Long id) {
        CouponActivityPo po = null;
        try {
            po = couponActivityMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return po.getShopId();
    }
}
//    public ReturnObject getCouponSpuByActivityId(Long id)
//    {
//
//    }
