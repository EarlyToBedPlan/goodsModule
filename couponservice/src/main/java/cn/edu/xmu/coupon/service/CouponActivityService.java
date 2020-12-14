package cn.edu.xmu.coupon.service;

import cn.edu.xmu.coupon.model.bo.CouponActivity;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/29 15:33
 */
public interface CouponActivityService {

    /**
     * @description:获取所有优惠活动所有状态
     * @author: Feiyan Liu
     * @date: Created at 2020/12/9 10:27
     */
    ReturnObject<List> getCouponAllState();
    /**
     * @description:根据活动id获取活动详情（只能查看本店的）
     */
    ReturnObject getCouponActivityById(Long id,Long shopId);

    /**
     * @description:创建优惠活动
     */
    ReturnObject<VoObject> createCouponActivity(Long shopId, Long SkuId, CouponActivity couponActivity);

    /**
     * @description:查看所有上线的优惠活动（不需要登录）
     */
    ReturnObject<PageInfo<VoObject>> getCouponActivities(Long shopId, LocalDateTime beginTime, LocalDateTime endTime, Integer page, Integer pageSize);

        /**
         * @description:查看所以下线的优惠活动（只能查看本店的）
         */
    ReturnObject<PageInfo<VoObject>> getInvalidCouponActivities(Integer page, Integer pagesize, Long shopId);

    /**
     * @description:管理员为己方已有优惠活动新增商品范围
     */
    ReturnObject addCouponSku(Long shopId, Long[] SkuArray, Long activityId);

    /**
     * @description:查看优惠活动中的商品
     */
    ReturnObject<PageInfo<VoObject>> getCouponSku(Long id, Integer page, Integer pagesize);

    /**
     * @description:上传图片
     */
    ReturnObject uploadImg(Long id, MultipartFile multipartFile);

    /**
     * @description:管理员删除优惠活动
     */
    ReturnObject deleteCouponActivity(Long shopId,Long id);

    /**
     * @description:店家删除已方某优惠券活动的某限定范围
     */
    ReturnObject deleteCouponSku(Long shopId,Long id);

    /**
     * @description:修改优惠活动信息
     */
    ReturnObject updateCouponActivity(CouponActivity couponActivity);

    /**
     * @description:买家查看自己的优惠券
     */
    ReturnObject<PageInfo<VoObject>> getCouponByUserId(Long id, Integer state, Integer page, Integer pagesize);

    /**
     * @description:买家领取活动优惠券
     */
    ReturnObject userGetCoupon(Long userId, Long id);

    /**
     * @description:管理员上线优惠活动
     */
    ReturnObject putCouponActivityOnShelves(Long shopId,Long id);

    /**
     * @description:管理员下线优惠活动
     */
    ReturnObject putCouponActivityOffShelves(Long shopId,Long id);

//    /**
//     * @description:买家使用自己的优惠券
//     */
//    ReturnObject useCoupon(Long id, Long userId);

//    /**
//     * @description:优惠券退回
//     */
//    ReturnObject returnCoupon(Long id);
}
