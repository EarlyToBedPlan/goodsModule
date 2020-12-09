package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.model.vo.FloatPriceRetVo;
import cn.edu.xmu.goods.model.vo.FloatPriceVo;
import cn.edu.xmu.goods.model.vo.TimePoint;
import cn.edu.xmu.ooad.util.ReturnObject;

import java.util.List;

public interface FloatPriceService {
    public ReturnObject<Object> logicallyDelete(Long userId, Long floatPriceId);
    public ReturnObject<FloatPriceRetVo> createFloatPrice(Long userId, FloatPriceVo floatPriceVo, Long skuId);
    public boolean isOverLap(List<TimePoint> list);
}
