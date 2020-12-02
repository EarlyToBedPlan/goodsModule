package cn.edu.xmu.goods.model.bo;

import cn.edu.xmu.goods.model.po.GoodsCategoryPo;
import cn.edu.xmu.goods.model.vo.GoodsCategoryRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：谢沛辰
 * @Date:2020.11.29
 * @Description:商品类型
 */
@Data
public class GoodsCategory implements VoObject {
    private Long id;
    private Long pId;
    private String name;
    private LocalDateTime gmtGreated;
    private LocalDateTime gmtModified;

    public GoodsCategory(GoodsCategoryPo goodsCategoryPo){
        this.id=goodsCategoryPo.getId();
        this.pId=goodsCategoryPo.getPid();
        this.name=goodsCategoryPo.getName();
        this.gmtGreated=goodsCategoryPo.getGmtCreated();
        this.gmtModified=goodsCategoryPo.getGmtModified();
    }

    public GoodsCategory() {

    }

    public GoodsCategoryRetVo createVo(){
        GoodsCategoryRetVo goodsCategoryRetVo=new GoodsCategoryRetVo();
        goodsCategoryRetVo.setId(this.id);
        goodsCategoryRetVo.setPId(this.pId);
        goodsCategoryRetVo.setName(this.name);
        goodsCategoryRetVo.setGmtCreated(this.gmtGreated.toString());
        goodsCategoryRetVo.setGmtModified(this.gmtModified.toString());
        return goodsCategoryRetVo;
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }

    public GoodsCategoryPo createPo(){
        GoodsCategoryPo goodsCategoryPo=new GoodsCategoryPo();
        goodsCategoryPo.setId(this.id);
        goodsCategoryPo.setName(this.name);
        goodsCategoryPo.setPid(this.pId);
        goodsCategoryPo.setGmtCreated(this.gmtGreated);
        goodsCategoryPo.setGmtModified(this.gmtModified);
        return goodsCategoryPo;
    }
}
