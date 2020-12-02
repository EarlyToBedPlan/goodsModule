package cn.edu.xmu.coupon.dao;

import cn.edu.xmu.coupon.mapper.CouponSpuPoMapper;
import cn.edu.xmu.coupon.model.bo.CouponSpu;
import cn.edu.xmu.coupon.model.po.CouponSpuPo;
import cn.edu.xmu.coupon.model.po.CouponSpuPoExample;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Feiyan Liu
 * @date Created at 2020/12/2 10:51
 */
@Repository
public class CouponSpuDao implements InitializingBean {
    @Autowired
    private CouponSpuPoMapper couponSpuMapper;
    private static final Logger logger = LoggerFactory.getLogger(CouponActivityDao.class);
    /**
     * 是否初始化，生成signature和加密
     */
    @Value("${couponservice.initialization}")
    private Boolean initialization;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    /**
     * @param spuId
     * @param activityId
     * @description:加入新的优惠商品
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 4:11
     */
    public ReturnObject addCouponSpu(Long spuId, Long activityId) {
        ReturnObject retObj = null;
        //数据插入优惠商品表
        CouponSpu couponSpu = new CouponSpu();
        couponSpu.setSpuId(spuId);
        couponSpu.setActivityId(activityId);
        CouponSpuPo couponSpuPo = couponSpu.createPo();
        try {
            couponSpuMapper.insert(couponSpuPo);
            //插入成功
            logger.debug("insertCouponSpu: insert couponSpu = " + couponSpuPo.toString());
            retObj = new ReturnObject<>(couponSpu);
        } catch (Exception e) {
            logger.error("严重错误：" + e.getMessage());
            return new ReturnObject(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("发生了严重的服务器内部错误：%s", e.getMessage()));
        }
        return retObj;
    }

    /**
     * @param id
     * @description: 根据活动id获取活动商品列表
     * @return: cn.edu.xmu.ooad.util.ReturnObject<java.util.List>
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 22:17
     */
    public List<CouponSpuPo> getCouponSpuListByActivityId(Long id) {
        CouponSpuPoExample example = new CouponSpuPoExample();
        CouponSpuPoExample.Criteria criteria = example.createCriteria();
        criteria.andActivityIdEqualTo(id);
        List<CouponSpuPo> couponSpuPos = null;
        try {
            couponSpuPos = couponSpuMapper.selectByExample(example);
            if (couponSpuPos.isEmpty()) {
                logger.debug("getCouponSpuByActivityId: Not Found");
            }
            logger.debug("getCouponSpuByActivityId: retCouponSpu" + couponSpuPos);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return couponSpuPos;
    }

    public List<CouponSpuPo> getCouponSpuListBySpuId(Long id) {
        CouponSpuPoExample example = new CouponSpuPoExample();
        CouponSpuPoExample.Criteria criteria = example.createCriteria();
        criteria.andSpuIdEqualTo(id);
        List<CouponSpuPo> couponSpuPos = null;
        try {
            couponSpuPos = couponSpuMapper.selectByExample(example);
            if (couponSpuPos.isEmpty()) {
                logger.debug("getCouponSpuByActivityId: Not Found");
            }
            logger.debug("getCouponSpuByActivityId: retCouponSpu" + couponSpuPos);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return couponSpuPos;
    }

    public Long getActivityId(Long id) {
        CouponSpuPo po = null;
        try {
            po = couponSpuMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return po.getActivityId();
    }

    public ReturnObject deleteCouponSpu(Long id) {
        ReturnObject returnObject = null;
        try {
            int ret = couponSpuMapper.deleteByPrimaryKey(id);
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

}
