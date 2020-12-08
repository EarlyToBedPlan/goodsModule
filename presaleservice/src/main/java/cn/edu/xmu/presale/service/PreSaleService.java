package cn.edu.xmu.presale.service;

import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.presale.dao.PreSaleDao;
import cn.edu.xmu.presale.model.bo.PreSale;
import cn.edu.xmu.presale.model.po.PreSalePo;
import cn.edu.xmu.presale.model.vo.NewPreSaleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LJP_3424
 * @create 2020-12-01 14:25
 */
@Service
public class PreSaleService {
    private Logger logger = LoggerFactory.getLogger(PreSaleService.class);

    @Autowired
    PreSaleDao preSaleDao;

    @Transactional
    public boolean getPreSaleInActivities(Long goodsSpuId, LocalDateTime beginTime, LocalDateTime endTime) {
        return preSaleDao.getPreSaleInActivities(goodsSpuId, beginTime, endTime);
    }

    @Transactional
    public ReturnObject<List> getPreSaleById(Long id, Byte state) {
        return preSaleDao.getPreSaleBySpuId(id, state);
    }

    /**
     * 分页查询所有预售活动
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return ReturnObject<PageInfo < VoObject>> 分页返回预售信息
     * @author LJP_3424
     */
    @Transactional
    public ReturnObject<PageInfo<VoObject>> selectAllPreSale(Long shopId, Byte timeline, Long spuId, Integer pageNum, Integer pageSize) {
        List<PreSalePo> preSalePos = preSaleDao.selectAllPreSale(shopId, timeline, spuId, pageNum, pageSize);
        List<VoObject> ret = new ArrayList<>(preSalePos.size());
        for(PreSalePo preSalePo:preSalePos){
            // 等待接口
            // PreSale preSale= new PreSale(preSalePo,goodsSpuDao.selectById(spuId),shopDao.selectById(shopId));
            PreSale preSale = new PreSale(preSalePo);
            ret.add(preSale);
        }
        PageHelper.startPage(pageNum, pageSize);

        PageInfo<VoObject> of = PageInfo.of(ret);
        return new ReturnObject<PageInfo<VoObject>>(of);
        //return preSaleDao.selectAllPreSale(shopId, timeline, spuId, pageNum, pageSize);
    }


    @Transactional
    public ReturnObject createNewPreSale(NewPreSaleVo vo, Long shopId, Long id) {
        // 检查商品Id是否为真
        //if(goodsSpuService.checkGoods(id) == false)return new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
        // 检查商品在beginTime 和 endTime是否参与了活动
        //if(checkActivities == true)return new ReturnObject(ResponseCode.SPU_PARTICIPATE);
        ReturnObject returnObject = preSaleDao.createNewPreSaleByVo(vo, shopId, id);
        return returnObject;
    }

    /**
     * @Description:  更新活动信息
     *
     * @param newPreSaleVo
     * @param id
     * @return: cn.edu.xmu.ooad.util.ReturnObject
     * @Author: LJP_3424
     * @Date: 2020/12/7 20:09
     */
    @Transactional
    public ReturnObject updatePreSale(NewPreSaleVo newPreSaleVo, Long id) {
        ReturnObject retObj = null;
        try {
            if(checkPreSale(id) == false) return new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
            PreSalePo po = preSaleDao.getPreSalePo(id);
            if (po.getBeginTime().compareTo(LocalDateTime.now()) < 0 && po.getEndTime().compareTo(LocalDateTime.now()) > 0) {
                return new ReturnObject<>(ResponseCode.PRESALE_STATENOTALLOW);
            }
            preSaleDao.updatePreSale(newPreSaleVo, id);
            PreSalePo preSalePo = preSaleDao.getPreSalePo(id);
            PreSale preSale = new PreSale(preSalePo);
            retObj = new ReturnObject<>(preSale);
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

    public boolean checkPreSale(Long preSaleId){
        if(preSaleDao.getPreSalePo(preSaleId) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public ReturnObject<Object> deletePreSale(Long id) {
        return preSaleDao.changePreSaleState(id,PreSale.State.END.getCode());
    }



}
