package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.vo.GoodsSkuRetVo;
import cn.edu.xmu.goods.model.vo.GoodsSkuSimpleRetVo;
import cn.edu.xmu.goods.model.vo.StateVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * SkuService 接口
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 16:53
 * modifiedBy Yancheng Lai 16:53
 **/

public interface GoodsSkuService {

    public ReturnObject<VoObject> revokeSku(Long id);

    public ReturnObject<PageInfo<GoodsSkuRetVo>> findAllSkus(Long shopId, String skuSn, Long spuId, String spuSn, Integer page, Integer pagesize);

    public ReturnObject<GoodsSkuSimpleRetVo> insertGoodsSku(GoodsSku goodsSku, Long shopId, Long id);

    public ReturnObject<VoObject> updateSku(GoodsSku vo,Long shopId,Long id);

    public ReturnObject uploadSkuImg(Long id, MultipartFile multipartFile);

    public ReturnObject<List<StateVo>> findSkuStates();

    public ReturnObject<GoodsSkuRetVo> getSkuById(Long id);

    public List<VoObject> getSkuBySpuId(Long id);

    public List<GoodsSkuSimpleRetVo> getSkuSimpleVoBySpuId(Long id);

    public ReturnObject<VoObject> updateSkuOnShelves(Long shopId,Long id);

    public ReturnObject<VoObject> updateSkuOffShelves(Long shopId,Long id);
}
