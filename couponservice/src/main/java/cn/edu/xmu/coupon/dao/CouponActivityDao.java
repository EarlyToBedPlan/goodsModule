package cn.edu.xmu.coupon.dao;

import cn.edu.xmu.coupon.mapper.CouponActivityPoMapper;
import cn.edu.xmu.coupon.mapper.CouponPoMapper;
import cn.edu.xmu.coupon.model.bo.CouponActivity;
import cn.edu.xmu.coupon.model.po.CouponActivityPo;
import cn.edu.xmu.coupon.model.po.CouponActivityPoExample;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/29 9:54
 */
@Repository
public class CouponActivityDao implements InitializingBean {

        @Autowired
        private CouponPoMapper couponPoMapper;

        @Autowired
        private CouponActivityPoMapper couponActivityPoMapper;

        private static final Logger logger = LoggerFactory.getLogger(CouponActivityDao.class);

        /**
         * 是否初始化，生成signature和加密
         */
        @Value("${privilegeservice.initialization}")
        private Boolean initialization;

    @Override
    public void afterPropertiesSet() throws Exception {

    }
    public ReturnObject<CouponActivity> getCouponActivityById(Long shopId,Long id) {
        CouponActivityPoExample example=new CouponActivityPoExample();
        CouponActivityPoExample.Criteria criteria=example.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        criteria.andIdEqualTo(id);
        List<CouponActivityPo> couponActivityPos = null;
        try {
            couponActivityPos=couponActivityPoMapper.selectByExample(example);
        } catch (DataAccessException e) {
            StringBuilder message = new StringBuilder().append("getCouponActivityById: ").append(e.getMessage());
            logger.error(message.toString());
        }

        if (null == couponActivityPos) {
            logger.debug("getCouponActivityById: Not Found");
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        } else {
            CouponActivity couponActivity=new CouponActivity(couponActivityPos.get(0));
                return new ReturnObject<>(couponActivity);
            }
        }
    }
