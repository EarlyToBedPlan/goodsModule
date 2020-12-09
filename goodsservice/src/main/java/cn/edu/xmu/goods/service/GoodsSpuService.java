package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.dao.GoodsSpuDao;
import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.bo.GoodsSpu;
import cn.edu.xmu.goods.model.vo.GoodsSpuRetVo;
import cn.edu.xmu.goods.model.vo.StateVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * SKU Service 接口
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/1 22:23
 * modifiedBy Yancheng Lai 22:23
 **/

public interface GoodsSpuService {

    public ReturnObject<GoodsSpuRetVo> findSpuById(Long id);

    public ReturnObject<VoObject> insertGoodsSpu(GoodsSpu goodsSpu, Long id);

    public ReturnObject<VoObject> updateSpu(GoodsSpu vo,Long shopId,Long id);

    public ReturnObject<VoObject> revokeSpu(Long id);

    public ReturnObject<VoObject> setCategoryDefault(Long id,Long defaultValue);

    public ReturnObject uploadSpuImg(Long id, MultipartFile multipartFile);
}

