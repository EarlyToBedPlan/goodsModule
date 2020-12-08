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

    /**
     * @param spuId
     * @param couponActivity
     * @description:新建己方优惠活动 bug：要判断是否要插入优惠券 优惠券的生成形式还没设计 是最初就全部生成吗？
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
            if (vo.getShop().getId() != shopId)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE, String.format("创建优惠活动失败，商品非用户店铺的商品"));
            //判断商品同一时段是否有其他活动（不同时间段有不同活动是可以的）
            boolean result = (Boolean) checkActivityParticipation(spuId, couponActivity.getBeginTime(), couponActivity.getEndTime()).getData();
            if (result) {
                logger.debug("the spu id=" + spuId + " already has other activity at the same time.");
                return new ReturnObject<>(ResponseCode.SPU_PARTICIPATE);
            }
            CouponActivityPo newPo = couponActivityDao.addCouponActivity(couponActivity, spuId);
            couponSpuDao.addCouponSpu(spuId, newPo.getId());
            couponActivity.setId(newPo.getId());
            VoObject retVo = couponActivity.createVo();
            return new ReturnObject(retVo);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
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
        try {
            CouponActivity.State state = CouponActivity.State.getTypeByCode(stateCode);
            List<CouponActivityPo> couponActivitiesPos = couponActivityDao.getCouponActivity(shopId, state);
            List<VoObject> couponActivityVos = new ArrayList<>(couponActivitiesPos.size());
            for (CouponActivityPo po : couponActivitiesPos) {
                CouponActivity couponActivity = new CouponActivity(po);
                couponActivityVos.add(couponActivity);
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
        try {
            //1. 判断活动状态是否待上线
            CouponSpuPo couponSpuPo = couponSpuDao.getCouponSpuById(spuId);
            CouponActivityPo couponActivityPo = couponActivityDao.getCouponActivityById(couponSpuPo.getActivityId());
            if (couponActivityPo.getState() != (byte) CouponActivity.State.WAITING.getCode())
                return new ReturnObject<>(ResponseCode.COUPONACT_STATENOTALLOW, String.format("优惠活动非待上线,不允许修改限定范围 id：" + couponSpuPo.getActivityId()));
            //2.判断商品是否存在
            //若商品不存在
            if (goodsSpuService.findSpuById(spuId) == null) {
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("新增优惠商品失败，优惠商品不存在 id：" + spuId));
            }
            //3.判断此商品的shopId和管理员的shopId是否相同
            if (goodsSpuService.findSpuById(spuId).getData().getShop().getId() != shopId)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE, String.format("新增优惠商品失败，此商品非用户店铺的商品"));
            //4.判断商品同一时段是否有其他活动（不同时间段有不同活动是可以的）
            ReturnObject<CouponActivity> couponActivityReturnObject = getCouponActivityById(activityId);
            CouponActivity couponActivity = couponActivityReturnObject.getData();
            Boolean result = (Boolean) checkActivityParticipation(spuId, couponActivity.getBeginTime(), couponActivity.getEndTime()).getData();
            if (result) {
                logger.debug("the spu id=" + spuId + " already has other activity at the same time.");
                return new ReturnObject<>(ResponseCode.SPU_PARTICIPATE);
            }
            return couponSpuDao.addCouponSpu(spuId, activityId);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }


    /**
     * @param id
     * @description: 删除己方待上线的某优惠券活动对应的限定范围
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/2 11:16
     */
    @Transactional
    @Override
    public ReturnObject deleteCouponSpu(Long id) {
        try {
            CouponSpuPo couponSpuPo = couponSpuDao.getCouponSpuById(id);
            if (couponSpuPo == null)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            CouponActivityPo couponActivityPo = couponActivityDao.getCouponActivityById(couponSpuPo.getActivityId());
            //判断活动状态是否为待上线
            if (couponActivityPo.getState() != (byte) CouponActivity.State.WAITING.getCode())
                return new ReturnObject<>(ResponseCode.COUPONACT_STATENOTALLOW, String.format("优惠活动非待上线,不允许修改限定范围 id：" + couponSpuPo.getActivityId()));
            couponSpuDao.deleteCouponSpu(id);
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
    public ReturnObject<PageInfo<VoObject>> getCouponSpu(Long id, Integer page, Integer pagesize) {
        try {
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
            PageInfo<VoObject> returnObject = new PageInfo<>(couponSpuVos);
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
                //如果活动状态不是已下线（因为有可能时间是有效，但被管理员下线了）
                if (po.getState().byteValue() != (byte) CouponActivity.State.INVALID.getCode()) {
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
     * @description: 判断spu在某个时间段内是否参加了活动
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/5 17:24
     */
    public ReturnObject checkActivityParticipation(Long spuId, LocalDateTime beginTime, LocalDateTime endTime) {
        try {
            Boolean participateCouponActivity = (Boolean) checkCouponActivityParticipation(spuId, beginTime, endTime).getData();
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
     * @param spuId
     * @param beginTime
     * @param endTime
     * @description: 判断商品在一个时间段内 是否已经参加了优惠活动
     * @return: boolean
     * @author: Feiyan Liu
     * @date: Created at 2020/11/30 20:06
     */
    public ReturnObject checkCouponActivityParticipation(Long spuId, LocalDateTime beginTime, LocalDateTime endTime) {
        try {
            //检测活动商品表中，此商品是否已参加此活动
            List<CouponSpuPo> couponSpuPos = couponSpuDao.getCouponSpuListBySpuId(spuId);
            for (CouponSpuPo po : couponSpuPos) {
                //判断商品参与的活动与即将加入的活动时间是否冲突
                CouponActivityPo couponActivityPo = couponActivityDao.getCouponActivityById(po.getActivityId());
                if (effective(po.getActivityId())
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
     * @param id
     * @description: 管理员下线优惠活动
     * bug：直接删了优惠券吗？还是将状态改为不可用？
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @author: Feiyan Liu
     * @date: Created at 2020/12/2 10:34
     */
    @Transactional
    @Override
    public ReturnObject deleteCouponActivity(Long id) {
        try {
            if (couponActivityDao.getCouponActivityById(id) == null)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("优惠活动不存在 id：" + id));
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
            returnObject = new PageInfo<>(couponVos);
            return new ReturnObject<>(returnObject);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
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
        try {
            CouponPo couponPo = couponDao.getCouponById(id);
            if (couponPo.getCustomerId() != userId)
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE, String.format("操作的优惠券非此用户优惠券" + id));
            if (couponPo.getState() != Coupon.State.available.getCode())
                return new ReturnObject<>(ResponseCode.COUPON_STATENOTALLOW, String.format("优惠券不可使用" + id));
            else return couponDao.updateCouponState(id, Coupon.State.used.getCode());
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
    public ReturnObject getCoupon(Long userId, Long id) {
        CouponActivityPo couponActivityPo = couponActivityDao.getCouponActivityById(id);
        //检测活动是否存在
        if (couponActivityPo == null)
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
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
        if (couponPo.getBeginTime().isAfter(LocalDateTime.now()))
            couponPo.setState((byte) Coupon.State.unavailable.getCode());
        else couponPo.setState((byte) Coupon.State.available.getCode());
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
            //判断优惠券是否仍有效
            //若无效则设置优惠券状态不可用
            if (couponPo.getEndTime().isBefore(LocalDateTime.now()))
                return couponDao.updateCouponState(id, Coupon.State.unavailable.getCode());
                //若有效则设置优惠券状态可用
            else return couponDao.updateCouponState(id, Coupon.State.available.getCode());
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }

    }


}
