package cn.edu.xmu.goods.model.bo;

import cn.edu.xmu.goods.model.vo.ShopRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.shop.model.po.ShopPo;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/9 18:11
 * modifiedBy Yancheng Lai 18:11
 **/
@Data
public class Shop implements VoObject {
    private ShopPo shopPo;

    @Override
    public ShopRetVo createVo() {
        return new ShopRetVo(this);
    }

    @Override
    public Object createSimpleVo() {
        return null;
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

    public Byte getState() {
        return shopPo.getState();
    }

    public void setState(Byte state) {
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
