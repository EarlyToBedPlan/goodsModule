package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * SkuService 接口
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 16:53
 * modifiedBy Yancheng Lai 16:53
 **/

public interface GoodsSkuService {

    public ReturnObject<VoObject> revokeSku(Long shopId,Long id);

    public ReturnObject<PageInfo<GoodsSkuRetVo>> findAllSkus(Long shopId, String skuSn, Long spuId, String spuSn, Integer page, Integer pagesize);

    public ReturnObject<VoObject> insertGoodsSku(GoodsSku goodsSku, Long shopId, Long id);

    public ReturnObject<VoObject> updateSku(GoodsSku vo,Long shopId,Long id);
}
