package cn.edu.xmu.goods.model.vo;

import cn.edu.xmu.goods.model.bo.GoodsSku;
import lombok.Data;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/5 12:40
 * modifiedBy Yancheng Lai 12:40
 **/
@Data
public class StateVo {



    private Byte code;

    private String name;

    public StateVo(){}
    public StateVo(Byte code,String name){
        this.code = code;
        this.name = name;
    }
    public StateVo(Byte code){
        this.code = code;
        this.name = GoodsSku.State.getTypeByCode(code).getDescription();
    }
}
