package cn.edu.xmu.coupon.dao;

import cn.edu.xmu.coupon.mapper.CouponPoMapper;

import cn.edu.xmu.coupon.model.bo.Coupon;
import cn.edu.xmu.coupon.model.po.CouponPo;
import cn.edu.xmu.coupon.model.po.CouponPoExample;
import cn.edu.xmu.coupon.model.po.CouponSpuPo;
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
 * @date Created at 2020/12/3 17:03
 */
@Repository
public class CouponDao implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(CouponActivityDao.class);
    @Autowired
    private CouponPoMapper couponMapper;
    /**
     * 是否初始化，生成signature和加密
     */
    @Value("${couponservice.initialization}")
    private Boolean initialization;

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    /**
     * @description: 根据用户id获得优惠券列表
     * @param id
     * @param state
     * @return: java.util.List<cn.edu.xmu.coupon.model.po.CouponPo>
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 17:10
     */
    public List<CouponPo> getCouponListByUserId(Long id,Integer state)
    {
        CouponPoExample example=new CouponPoExample();
        CouponPoExample.Criteria criteria=example.createCriteria();
        criteria.andCustomerIdEqualTo(id);
        if(state!=null)
            criteria.andStateEqualTo((byte)state.intValue());
        List<CouponPo> couponPos = null;
        try {
            couponPos = couponMapper.selectByExample(example);
            if (couponPos.isEmpty()) {
                logger.debug("getCouponSpuByActivityId: Not Found");
            }
            else logger.debug("getCouponSpuByActivityId: retCouponSpu" + couponPos);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return couponPos;
    }

    /**
     * @description: 更新优惠券状态
     * @param id
     * @param state
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 19:04
     */
    public ReturnObject updateCouponState(Long id,Integer state) {
        ReturnObject returnObject = null;
        try {
            CouponPo po = couponMapper.selectByPrimaryKey(id);
            po.setState((byte) state.intValue());
            int ret = couponMapper.updateByPrimaryKeySelective(po);
            if (ret == 0) {
                logger.debug("updateCouponState: update fail. coupon id: " + id);
                returnObject = new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
            } else {
                logger.debug("updateCouponState: update success. coupon id: " + id);
                returnObject = new ReturnObject();
            }
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return returnObject;
    }

    public CouponPo getCouponById(Long id)
    {
        ReturnObject returnObject = null;
        CouponPo po=new CouponPo();
        try {
             po = couponMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return po;
    }

}
