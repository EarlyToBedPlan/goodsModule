package cn.edu.xmu.groupon.model.vo;

import cn.edu.xmu.groupon.model.bo.Groupon;
import lombok.Data;

/**
 * @author LJP_3424
 * @create 2020-12-01 3:49
 */
@Data
public class GrouponStateVo {
    private Long Code;

    private String name;

    public GrouponStateVo(Groupon.State state) {
        Code = Long.valueOf(state.getCode());
        name = state.getDescription();
    }
}

