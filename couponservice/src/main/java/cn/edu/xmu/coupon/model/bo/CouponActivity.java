package cn.edu.xmu.coupon.model.bo;

import cn.edu.xmu.coupon.model.po.CouponActivityPo;
import cn.edu.xmu.coupon.model.vo.CouponActivityRetSimpleVo;
import cn.edu.xmu.coupon.model.vo.CouponActivityRetVo;
import cn.edu.xmu.coupon.model.vo.UserRetVo;
import cn.edu.xmu.coupon.service.CouponActivityService;
import cn.edu.xmu.coupon.service.CouponActivityServiceImpl;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/29 10:39
 */
@Data
public class CouponActivity implements VoObject {
    Long id;
    Byte state = (byte) State.NEW.code;
    String name;
    int quantity;
    Byte quantityType;
    Byte validTerm;
    LocalDateTime beginTime;
    LocalDateTime couponTime;
    LocalDateTime endTime;
    String strategy;
    LocalDateTime gmtCreated;
    LocalDateTime gmtModified;
    String img;
    UserRetVo createdBy = new UserRetVo();
    UserRetVo modifiedBy = new UserRetVo();
    Long shopId;
    private Logger logger = LoggerFactory.getLogger(CouponActivity.class);
    public CouponActivity(CouponActivityPo po) {
        this.id = po.getId();
        this.state = po.getState();
        this.name = po.getName();
//        this.quantity=po.getQuantity();
//        this.quantityType=po.getQuantitiyType();
//        this.validTerm=po.getValidTerm();
//        this.beginTime=po.getBeginTime();
//        this.endTime=po.getEndTime();
//        this.couponTime=po.getCouponTime();
//        this.strategy=po.getStrategy();
        this.gmtCreated = po.getGmtCreate();
//        this.modifiedBy.setId(po.getModiBy());
//        this.createdBy.setId(po.getCreatedBy());
//        this.modifiedBy.setName("哈哈");
//        this.createdBy.setName("哈哈哈");
//        this.img=po.getImageUrl();
    }

    public CouponActivity() {
    }

    @Override
    public VoObject createVo() {
        return new CouponActivityRetVo(this);
    }

    @Override
    public VoObject createSimpleVo() {
        return new CouponActivityRetSimpleVo(this);
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
        po.setState((byte) State.NEW.code);
        po.setGmtCreate(LocalDateTime.now());
        return po;
    }
    public enum Timeline {
        WAITING(0, "待上线"),
        TOMORROW_ONLINE(1, "明天开始"),
        ONLINE(2,"正在进行"),
        OFFLINE(3,"结束下线");
        private static final Map<Integer, CouponActivity.Timeline> stateMap;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap();
            for (CouponActivity.Timeline enum1 : values()) {
                stateMap.put(enum1.code, enum1);
            }
        }
        private int code;
        private String description;
        Timeline(int code, String description) {
            this.code = code;
            this.description = description;
        }
        public static CouponActivity.Timeline getTypeByTime(LocalDateTime beginTime,LocalDateTime endTime)
        {
            if(beginTime.isAfter(LocalDateTime.now()))
                return Timeline.WAITING;
            else if(LocalDateTime.now().minusDays(1).toLocalDate()==beginTime.toLocalDate())
                return Timeline.TOMORROW_ONLINE;
            else if(beginTime.isBefore(LocalDateTime.now())&&endTime.isAfter(LocalDateTime.now()))
                return Timeline.ONLINE;
            else
                return Timeline.OFFLINE;
        }

        public static CouponActivity.Timeline getTypeByCode(Integer code) {
            return stateMap.get(code);
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
    public enum State {
        NEW(0, "新建"),
        CANCELLED(1, "被取消");
        private static final Map<Integer, CouponActivity.State> stateMap;

        public static int STATE_SIZE=2;
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
}
