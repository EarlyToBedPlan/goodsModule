package cn.edu.xmu.presale.dao;

import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.presale.mapper.NewUserPoMapper;
import cn.edu.xmu.presale.mapper.PreSalePoMapper;
import cn.edu.xmu.presale.model.bo.NewPreSale;
import cn.edu.xmu.presale.model.bo.PreSale;
import cn.edu.xmu.presale.model.po.PreSalePo;
import cn.edu.xmu.presale.model.po.PreSalePoExample;
import cn.edu.xmu.presale.model.vo.NewPreSaleVo;
import cn.edu.xmu.presale.model.vo.PreSaleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author LJP_3424
 * @create 2020-12-01 9:59
 */
@Repository
public class PreSaleDao implements InitializingBean {

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
        criteria1.andGoodsSpuIdEqualTo(goodsSpuId);
        // 这里使用select,实际上自己写count可以得到更高的效率
        List<PreSalePo> preSalePos = preSalePoMapper.selectByExample(example);
        //返回为空则不存在,返回false ,不为空说明查询到了
        if (preSalePos.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public ReturnObject<List> getPreSaleById(Long shopId, Long id, Byte state) {
        PreSalePoExample example = new PreSalePoExample();
        PreSalePoExample.Criteria criteria = example.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        criteria.andGoodsSpuIdEqualTo(id);
        if (state != -1) criteria.andStateEqualTo(state);
        logger.debug("findPreSaleById: Id = " + id + ";Shop Id = " + shopId + ";state = " + state);
        List<PreSalePo> preSalePos = preSalePoMapper.selectByExample(example);

        if (preSalePos.size() != 0) {
            return new ReturnObject<>(preSalePos);
        } else {
            return null;
        }
    }

    /**
     * 分页查询所有预售信息
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return ReturnObject<List> 活动列表
     * @author LJP_3424
     */
    public ReturnObject<PageInfo<VoObject>> selectAllPreSale(Long shopId, Byte timeline, Long spuId, Integer pageNum, Integer pageSize) {
        PreSalePoExample example = new PreSalePoExample();
        PreSalePoExample.Criteria criteria = example.createCriteria();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime tomorrow;
        LocalDate localDate = LocalDate.now();
        switch (timeline) {
            case 0:
                criteria.andBeginTimeGreaterThan(LocalDateTime.now());
                break;
            case 1:
                // 明天
                tomorrow = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN).minusDays(-1);
                criteria.andBeginTimeBetween(tomorrow, tomorrow.minusDays(-1));
                break;
            case 2:
                criteria.andBeginTimeLessThanOrEqualTo(localDateTime);
                criteria.andEndTimeGreaterThan(localDateTime);
                break;
            case 3:
                criteria.andEndTimeLessThan(localDateTime);
                break;
        }
        if (shopId != 0) criteria.andShopIdEqualTo(shopId);
        if (spuId != 0) criteria.andGoodsSpuIdEqualTo(spuId);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        logger.debug("page = " + pageNum + "pageSize = " + pageSize);
        List<PreSalePo> preSalePos = null;
        try {
            //不加限定条件查询所有
            preSalePos = preSalePoMapper.selectByExample(example);
            List<VoObject> ret = new ArrayList<>(preSalePos.size());
            for (PreSalePo po : preSalePos) {
                PreSale preSale = new PreSale(po);
                ret.add(preSale);
            }
            PageInfo<VoObject> preSalePage = PageInfo.of(ret);
            return new ReturnObject<PageInfo<VoObject>>(preSalePage);
        } catch (DataAccessException e) {
            logger.error("selectAllPreSale: DataAccessException:" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        } catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
    }

    /**
     * 由vo创建newUser检查重复后插入
     *
     * @param vo vo对象
     * @return ReturnObject
     * createdBy: LJP_3424
     */
    public ReturnObject<List> createNewPreSaleByVo(NewPreSaleVo vo, Long shopId, Long id) {
        //logger.debug(String.valueOf(bloomFilter.includeByBloomFilter("mobileBloomFilter","FAED5EEF1C8562B02110BCA3F9165CBE")));
        //by default,email/mobile are both needed
        PreSalePo preSalePo = new PreSalePo();
        ReturnObject returnObject;
        preSalePo.setName(vo.getName());
        preSalePo.setAdvancePayPrice(vo.getAdvancePayPrice());
        preSalePo.setRestPayPrice(vo.getRestPayPrice());
        preSalePo.setQuantity(vo.getQuantity());
        preSalePo.setBeginTime(vo.getBeginTime());
        preSalePo.setEndTime(vo.getEndTime());
        preSalePo.setGoodsSpuId(id);
        preSalePo.setShopId(shopId);
        preSalePo.setGmtCreated(LocalDateTime.now());
        try {
            preSalePoMapper.insert(preSalePo);
            return getPreSaleById(shopId, id, (byte) -1);
        } catch (Exception e) {
            return new ReturnObject(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }


    public ReturnObject<Object> changePreSaleState(Long shopId, Long id) {
        PreSalePo po = preSalePoMapper.selectByPrimaryKey(id);
        if (po == null) {
            logger.info("活动不存在或已被删除：id = " + id);
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        if (po.getBeginTime().compareTo(LocalDateTime.now()) < 0 && po.getEndTime().compareTo(LocalDateTime.now()) > 0) {
            return new ReturnObject<>(ResponseCode.PRESALE_STATENOTALLOW);
        }
        po.setState((byte) 4);
        ReturnObject<Object> retObj = new ReturnObject<>(ResponseCode.OK);
        int ret;
        try {
            ret = preSalePoMapper.updateByPrimaryKey(po);
            if (ret == 0) {
                logger.info("活动不存在或已被删除：id = " + id);
                retObj = new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            }
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
        return retObj;
    }

    /**
     * 修改一个预售活动信息
     *
     * @author LJP_3424
     */
    public ReturnObject<VoObject> updatePreSale(NewPreSaleVo preSaleVo, Long shopId, Long id) {
        PreSalePo po = preSalePoMapper.selectByPrimaryKey(id);
        if (po.getBeginTime().compareTo(LocalDateTime.now()) < 0 && po.getEndTime().compareTo(LocalDateTime.now()) > 0) {
            return new ReturnObject<>(ResponseCode.PRESALE_STATENOTALLOW);
        }
        po.setName(preSaleVo.getName());
        po.setAdvancePayPrice(preSaleVo.getAdvancePayPrice());
        po.setRestPayPrice(preSaleVo.getRestPayPrice());
        po.setQuantity(preSaleVo.getQuantity());
        po.setBeginTime(preSaleVo.getBeginTime());
        po.setEndTime(preSaleVo.getEndTime());
        po.setGmtModified(LocalDateTime.now());
        ReturnObject<VoObject> retObj = null;
        try {
            int ret = preSalePoMapper.updateByPrimaryKey(po);
            if (ret != 0) {
                PreSale preSale = new PreSale(preSalePoMapper.selectByPrimaryKey(id));
                retObj = new ReturnObject<>(preSale);
            }
        } catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
        return retObj;
    }


}
