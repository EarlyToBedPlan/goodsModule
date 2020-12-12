package cn.edu.xmu.presale.dao;

import cn.edu.xmu.goods.dao.GoodsSkuDao;
import cn.edu.xmu.goods.model.po.GoodsSkuPo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.presale.mapper.PreSalePoMapper;
import cn.edu.xmu.presale.model.bo.PreSale;
import cn.edu.xmu.presale.model.po.PreSalePo;
import cn.edu.xmu.presale.model.po.PreSalePoExample;
import cn.edu.xmu.presale.model.vo.NewPreSaleVo;
import cn.edu.xmu.shop.dao.ShopDao;
import cn.edu.xmu.shop.model.po.ShopPo;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author LJP_3424
 * @create 2020-12-01 9:59
 */
@Repository
public class PreSaleDao implements InitializingBean {

    @Autowired
    GoodsSkuDao goodsSkuDao;
    @Autowired
    ShopDao shopDao;

    @Autowired
    private PreSalePoMapper preSalePoMapper;

    private static final Logger logger = LoggerFactory.getLogger(PreSaleDao.class);


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public boolean getPreSaleInActivities(Long goodsSpuId, LocalDateTime beginTime, LocalDateTime endTime) {
        PreSalePoExample example = new PreSalePoExample();
        PreSalePoExample.Criteria criteria1 = example.createCriteria();
        criteria1.andEndTimeGreaterThan(beginTime);
        criteria1.andBeginTimeLessThan(endTime);
        criteria1.andGoodsSkuIdEqualTo(goodsSpuId);
        criteria1.andStateEqualTo(PreSale.State.ON.getCode());
        // 这里使用select,实际上自己写count可以得到更高的效率
        List<PreSalePo> preSalePos = preSalePoMapper.selectByExample(example);
        //返回为空则不存在,返回false ,不为空说明查询到了
        if (preSalePos.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public ReturnObject<List> getPreSaleBySpuId( Long id, Byte state) {
        PreSalePoExample example = new PreSalePoExample();
        PreSalePoExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsSkuIdEqualTo(id);
        criteria.andStateNotEqualTo(PreSale.State.DELETE.getCode());
        if (state != null) criteria.andStateEqualTo(state);
        logger.debug("find PreSale By Id: Id = " + id + ";state = " + state);
        List<PreSalePo> preSalePos = null;
        try {
            preSalePos = preSalePoMapper.selectByExample(example);
        } catch (DataAccessException e) {
            logger.error("selectAllPreSale: DataAccessException:" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        } catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
        return new ReturnObject<>(preSalePos);
    }

    /**
     * 分页查询所有预售信息
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return ReturnObject<List> 活动列表
     * @author LJP_3424
     */
    public List<PreSalePo> selectAllPreSale(Long shopId, Byte timeline, Long spuId, Integer pageNum, Integer pageSize) {
        PreSalePoExample example = new PreSalePoExample();
        PreSalePoExample.Criteria criteria = example.createCriteria();
        LocalDateTime tomorrow;
        if(timeline != null) {
            switch (timeline) {
                case 0:
                    //
                    criteria.andBeginTimeGreaterThan(LocalDateTime.now());
                    break;
                case 1:
                    // 明天
                    tomorrow = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIN).minusDays(-1);
                    criteria.andBeginTimeBetween(tomorrow, tomorrow.minusDays(-1));
                    break;
                case 2:
                    criteria.andBeginTimeLessThanOrEqualTo(LocalDateTime.now());
                    criteria.andEndTimeGreaterThan(LocalDateTime.now());
                    break;
                case 3:
                    criteria.andEndTimeLessThan(LocalDateTime.now());
                    break;
            }
        }
        if (shopId != null) criteria.andShopIdEqualTo(shopId);
        if (spuId != null) criteria.andGoodsSkuIdEqualTo(spuId);

        criteria.andStateEqualTo(PreSale.State.ON.getCode());
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        logger.debug("page = " + pageNum + "pageSize = " + pageSize);
        List<PreSalePo> preSalePos = null;
        try {
            //不加限定条件查询所有
            preSalePos = preSalePoMapper.selectByExample(example);
        } catch (DataAccessException e) {
            logger.error("selectAllPreSale: DataAccessException:" + e.getMessage());
            //return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        } catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            //return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
        return preSalePos;
    }

    /**
     *
     *
     * @param vo vo对象
     * @return ReturnObject
     * createdBy: LJP_3424
     */
    public PreSalePo createNewPreSaleByVo(NewPreSaleVo vo, Long shopId, Long id) {
        PreSalePo preSalePo = vo.createPreSalePo();
        preSalePo.setState(PreSale.State.ON.getCode());
        preSalePo.setGoodsSkuId(id);
        preSalePo.setShopId(shopId);
        preSalePo.setGmtCreate(LocalDateTime.now());
        ReturnObject<VoObject> retObj = null;
        try {
            int insertResult = preSalePoMapper.insert(preSalePo);
            if(insertResult != 0){
                return preSalePo;
            }
        } catch (DataAccessException e) {
            // 数据库错误
            logger.error("数据库错误：" + e.getMessage());
        } catch (Exception e) {
            // 属未知错误
            logger.error("严重错误：" + e.getMessage());
        }
        return null;
    }


    /**
     * 修改一个预售活动信息
     *
     * @author LJP_3424
     */
    public Long updatePreSale(NewPreSaleVo preSaleVo, Long id) {

        PreSalePo preSalePo = preSaleVo.createPreSalePo();
        preSalePo.setId(id);
        int ret;
        preSalePo.setGmtModified(LocalDateTime.now());
        try {
            ret = preSalePoMapper.updateByPrimaryKeySelective(preSalePo);
        }  catch (DataAccessException e) {
            // 数据库错误
            logger.error("数据库错误：" + e.getMessage());
           /* retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("发生了严重的数据库错误：%s", e.getMessage()));*/
        } catch (Exception e) {
            // 属未知错误
            logger.error("严重错误：" + e.getMessage());
            /*retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("发生了严重的未知错误：%s", e.getMessage()));*/
        }
        // 这里可能存在空指针异常,在上一层捕获
        return preSalePo.getId();
    }


    /**
     * @Description:  
     *
     * @param id
     * @param state 
     * @return: cn.edu.xmu.ooad.util.ReturnObject<java.lang.Object> 
     * @Author: LJP_3424
     * @Date: 2020/12/11 16:26
     */
    public ReturnObject<Object> changePreSaleState(Long id, Byte state) {
        PreSalePo po = preSalePoMapper.selectByPrimaryKey(id);

        // 不存在或删除态拒绝
        if (po == null || po.getState() == PreSale.State.DELETE.getCode()) {
            logger.info("活动不存在或已被删除：id = " + id);
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        // 状态重复或者 上线态转删除
        if (po.getState() == state || (state == PreSale.State.DELETE.getCode() && po.getState() == PreSale.State.ON.getCode())) {
            return new ReturnObject<>(ResponseCode.PRESALE_STATENOTALLOW);
        }
        // po.setState(state);
        // 这里采用部分更新,防止数据更改
        PreSalePo preSalePo = new PreSalePo();
        preSalePo.setId(po.getId());
        preSalePo.setState(state);
        ReturnObject<Object> retObj = new ReturnObject<>(ResponseCode.OK);
        int ret = 0;
        try {
            ret = preSalePoMapper.updateByPrimaryKeySelective(preSalePo);
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
        if (ret == 0) {
            logger.info("活动不存在或已被删除：id = " + id);
            retObj = new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        return retObj;
    }


    public PreSalePo getPreSalePo(Long preSaleId){
        PreSalePo preSalePo = null;
        try {
            preSalePo = preSalePoMapper.selectByPrimaryKey(preSaleId);
        } catch (DataAccessException e) {
            // 数据库错误
            logger.error("数据库错误：" + e.getMessage());
        } catch (Exception e) {
            // 属未知错误
            logger.error("严重错误：" + e.getMessage());
        }
        if(preSalePo.getState() == PreSale.State.OFF.getCode()) {
            return null;
        }else {
            return preSalePo;
        }
    }

}
