package cn.edu.xmu.coupon.service;

import cn.edu.xmu.coupon.dao.CouponActivityDao;
import cn.edu.xmu.coupon.dao.CouponDao;
import cn.edu.xmu.coupon.dao.CouponSkuDao;
import cn.edu.xmu.coupon.model.bo.Coupon;
import cn.edu.xmu.coupon.model.bo.CouponActivity;
import cn.edu.xmu.coupon.model.bo.CouponSku;
import cn.edu.xmu.coupon.model.po.CouponActivityPo;
import cn.edu.xmu.coupon.model.po.CouponPo;
import cn.edu.xmu.coupon.model.po.CouponSkuPo;
//import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
//import cn.edu.xmu.goods.service.GoodsSkuServiceClass;
import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.bo.GoodsSpu;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ImgHelper;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/29 11:57
 */
@Service
public class CouponActivityServiceImpl implements CouponActivityService {
    @Autowired
    CouponActivityDao couponActivityDao;
    @Autowired
    CouponSkuDao couponSkuDao;
    @Autowired
    CouponDao couponDao;
    private Logger logger = LoggerFactory.getLogger(CouponActivityServiceImpl.class);

    @Autowired
    GoodsSkuService goodsSkuService;

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

    /**
     * @param id
     * @description: 获取优惠活动详情
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/5 15:35
     */
    @Transactional
    @Override
    public ReturnObject getCouponActivityById(Long id) {
        try {
            CouponActivityPo po = couponActivityDao.getCouponActivityById(id);
            if (po == null)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            else {
                CouponActivity couponActivity = new CouponActivity(po);
                return new ReturnObject(couponActivity.createVo());
            }
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }

    }

    @Override
    public ReturnObject<VoObject> createCouponActivity(Long shopId, Long SkuId, CouponActivity couponActivity) {
        return null;
    }

