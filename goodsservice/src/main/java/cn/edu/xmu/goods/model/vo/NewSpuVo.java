package cn.edu.xmu.goods.model.vo;

import lombok.Data;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/9 9:20
 * modifiedBy Yancheng Lai 9:20
 **/
@Data
public class NewSpuVo {

    private String name;

    private String description;

    private String spec;

    public GoodsSpuRetVo createSpuVo(){
        GoodsSpuRetVo goodsSpuRetVo = new GoodsSpuRetVo();
        goodsSpuRetVo.setName(name);
        goodsSpuRetVo.setDetail(description);
        //goodsSpuRetVo.setSpec(spec);
        return goodsSpuRetVo;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
