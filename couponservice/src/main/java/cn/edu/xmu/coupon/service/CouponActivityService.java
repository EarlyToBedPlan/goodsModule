package cn.edu.xmu.coupon.service;

import cn.edu.xmu.coupon.dao.CouponActivityDao;
import cn.edu.xmu.coupon.model.bo.CouponActivity;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/29 11:57
 */
@Service
public class CouponActivityService {
    private Logger logger = LoggerFactory.getLogger(CouponActivityService.class);


    @Autowired
    CouponActivityDao couponActivityDao;
    @Transactional
    public ReturnObject getCouponActivityById(Long shopId,Long id) {
        return couponActivityDao.getCouponActivityById(shopId,id);
    }

}
