package cn.edu.xmu.goods.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/10 16:59
 * modifiedBy Yancheng Lai 16:59
 **/

@Data
public class GoodsSpuPostVo implements Serializable {
    String name;

    String description;

    String specs;

    public GoodsSpuPostVo(){}
}
