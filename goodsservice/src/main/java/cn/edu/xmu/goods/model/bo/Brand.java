package cn.edu.xmu.goods.model.bo;

import cn.edu.xmu.goods.model.vo.BrandRetVo;
import cn.edu.xmu.ooad.model.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 品牌
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 16:56
 * modifiedBy Yancheng Lai 16:56
 **/
@Data
public class Brand implements VoObject{

    private Integer id;

    private String name;

    private String imageUrl;

    private String detail;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    @Override
    public BrandRetVo createVo() {
        return new BrandRetVo(this);
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }
}
