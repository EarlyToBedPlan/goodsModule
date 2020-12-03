package cn.edu.xmu.coupon.service;

import cn.edu.xmu.coupon.dao.CouponActivityDao;
import cn.edu.xmu.coupon.dao.CouponDao;
import cn.edu.xmu.coupon.dao.CouponSpuDao;
import cn.edu.xmu.coupon.model.bo.Coupon;
import cn.edu.xmu.coupon.model.bo.CouponActivity;
import cn.edu.xmu.coupon.model.bo.CouponSpu;
import cn.edu.xmu.coupon.model.po.CouponActivityPo;
import cn.edu.xmu.coupon.model.po.CouponPo;
import cn.edu.xmu.coupon.model.po.CouponSpuPo;
import cn.edu.xmu.coupon.model.po.CouponSpuPoExample;
//import cn.edu.xmu.goods.model.po.GoodsSpuPo;
import cn.edu.xmu.coupon.model.vo.CouponActivityRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSpuRetVo;
import cn.edu.xmu.goods.service.GoodsSpuService;
import cn.edu.xmu.goods.service.GoodsSpuServiceClass;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ImgHelper;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mysql.cj.conf.PropertyKey.logger;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/29 11:57
 */
@Service
public class CouponActivityServiceImpl implements CouponActivityService {
    @Autowired
    CouponActivityDao couponActivityDao;
    @Autowired
    CouponSpuDao couponSpuDao;
    @Autowired
    CouponDao couponDao;
    GoodsSpuServiceClass goodsSpuService = new GoodsSpuServiceClass();
    private Logger logger = LoggerFactory.getLogger(CouponActivityServiceImpl.class);

    /**
     * @param obj
     * @param clazz
     * @description: 将List类型的ReturnObject转为List
     * @return: java.util.List<T>
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 17:38
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    //    /**
//     * @author 24320182203218
//     **/
//    @Value("${couponservice.imglocation}")
//    private String imgLocation;
//
//    @Value("${couponservice.dav.sername}")
//    private String davUserName;
//
//    @Value("${couponservice.dav.password}")
//    private String davPassword;
//
//    @Value("${couponservice.dav.baseUrl}")
//    private String baseUrl;
    @Transactional
    @Override
    public ReturnObject getCouponActivityById(Long id) {
        CouponActivityPo po = couponActivityDao.getCouponActivityById(id);
        if (po == null)
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        else {
            CouponActivity couponActivity = new CouponActivity(po);
            couponActivity.createVo();
            return new ReturnObject(couponActivity.createVo());
        }
    }

    /**
     * @param spuId
     * @param couponActivity
     * @description:新建己方优惠活动 bug：要判断是否要插入优惠券 优惠券也还没设计..
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 4:33
     */
    @Transactional
    @Override
    public ReturnObject createCouponActivity(Long shopId, Long spuId, CouponActivity couponActivity) {
        ReturnObject ret = new ReturnObject();
        //判断商品是否存在
        try {
            GoodsSpuRetVo vo = goodsSpuService.findSpuById(spuId).getData();
            if (vo.getId() == null)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("新增优惠商品失败，优惠商品不存在 id：" + spuId));
            //判断商品是否属于此商铺
            if (goodsSpuService.getShopBySpuId(spuId) != shopId)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE, String.format("创建优惠活动失败，商品非用户店铺的商品"));
            //判断商品同一时段是否有其他活动（不同时间段有不同活动是可以的）
            //这里需要调用三个活动的service的接口
            boolean participateGroupon = false;
            boolean participatePreSale = false;
            boolean participateFlashSale = false;
            if (checkParticipationByTime(spuId, couponActivity.getBeginTime(), couponActivity.getEndTime())
                    || participateFlashSale || participateGroupon || participatePreSale) {
                logger.debug("the spu id=" + spuId + " already has other activity at the same time.");
                return new ReturnObject<>(ResponseCode.SPU_PARTICIPATE);
            }
            CouponActivityPo newPo = couponActivityDao.addCouponActivity(couponActivity, spuId);
            CouponActivity couponActivity1 = new CouponActivity(newPo);
            couponSpuDao.addCouponSpu(spuId, couponActivity1.getId());
            ret = new ReturnObject(couponActivity1.createVo());
        } catch (Exception e) {
            ret = new ReturnObject(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("发生了严重的服务器内部错误：%s", e.getMessage()));
        }
        return ret;
    }

    /**
     * @param page
     * @param pagesize
     * @description:获取上线活动列表 无需登录
     * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo < cn.edu.xmu.ooad.model.VoObject>>
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 9:43
     */
    @Transactional
    @Override
    public ReturnObject<PageInfo<VoObject>> getCouponActivities(Long shopId, Integer stateCode, Integer page, Integer pagesize) {
        PageHelper.startPage(page, pagesize);
        CouponActivity.State state = CouponActivity.State.getTypeByCode(stateCode);
        List<CouponActivityPo> couponActivitiesPos = couponActivityDao.getCouponActivity(shopId, state);
        List<VoObject> couponActivities = new ArrayList<>(couponActivitiesPos.size());
        for (CouponActivityPo po : couponActivitiesPos) {
            CouponActivity couponActivity = new CouponActivity(po);
            couponActivities.add(couponActivity);
        }
        return getPageInfoReturnObject(new PageInfo(couponActivities));
    }
    /**
     * @param page
     * @param pagesize
     * @description:获取本店下线活动列表
     * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo < cn.edu.xmu.ooad.model.VoObject>>
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 9:47
     */
