package cn.edu.xmu.goods.model.bo;

import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;

/**
 * 商品状态Bo
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 19:46
 * modifiedBy Yancheng Lai 19:46
 **/
@Data
public class GoodsState implements VoObject {

    private Integer code;

    private String name;

    @Override
    public Object createVo() {
        return null;
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }
}
