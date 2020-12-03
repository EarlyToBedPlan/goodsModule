package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.dao.FloatPriceDao;
import cn.edu.xmu.goods.model.bo.FloatPrice;
import cn.edu.xmu.goods.model.po.FloatPricePo;
import cn.edu.xmu.goods.model.vo.FloatPriceRetVo;
import cn.edu.xmu.goods.model.vo.FloatPriceVo;
import cn.edu.xmu.goods.model.vo.TimePoint;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author：谢沛辰
 * @Date: 2020.12.01
 * @Description:价格浮动业务逻辑
 */
@Service
public class FloatPriceService {

    @Autowired
    FloatPriceDao floatPriceDao;

    public ReturnObject<Object> logicallyDelete(Long userId, Long floatPriceId){
        ReturnObject<FloatPrice> target=floatPriceDao.getFloatPriceById(floatPriceId);
        if(target.getData()!=null){
            FloatPrice selected = target.getData();
            selected.setInvaildBy(userId);
            selected.setValid((byte) 0);
            return floatPriceDao.logicallyDeleteFloatPrice(selected);
        }else{
            return new ReturnObject<Object>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
    }

    public ReturnObject<FloatPriceRetVo> createFloatPrice(Long userId,FloatPriceVo floatPriceVo,Long skuId){
        ReturnObject<FloatPriceRetVo> retObj=null;
        if(LocalDateTime.parse(floatPriceVo.getBeginTime()).isAfter(LocalDateTime.parse(floatPriceVo.getEndTime()))){
            return new ReturnObject<>(ResponseCode.SKUPRICE_CONFLICT);
        }
        ReturnObject<List> floatPriceInDataBase=floatPriceDao.getFloatPriceBySkuId(skuId);
        FloatPrice floatPrice = new FloatPrice(floatPriceVo);
        boolean signal;
        if(floatPriceInDataBase.getCode()==ResponseCode.RESOURCE_ID_NOTEXIST){
            if(floatPriceDao.insertFloatPrice(floatPrice).getCode()==ResponseCode.OK){
                retObj=new ReturnObject<>(new FloatPriceRetVo(floatPrice,userId,userId));
            }
        }
        else if(floatPriceInDataBase.getCode()==ResponseCode.OK){
            List<FloatPricePo> media=floatPriceInDataBase.getData();
            List<TimePoint> timeList=new ArrayList<>();
            for (FloatPricePo floatPricePo : media) {
                timeList.add(new TimePoint(floatPricePo.getBeginTime(), true));
                timeList.add(new TimePoint(floatPricePo.getEndTime(), false));
            }
            timeList.add(new TimePoint(LocalDateTime.parse(floatPriceVo.getBeginTime()),true));
            timeList.add(new TimePoint(LocalDateTime.parse(floatPriceVo.getEndTime()),false));
            if(isOverLap(timeList)){
                retObj=new ReturnObject<>(ResponseCode.SKUPRICE_CONFLICT);
            }else{
                ReturnObject<FloatPrice> returnObject=floatPriceDao.insertFloatPrice(floatPrice);
                if(returnObject.getCode()==ResponseCode.OK)
                    retObj=new ReturnObject<>(new FloatPriceRetVo(floatPrice,userId,userId));
                else
                    retObj=new ReturnObject<>(returnObject.getCode());
            }
        }
        return retObj;
    }

    public boolean isOverLap(List<TimePoint> list){
        int max=0;
        int count=0;
        list.sort(new Comparator<TimePoint>() {
            @Override
            public int compare(TimePoint timePoint, TimePoint t1) {
                if (timePoint.getPoint().equals(t1.getPoint()))
                    return 0;
                else {
                    if (timePoint.getPoint().isAfter(t1.getPoint()))
                        return 1;
                    else
                        return -1;
                }
            }
        });
        for (TimePoint timePoint : list) {
            if (timePoint.isIsbegin())
                count++;
            else
                count--;
            if (count > max)
                max = count;
        }
        return max != 1;
    }
}
