package cn.edu.xmu.goods.model.bo;

import cn.edu.xmu.goods.model.po.ShopPo;
import cn.edu.xmu.goods.model.vo.ShopRetVo;
import cn.edu.xmu.goods.model.vo.ShopSimpleRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商店
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/2 18:05
 * modifiedBy Yancheng Lai 18:05
 **/
@Data
public class Shop implements VoObject {

//    private Long id;
//
//    private String name;
//
//    private Integer state;
//
//    private LocalDateTime gmtCreate;
//
//    private LocalDateTime gmtModified;

    ShopPo shopPo;
    @Override
    public ShopRetVo createVo() {
        return new ShopRetVo(this);
    }

    @Override
    public ShopSimpleRetVo createSimpleVo() {
        return new ShopSimpleRetVo(this);
    }

    public Long getId() {
        return shopPo.getId();
    }

    public void setId(Long id) {
        shopPo.setId(id);
    }

    public String getName() {
        return shopPo.getName();
    }

    public void setName(String name) {
        shopPo.setName(name);
    }

    public Integer getState() {
        return shopPo.getState();
    }

    public void setState(Integer state) {
        shopPo.setState(state);
    }

    public LocalDateTime getGmtCreate() {
        return shopPo.getGmtCreate();
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        shopPo.setGmtCreate(gmtCreate);
    }

    public LocalDateTime getGmtModified() {
        return shopPo.getGmtModified();
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        shopPo.setGmtModified(gmtModified);
    }
}