//    @Transactional
//    @Override
//    public ReturnObject<PageInfo<VoObject>> getInvalidCouponActivities(Integer page, Integer pagesize, Long shopId) {
//
//        List<CouponActivity> couponActivities = castList(couponActivityDao.getInvalidCouponActivity(shopId), CouponActivity.class);
//        PageHelper.startPage(page, pagesize);
//        return getPageInfoReturnObject(new PageInfo<>(couponActivities));
//    }

    @Override
    public List<VoObject> getInvalidCouponActivities(Integer page, Integer pagesize, Long shopId) {
        return null;
    }

    /**
     * @param couponActivities
     * @description: 将结果分页返回
     * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo < cn.edu.xmu.ooad.model.VoObject>>
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 17:40
     */
    private ReturnObject<PageInfo<VoObject>> getPageInfoReturnObject(PageInfo<CouponActivity> couponActivities) {
        List<VoObject> couponActivitiesVo = couponActivities.getList().stream().map(CouponActivity::createSimpleVo).collect(Collectors.toList());
        PageInfo<VoObject> returnObject = new PageInfo<>(couponActivitiesVo);
        returnObject.setPages(couponActivities.getPages());
        returnObject.setPageNum(couponActivities.getPageNum());
        returnObject.setPageSize(couponActivities.getPageSize());
        returnObject.setTotal(couponActivities.getTotal());
        return new ReturnObject<>(returnObject);
    }

    /**
     * @param spuId
     * @param activityId
     * @description:管理员为己方已有优惠活动新增商品范围
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 21:08
     */
    @Transactional
    @Override
    public ReturnObject addCouponSpu(Long shopId, Long spuId, Long activityId) {
        //1. 判断活动是否有效，若已经无效则不操作
        if (!effective(activityId)) {
            logger.debug("the coupon activity id=" + activityId + "is offline.");
            return new ReturnObject<>(ResponseCode.COUPONACT_STATENOTALLOW);
        }
        //2.判断商品是否存在
        //若商品不存在
        if (goodsSpuService.findSpuById(spuId) == null) {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("新增优惠商品失败，优惠商品不存在 id：" + spuId));
        }
        //3.判断此商品的shopId和管理员的shopId是否相同
        if (goodsSpuService.getShopBySpuId(spuId) != shopId)
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE, String.format("新增优惠商品失败，此商品非用户店铺的商品"));
        //4.判断商品同一时段是否有其他活动（不同时间段有不同活动是可以的）
        //这里需要调用三个活动的service的接口
        boolean participateGroupon = false;
        boolean participatePreSale = false;
        boolean participateFlashSale = false;
        ReturnObject<CouponActivity> couponActivityReturnObject = getCouponActivityById(activityId);
        CouponActivity couponActivity = couponActivityReturnObject.getData();
        if (checkParticipationByTime(spuId, couponActivity.getBeginTime(), couponActivity.getEndTime())
                || participateFlashSale || participateGroupon || participatePreSale) {
            logger.debug("the spu id=" + spuId + " already has other activity at the same time.");
            return new ReturnObject<>(ResponseCode.SPU_PARTICIPATE);
        }
        return couponSpuDao.addCouponSpu(spuId, activityId);
    }

    /**
     * @param id
     * @param page
     * @param pagesize
     * @description: 查看优惠活动中的商品
     * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo < cn.edu.xmu.ooad.model.VoObject>>
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 22:11
     */
    @Transactional
    @Override
    public ReturnObject<PageInfo<VoObject>> getCouponSpu(Long id, Integer page, Integer pagesize) {
        CouponActivityPo couponActivityPo = couponActivityDao.getCouponActivityById(id);
        if (couponActivityPo == null)
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("查询的优惠活动不存在 id：" + id));
        List<CouponSpuPo> couponSpuPos = couponSpuDao.getCouponSpuListByActivityId(id);
        List<VoObject> couponSpuVos = new ArrayList<>(couponSpuPos.size());
        for (CouponSpuPo po : couponSpuPos) {
            CouponSpu couponSpu = new CouponSpu(po);
            couponSpuVos.add(couponSpu.createSimpleVo());
        }
        PageHelper.startPage(page, pagesize);
        PageInfo<VoObject> couponSpuPage = new PageInfo<>(couponSpuVos);
        PageInfo<VoObject> returnObject = new PageInfo<>(couponSpuVos);
        returnObject.setPages(couponSpuPage.getPages());
        returnObject.setPageNum(couponSpuPage.getPageNum());
        returnObject.setPageSize(couponSpuPage.getPageSize());
        returnObject.setTotal(couponSpuPage.getTotal());
        return new ReturnObject<>(returnObject);
    }

    @Override
    public ReturnObject uploadImg(Long id, MultipartFile multipartFile) {
        return null;
    }

    /**
     * @param id1
     * @param id2
     * @description: 判断两个优惠活动是否冲突
     * @return: boolean
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 19:29
     */
    public boolean activityClash(Long id1, Long id2) {
        CouponActivityPo po1 = couponActivityDao.getCouponActivityById(id1);
        CouponActivityPo po2 = couponActivityDao.getCouponActivityById(id2);
        //判断活动时间区间是否相交
        return timeClash(po1.getBeginTime(), po1.getEndTime(), po2.getBeginTime(), po2.getEndTime());
    }

    /**
     * @param beginTime1
     * @param endTime1
     * @param beginTime2
     * @param endTime2
     * @description: 判断两个时间区间是否相交
     * @return: boolean
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 20:02
     */
    public boolean timeClash(LocalDateTime beginTime1, LocalDateTime endTime1, LocalDateTime beginTime2, LocalDateTime endTime2) {
        if (beginTime1.isBefore(endTime2) || endTime1.isBefore(beginTime2))
            return false;
        return true;
    }

    /**
     * @param id
     * @description: 根据活动id判断活动是否有效
     * @return: boolean
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 19:08
     */
    public boolean effective(Long id) {
        LocalDateTime now = LocalDateTime.now();
        try {
            CouponActivityPo po = couponActivityDao.getCouponActivityById(id);
            //如果活动是在待上线或者进行中，即活动时间有效
            if (now.isAfter(po.getEndTime()) || now.isAfter(po.getBeginTime()) && now.isBefore(po.getEndTime())) {
                //如果活动状态不是已结束（因为有可能时间是有效，但被管理员下线了）
                if (po.getState().byteValue() != (byte) CouponActivity.State.OFFLINE.getCode()) {
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误严重错误：" + e.getMessage());
        }
        return false;
    }

    /**
     * @param spuId
     * @param beginTime
     * @param endTime
     * @description: 判断商品在同一个时间段内 是否已经参加了优惠活动
     * @return: boolean
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 20:06
     */
    public boolean checkParticipationByTime(Long spuId, LocalDateTime beginTime, LocalDateTime endTime) {
        //检测活动商品表中，此商品是否已参加此活动
        CouponSpuPoExample example = new CouponSpuPoExample();
        CouponSpuPoExample.Criteria criteria = example.createCriteria();
        criteria.andSpuIdEqualTo(spuId);
        List<CouponSpuPo> couponSpuPos = couponSpuDao.getCouponSpuListBySpuId(spuId);
        for (CouponSpuPo po : couponSpuPos) {
            //判断商品参与的活动与即将加入的活动时间是否冲突
            CouponActivityPo couponActivityPo = couponActivityDao.getCouponActivityById(po.getActivityId());
            if (effective(po.getActivityId())
                    && timeClash(couponActivityPo.getBeginTime(), couponActivityPo.getEndTime(), beginTime, endTime))
                return true;
        }
        return false;
    }
    /**
     * @param id
     * @param multipartFile
     * @description: 优惠活动上传图片
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/1 13:39
     */
//    @Transactional
//    @Override
//    public ReturnObject uploadImg(Long id, MultipartFile multipartFile) {
//        CouponActivity couponActivity = new CouponActivity(couponActivityDao.getCouponActivityById(id));
//        if (couponActivity == null) {
//            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("查询的优惠活动不存在 id：" + id));
//        }
//        ReturnObject returnObject = new ReturnObject();
//        try {
//            returnObject = ImgHelper.remoteSaveImg(multipartFile, 2, davUserName, davPassword, baseUrl);
//
//            //文件上传错误
//            if (returnObject.getCode() != ResponseCode.OK) {
//                logger.debug(returnObject.getErrmsg());
//                return returnObject;
//            }
//            String oldFileName = couponActivity.getImg();
//
//            couponActivity.setImg(returnObject.getData().toString());
//            ReturnObject updateReturnObject = couponActivityDao.updateCouponActivityImg(couponActivity);
//
//            //数据库更新失败，需删除新增的图片
//            if (updateReturnObject.getCode() == ResponseCode.RESOURCE_ID_NOTEXIST) {
//                ImgHelper.deleteRemoteImg(returnObject.getData().toString(), davUserName, davPassword, baseUrl);
//                return updateReturnObject;
//            }
//
//            //数据库更新成功需删除旧图片，未设置则不删除
//            if (oldFileName != null) {
//                ImgHelper.deleteRemoteImg(oldFileName, davUserName, davPassword, baseUrl);
//            }
//        } catch (IOException e) {
//            logger.debug("uploadImg: I/O Error:" + baseUrl);
//            return new ReturnObject(ResponseCode.FILE_NO_WRITE_PERMISSION);
//        }
//        return returnObject;
//    }

    /**
     * @param couponActivity 修改内容
     * @description: 修改优惠活动信息
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/2 10:25
     */
    public ReturnObject updateCouponActivity(CouponActivity couponActivity) {
        if (couponActivityDao.getCouponActivityById(couponActivity.getId()) == null)
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("优惠活动不存在 id：" + couponActivity.getId()));
        if (couponActivityDao.getShopId(couponActivity.getId()) != couponActivity.getShopId())
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE, String.format("无权限修改此店的优惠活动"));
        return couponActivityDao.updateCouponActivity(couponActivity);
    }

    /**
     * @param id
     * @description: 管理员下线优惠活动
     * bug：没有下线已发行未用的优惠券
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/2 10:34
     */
    public ReturnObject deleteCouponActivity(Long id) {
        if (couponActivityDao.getCouponActivityById(id) == null)
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("优惠活动不存在 id：" + id));
        return couponActivityDao.deleteCouponActivity(id);
    }

    /**
     * @param id
     * @description: 删除己方待上线的某优惠券活动对应的限定范围
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/2 11:16
     */
    public ReturnObject deleteCouponSpu(Long id) {
        return new ReturnObject();
    }


    /**
     * @description: 买家获取自己的优惠券列表
     * @param id
     * @param state
     * @param page
     * @param pagesize
     * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo<cn.edu.xmu.ooad.model.VoObject>>
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 17:24
     */
    @Transactional
    @Override
    public ReturnObject<PageInfo<VoObject>> getCouponByUserId(Long id,Integer state ,Integer page, Integer pagesize) {
        List<CouponPo> couponPos = couponDao.getCouponListByUserId(id,state);
        List<VoObject> couponVos = new ArrayList<>(couponPos.size());
        for (CouponPo po : couponPos) {
            CouponActivityPo couponActivityPo=couponActivityDao.getCouponActivityById(po.getActivityId());
            Coupon coupon = new Coupon(po,couponActivityPo);
            couponVos.add(coupon.createSimpleVo());
        }
        PageHelper.startPage(page, pagesize);
        PageInfo<VoObject> couponSpuPage = new PageInfo<>(couponVos);
        PageInfo<VoObject> returnObject = new PageInfo<>(couponVos);
        returnObject.setPages(couponSpuPage.getPages());
        returnObject.setPageNum(couponSpuPage.getPageNum());
        returnObject.setPageSize(couponSpuPage.getPageSize());
        returnObject.setTotal(couponSpuPage.getTotal());
        return new ReturnObject<>(returnObject);
    }


    @Override
    @Transactional
    public ReturnObject useCoupon(Long id,Long userId)
    {
        CouponPo couponPo=couponDao.getCouponById(id);
        if(couponPo.getCustomerId()!=userId)
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE, String.format("操作的优惠券非此用户优惠券" + id));
        if(couponPo.getState()!=Coupon.State.available.getCode())
            return new ReturnObject<>(ResponseCode.COUPON_STATENOTALLOW, String.format("优惠券不可使用" + id));
        else return couponDao.updateCouponState(id,Coupon.State.used.getCode());
    }


}
