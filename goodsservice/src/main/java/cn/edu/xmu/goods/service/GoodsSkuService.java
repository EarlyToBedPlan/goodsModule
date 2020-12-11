package cn.edu.xmu.goods.service;

import cn.edu.xmu.goods.model.bo.GoodsSku;
import cn.edu.xmu.goods.model.vo.*;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * SkuService 接口
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 16:53
 * modifiedBy Yancheng Lai 16:53
 **/

public interface GoodsSkuService {

    public Boolean changeGoodsFreightWeight(Long FreightId, Long defaultFreightId);

    public InfoVo getWeightBySkuId(Long skuId);

    public Boolean deductStock(List<OrderItemVo> vo);

    public ReturnObject<VoObject> revokeSku(Long shopId,Long id);

    public ReturnObject<PageInfo<GoodsSkuRetVo>> findAllSkus(Long shopId, String skuSn, Long spuId, String spuSn, Integer page, Integer pagesize);

    public ReturnObject<GoodsSkuSimpleRetVo> insertGoodsSku(GoodsSku goodsSku, Long shopId, Long id);

    public ReturnObject<VoObject> updateSku(GoodsSku vo,Long shopId,Long id);

    public ReturnObject uploadSkuImg(Long id, Long shopId,MultipartFile multipartFile);

    public ReturnObject<List<StateVo>> findSkuStates();

    public ReturnObject<GoodsSkuRetVo> getSkuById(Long id);

    public List<VoObject> getSkuBySpuId(Long id);

    public List<GoodsSkuSimpleRetVo> getSkuSimpleVoBySpuId(Long id);

    public ReturnObject<VoObject> updateSkuOnShelves(Long shopId,Long id);

    public ReturnObject<VoObject> updateSkuOffShelves(Long shopId,Long id);

    public Integer getPriceById(Long goodsSkuId);

    public GoodsCartVo getCartByskuId(Long Sku);

    public boolean checkSkuIdInShop(Long shopId,Long goodsSpuId);

    public boolean checkSkuDisabled(Long skuId);
}
