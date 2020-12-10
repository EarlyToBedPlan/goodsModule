package cn.edu.xmu.goods.model.bo;

import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/1 20:22
 * modifiedBy Yancheng Lai 20:22
 **/

@Data
public class SpecItems implements VoObject, Serializable {

    private Integer id;

    private String name;

    public SpecItems(Integer id,String name){
        this.name = name;
        this.id = id;
    }
    @Override
    public Object createVo() {
        return null;
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }
}
