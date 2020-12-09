package cn.edu.xmu.goods.model.vo;

import lombok.Data;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/9 11:19
 * modifiedBy Yancheng Lai 11:19
 **/
@Data
public class GoodsSkuPostVo {
    String sn;
    String name;
    Long originalPrice;
    String configuration;
    Long weight;
    String imageUrl;
    Integer inventory;
    String detail;

}
