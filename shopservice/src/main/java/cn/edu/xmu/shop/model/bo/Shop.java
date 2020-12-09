package cn.edu.xmu.shop.model.bo;

import cn.edu.xmu.shop.model.po.ShopPo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.shop.model.vo.ShopSimpleVo;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ruzhen Chang
 */
@Data
public class Shop implements VoObject{

    public void setState(State state) {
    }

    public enum State {
        UNAUDITED(0, "未审核"),
        OFFLINE(1, "未上线"),
        ONLINE(2, "上线"),
        DELETE(3, "关闭"),
        FAILED(4,"审核未通过");



        private static final Map<Integer, Shop.State> stateMap;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap<>();
            for (Shop.State enum1 : values()) {
                stateMap.put(enum1.code, enum1);
            }
        }

        private int code;
        private String description;

        State(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static Shop.State getTypeByCode(Integer code) {
            return stateMap.get(code);
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    long id;

    String shopName;

    Byte state=(byte) State.UNAUDITED.code;

    public Shop(ShopPo po){
        this.id=po.getId();
        this.shopName = po.getName();
    }

    @Override
    public Object createVo() {
        return null;
    }

    @Override
    public ShopSimpleVo createSimpleVo() {
        return new ShopSimpleVo(this);
    }

    public ShopPo createPo(){
        ShopPo po=new ShopPo();
        po.setId(this.id);
        po.setName(this.shopName);
        po.setState((byte)0);
        return po;
    }


}