    /**
     * @param SkuId
     * @param couponActivity
     * @description:新建己方优惠活动 bug：要判断是否要插入优惠券 优惠券的生成形式还没设计 是最初就全部生成吗？
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 4:33
     */
//    @Transactional
//    @Override
//    public ReturnObject createCouponActivity(Long shopId, Long SkuId, CouponActivity couponActivity) {
//        ReturnObject ret = new ReturnObject();
//        //判断商品是否存在
//        try {
//            GoodsSkuRetVo vo = goodsSkuService.findSkuById(SkuId).getData();
//            if (vo.getId() == null)
//                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("新增优惠商品失败，优惠商品不存在 id：" + SkuId));
//            //判断商品是否属于此商铺
//            if (vo.getShop().getId() != shopId)
//                return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE, String.format("创建优惠活动失败，商品非用户店铺的商品"));
//            //判断商品同一时段是否有其他活动（不同时间段有不同活动是可以的）
//            boolean result = (Boolean) checkActivityParticipation(SkuId, couponActivity.getBeginTime(), couponActivity.getEndTime()).getData();
//            if (result) {
//                logger.debug("the Sku id=" + SkuId + " already has other activity at the same time.");
//                return new ReturnObject<>(ResponseCode.Sku_PARTICIPATE);
//            }
//            CouponActivityPo newPo = couponActivityDao.addCouponActivity(couponActivity, SkuId);
//            couponSkuDao.addCouponSku(SkuId, newPo.getId());
//            couponActivity.setId(newPo.getId());
//            VoObject retVo = couponActivity.createVo();
//            return new ReturnObject(retVo);
//        } catch (Exception e) {
//            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
//            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
//        }
//    }

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
    public ReturnObject<PageInfo<VoObject>> getCouponActivities(Long shopId, Integer timelineCode, Integer page, Integer pagesize) {
        try {
            List<CouponActivityPo> couponActivitiesPos = couponActivityDao.getCouponActivity(shopId);
            List<VoObject> couponActivityVos = new ArrayList<>(couponActivitiesPos.size());
            for (CouponActivityPo po : couponActivitiesPos) {
                if(timelineCode==null)
                {
                    CouponActivity couponActivity = new CouponActivity(po);
                    couponActivityVos.add(couponActivity);
                }
                else if(timelineCode!=null&&CouponActivity.Timeline.getTypeByTime(po.getBeginTime(),po.getEndTime()).getCode()==timelineCode)
                {
                        CouponActivity couponActivity = new CouponActivity(po);
                        couponActivityVos.add(couponActivity);
                }
                else
                    continue;
            }
            PageHelper.startPage(page, pagesize);
            PageInfo<VoObject> returnObject = new PageInfo<>(couponActivityVos);
            return new ReturnObject<>(returnObject);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }

    /**
     * @param page
     * @param pagesize
     * @description:获取本店下线活动列表
     * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo < cn.edu.xmu.ooad.model.VoObject>>
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 9:47
     */
    @Transactional
    @Override
    public ReturnObject<PageInfo<VoObject>> getInvalidCouponActivities(Integer page, Integer pagesize, Long shopId) {
        try {
            List<CouponActivityPo> couponActivitiesPos = couponActivityDao.getInvalidCouponActivity(shopId);
            List<VoObject> couponActivityVos = new ArrayList<>(couponActivitiesPos.size());
            for (CouponActivityPo po : couponActivitiesPos) {
                CouponActivity couponActivity = new CouponActivity(po);
                couponActivityVos.add(couponActivity.createVo());
            }
            PageHelper.startPage(page, pagesize);
            PageInfo<VoObject> returnObject = new PageInfo<>(couponActivityVos);
            return new ReturnObject<>(returnObject);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }

    @Override
    public ReturnObject addCouponSku(Long shopId,Long[] SkuId , Long activityId) {
        return null;
    }

    /**
     * @param SkuId
     * @param activityId
     * @description:管理员为己方已有优惠活动新增商品范围 可以批量新增
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 21:08
     */
    @Transactional
    @Override
    public ReturnObject addCouponSku(Long shopId, Long SkuId, Long activityId) {
        try {
            //1. 判断活动状态是否待上线
            CouponSkuPo couponSkuPo = couponSkuDao.getCouponSkuById(SkuId);
            CouponActivityPo couponActivityPo = couponActivityDao.getCouponActivityById(couponSkuPo.getActivityId());
            if (couponActivityPo.getState() != (byte) CouponActivity.State.WAITING.getCode())
                return new ReturnObject<>(ResponseCode.COUPONACT_STATENOTALLOW, String.format("优惠活动非待上线,不允许修改限定范围 id：" + couponSkuPo.getActivityId()));
            //2.判断商品是否存在
            //若商品不存在
            GoodsSku goodsSku=goodsSkuService
            if (goodsSkuService.findSkuById(SkuId) == null) {
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("新增优惠商品失败，优惠商品不存在 id：" + SkuId));
            }
            //3.判断此商品的shopId和管理员的shopId是否相同
            if (goodsSkuService.findSkuById(SkuId).getData().getShop().getId() != shopId)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE, String.format("新增优惠商品失败，此商品非用户店铺的商品"));
            //4.判断商品同一时段是否有其他活动（不同时间段有不同活动是可以的）
            ReturnObject<CouponActivity> couponActivityReturnObject = getCouponActivityById(activityId);
            CouponActivity couponActivity = couponActivityReturnObject.getData();
            Boolean result = (Boolean) checkActivityParticipation(SkuId, couponActivity.getBeginTime(), couponActivity.getEndTime()).getData();
            if (result) {
                logger.debug("the Sku id=" + SkuId + " already has other activity at the same time.");
                return new ReturnObject<>(ResponseCode.Sku_PARTICIPATE);
            }
            return couponSkuDao.addCouponSku(SkuId, activityId);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }


    /**
     * @param id
     * @description: 删除己方待上线的某优惠券活动对应的限定范围
     * bug:还需根据skuid获得shopId 判断操作权限
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/2 11:16
     */
    @Transactional
    @Override
    public ReturnObject deleteCouponSku(Long shopId,Long id) {
        try {
            CouponSkuPo couponSkuPo = couponSkuDao.getCouponSkuById(id);
            //SpuPo spuPo=
            if (couponSkuPo == null)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            CouponActivityPo couponActivityPo = couponActivityDao.getCouponActivityById(couponSkuPo.getActivityId());
            //判断活动状态是否为待上线
            CouponActivity.Timeline timeline=CouponActivity.Timeline.getTypeByTime(couponActivityPo.getBeginTime(),couponActivityPo.getEndTime());
            if (timeline!=CouponActivity.Timeline.WAITING||timeline!=CouponActivity.Timeline.TOMORROW_ONLINE)
                return new ReturnObject<>(ResponseCode.COUPONACT_STATENOTALLOW, String.format("优惠活动非待上线,不允许修改限定范围 id：" + couponSkuPo.getActivityId()));
            couponSkuDao.deleteCouponSku(id);
            return new ReturnObject();
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
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
    public ReturnObject<PageInfo<VoObject>> getCouponSku(Long id, Integer page, Integer pagesize) {
        try {
            CouponActivityPo couponActivityPo = couponActivityDao.getCouponActivityById(id);
            if (couponActivityPo == null)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            List<CouponSkuPo> couponSkuPos = couponSkuDao.getCouponSkuListByActivityId(id);
            List<VoObject> couponSkuVos = new ArrayList<>(couponSkuPos.size());
            for (CouponSkuPo po : couponSkuPos) {
                CouponSku couponSku = new CouponSku(po);
                couponSkuVos.add(couponSku.createSimpleVo());
            }
            PageHelper.startPage(page, pagesize);
            PageInfo<VoObject> returnObject = new PageInfo<>(couponSkuVos);
            return new ReturnObject<>(returnObject);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
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
        if (beginTime1.isAfter(endTime2) || endTime1.isBefore(beginTime2))
            return false;
        return true;
    }


    /**
     * @param SkuId
     * @param beginTime
     * @param endTime
     * @description: 判断Sku在某个时间段内是否参加了活动
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/5 17:24
     */
    public ReturnObject checkActivityParticipation(Long SkuId, LocalDateTime beginTime, LocalDateTime endTime) {
        try {
            Boolean participateCouponActivity = (Boolean) checkCouponActivityParticipation(SkuId, beginTime, endTime).getData();
            //这里需要调用三个活动的service的接口
            boolean participateGroupon = false;
            boolean participatePreSale = false;
            boolean participateFlashSale = false;
            Boolean result = participateCouponActivity && participateFlashSale && participatePreSale && participateGroupon;
            return new ReturnObject<>(result);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }


    /**
     * @param SkuId
     * @param beginTime
     * @param endTime
     * @description: 判断商品在一个时间段内 是否已经参加了优惠活动
     * @return: ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 20:06
     */
    public ReturnObject checkCouponActivityParticipation(Long SkuId, LocalDateTime beginTime, LocalDateTime endTime) {
        try {
            //检测活动商品表中，此商品是否已参加此活动
            List<CouponSkuPo> couponSkuPos = couponSkuDao.getCouponSkuListBySkuId(SkuId);
            for (CouponSkuPo po : couponSkuPos) {
                //判断商品参与的活动与即将加入的活动时间是否冲突
                CouponActivityPo couponActivityPo = couponActivityDao.getCouponActivityById(po.getActivityId());
                if (couponActivityPo.getState()!=CouponActivity.State.CANCELLED.getCode()
                        && timeClash(couponActivityPo.getBeginTime(), couponActivityPo.getEndTime(), beginTime, endTime)) {
                    return new ReturnObject<>(true);
                }
            }
            return new ReturnObject<>(false);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }
//    /**
//     * @param id
//     * @param multipartFile
//     * @description: 优惠活动上传图片
//     * @return: cn.edu.xmu.ooad.util.ReturnObject
//     * @author: Feiyan Liu
//     * @date: Created at 2020/12/1 13:39
//     */
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
    @Transactional
    @Override
    public ReturnObject updateCouponActivity(CouponActivity couponActivity) {
        try {
            CouponActivityPo po = couponActivityDao.getCouponActivityById(couponActivity.getId());
            if (po == null)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("优惠活动不存在 id：" + couponActivity.getId()));
            if (po.getShopId() != couponActivity.getShopId())
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE, String.format("无权限修改此店的优惠活动"));
            return couponActivityDao.updateCouponActivity(couponActivity);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }

    /**
     * @param id 活动id
     * @description: 管理员下线优惠活动
     * bug：直接删了优惠券吗？还是将状态改为不可用？
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/2 10:34
     */
    @Transactional
    @Override
    public ReturnObject deleteCouponActivity(Long shopId,Long id) {
        try {
            CouponActivityPo couponActivityPo=couponActivityDao.getCouponActivityById(id);
            if (couponActivityPo == null)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("优惠活动不存在 id：" + id));
            if(couponActivityPo.getShopId()!=shopId)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE, String.format("无权限删除此店的优惠活动"));
            couponActivityDao.deleteCouponActivity(id);
            //直接删了优惠券吗？还是将状态改为不可用？
            //暂时删了优惠券
            couponDao.deleteCouponByActivityId(id);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
        return new ReturnObject();
    }

    /**
     * @param id
     * @param state
     * @param page
     * @param pagesize
     * @description: 买家获取自己的优惠券列表
     * @return: cn.edu.xmu.ooad.util.ReturnObject<com.github.pagehelper.PageInfo < cn.edu.xmu.ooad.model.VoObject>>
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 17:24
     */
    @Transactional
    @Override
    public ReturnObject<PageInfo<VoObject>> getCouponByUserId(Long id, Integer state, Integer page, Integer pagesize) {
        PageInfo<VoObject> returnObject = null;
        try {
            List<CouponPo> couponPos = couponDao.getCouponListByUserId(id, state);
            List<VoObject> couponVos = new ArrayList<>(couponPos.size());
            for (CouponPo po : couponPos) {
                //根据活动id获取活动详情 和优惠券信息一起组装成vo返回
                CouponActivityPo couponActivityPo = couponActivityDao.getCouponActivityById(po.getActivityId());
                Coupon coupon = new Coupon(po, couponActivityPo);
                couponVos.add(coupon.createSimpleVo());
            }
            PageHelper.startPage(page, pagesize);
            logger.error("发生了严重的服务器内部错误：pagehelper" );
            returnObject = new PageInfo<>(couponVos);
            logger.error("发生了严重的服务器内部错误 返回失败：" );
            return new ReturnObject<>(returnObject);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误ha：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }

    }
    
    /**
     * @param id
     * @param userId
     * @description: 用户使用优惠券下单
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/4 14:46
     */
    @Override
    @Transactional
    public ReturnObject useCoupon(Long id, Long userId) {
        CouponPo couponPo=null;
        try {
            couponPo = couponDao.getCouponById(id);
            //判断优惠券id是否存在
            if(couponPo==null)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            //判断优惠券是否属于客户
            if (couponPo.getCustomerId() != userId)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE);
            //若优惠券已使用或者是失效了
            if (couponPo.getState() != (byte)Coupon.State.CLAIMED.getCode())
                return new ReturnObject<>(ResponseCode.COUPON_STATENOTALLOW);
            //若优惠券已过期或者是还未到有效时间
            if(couponPo.getBeginTime().isAfter(LocalDateTime.now())||couponPo.getEndTime().isBefore(LocalDateTime.now()))
                return new ReturnObject<>(ResponseCode.COUPON_STATENOTALLOW);
            else return couponDao.updateCouponState(id, Coupon.State.USED.getCode());
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }

    /**
     * @param userId
     * @param id
     * @description: 用户领取优惠券
     * bug：用户领取完优惠券之后要减优惠券数量 优惠券数量是0不能领取 这个方法的返回值写得有问题
     * bug: 优惠券sn的生成还没写
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 22:44
     */
    @Override
    @Transactional
    public ReturnObject userGetCoupon(Long userId, Long id) {
        CouponActivityPo couponActivityPo = couponActivityDao.getCouponActivityById(id);
        //检测活动是否有效
        if (couponActivityPo == null)
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        boolean effective=false;
        LocalDateTime now=LocalDateTime.now();
        //如果活动是在待上线或者进行中，即活动时间有效
        if (now.isAfter(couponActivityPo.getEndTime()) || now.isAfter(couponActivityPo.getBeginTime()) && now.isBefore(couponActivityPo.getEndTime())) {
            //如果活动状态不是已下线（因为有可能时间是有效，但被管理员下线了）
            if (couponActivityPo.getState().byteValue() != (byte) CouponActivity.State.CANCELLED.getCode()) {
               effective=true;
            }
        }
        if(effective==false)
            return new ReturnObject(ResponseCode.COUPONACT_STATENOTALLOW);
        //若领取优惠券的时间还没到
        if(couponActivityPo.getCouponTime().isAfter(now))
            return new ReturnObject(ResponseCode.COUPON_STATENOTALLOW);
        int quantityType = couponActivityPo.getQuantitiyType();
        ReturnObject returnObject = null;
        try {
            //判断优惠活动是否需要优惠券(即优惠券是否限量）
            //若不需要优惠券则直接领取
            if (couponActivityPo.getQuantity() == 0) {
                CouponPo couponPo = createCoupon(userId, id, couponActivityPo);
                returnObject = couponDao.addCoupon(couponPo);
            }
            //需要优惠券则需进行限量操作
            else {
                //查询用户是否领过优惠券
                boolean haveCoupon = couponDao.haveCoupon(userId, id);
                if (haveCoupon)
                    return new ReturnObject<>(ResponseCode.USER_HASCOUPON);
                if (quantityType == 0)//每人数量
                {
                    CouponPo couponPo = createCoupon(userId, id, couponActivityPo);
                    for (int i = 0; i < couponActivityPo.getQuantity(); i++)
                        returnObject = couponDao.addCoupon(couponPo);//这里返回值循环赋值？？？
                } else if (quantityType == 1) {
                    CouponPo couponPo = createCoupon(userId, id, couponActivityPo);
                    returnObject = couponDao.addCoupon(couponPo);
                    //用户领取完优惠券之后要减优惠券数量
                    //
                    //
                }
            }
            return returnObject;
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }

    }

    /**
     * @param userId
     * @param id
     * @param couponActivityPo
     * @description: 创建优惠券po对象
     * @return: cn.edu.xmu.coupon.model.po.CouponPo
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 22:44
     */
    private CouponPo createCoupon(Long userId, Long id, CouponActivityPo couponActivityPo) {
        CouponPo couponPo = new CouponPo();
        couponPo.setCouponSn("1");//要设计sn的生成算法
        couponPo.setCustomerId(userId);
        couponPo.setActivityId(id);
        couponPo.setBeginTime(couponActivityPo.getCouponTime());
        couponPo.setEndTime(couponActivityPo.getEndTime());
        couponPo.setState((byte) Coupon.State.NOT_CLAIMED.getCode());
        return couponPo;
    }

    /**
     * @param id
     * @description: 退回优惠券
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/3 23:18
     */
    @Override
    public ReturnObject returnCoupon(Long id) {
        try {
            CouponPo couponPo = couponDao.getCouponById(id);
            return couponDao.updateCouponState(id, Coupon.State.CLAIMED.getCode());
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }

    }


}
