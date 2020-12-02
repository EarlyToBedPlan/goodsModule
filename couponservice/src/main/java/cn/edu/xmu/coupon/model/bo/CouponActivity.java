package cn.edu.xmu.coupon.model.bo;

import cn.edu.xmu.coupon.mapper.CouponActivityPoMapper;
import cn.edu.xmu.coupon.model.po.CouponActivityPo;
import cn.edu.xmu.coupon.model.po.CouponActivityPoExample;
import cn.edu.xmu.coupon.model.vo.CouponActivityRetVo;
import cn.edu.xmu.coupon.model.vo.CouponActivitySimpleRetVo;
import cn.edu.xmu.coupon.model.vo.CouponActivityVo;
import cn.edu.xmu.coupon.model.vo.UserRetVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.encript.AES;
import cn.edu.xmu.ooad.util.encript.SHA256;
import lombok.Data;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.encript.AES;
import cn.edu.xmu.ooad.util.encript.SHA256;
import org.aspectj.apache.bcel.classfile.Code;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/29 10:39
 */
@Data
public class CouponActivity implements VoObject {


    public enum State {
        WAITING(0, "还未开始的"),
        TOMORROW_ONLINE(1, "明天开始的"),
        ONLINE(2, "正在进行中的"),
        OFFLINE(3, "已经结束的");

        private static final Map<Integer, CouponActivity.State> stateMap;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap();
            for (CouponActivity.State enum1 : values()) {
                stateMap.put(enum1.code, enum1);
            }
        }

        private int code;
        private String description;

        State(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static CouponActivity.State getTypeByCode(Integer code) {
            return stateMap.get(code);
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    Long id;
    Byte state = (byte) State.WAITING.code;
    String name;
    int quantity;
    Byte quantityType;
    Byte validTerm;
    LocalDateTime beginTime;
    LocalDateTime couponTime;
    LocalDateTime endTime;
    String strategy;
    LocalDateTime gmtCreate;
    LocalDateTime gmtModified;
    String img;
    UserRetVo creator;
    UserRetVo modifiedBy;

    public CouponActivity(CouponActivityPo po) {
        this.id = po.getId();
    }

    public CouponActivity() {
    }

    ;


    @Override
    public Object createVo() {
        return new CouponActivityRetVo(this);
    }

    @Override
    public Object createSimpleVo() {
        return new CouponActivitySimpleRetVo(this);
    }

    public CouponActivityPo createPo() {
        CouponActivityPo po = new CouponActivityPo();
        po.setBeginTime(this.beginTime);
        po.setCouponTime(this.couponTime);
        po.setName(this.name);
        po.setQuantitiyType(this.quantityType);
        po.setQuantity(this.quantity);
        po.setEndTime(this.endTime);
        po.setStrategy(this.strategy);
        po.setValidTerm(this.validTerm);
        po.setState((byte) State.WAITING.code);
        return po;
    }
}
