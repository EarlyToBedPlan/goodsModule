package cn.edu.xmu.goods.model.vo;

import lombok.Data;
/**
 * @Author：谢沛辰
 * @Date: 2020.11.29
 * @Description:
 */
@Data
public class GoodsCategoryRetVo {
    private Long id;
    private Long pId;
    private String name;
    private String gmtCreate;
    private String gmtModified;
}
