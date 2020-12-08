package cn.edu.xmu.presale.model.bo;

import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.presale.model.po.PreSalePo;
import cn.edu.xmu.presale.model.vo.PreSaleVo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class PreSale implements VoObject {
    /**
     * 活动状态
     */

    public enum State {
        NEW(0, "已新建"),
        ADVANCE(1, "定金"),
        REST(2, "尾款"),
        END(3, "已下线"),
        DELETE(4, "活动结束");

        private static final Map<Integer, State> stateMap;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap();
            for (PreSale.State enum1 : values()) {
                stateMap.put(enum1.code, enum1);
            }
        }

        private int code;
        private String description;

        State(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static PreSale.State getTypeByCode(Integer code) {
            return stateMap.get(code);
        }

        public Byte getCode() {
            return (byte)code;
        }

        public String getDescription() {
            return description;
        }
    }

    public PreSale(PreSalePo po) {
        this.id = po.getId();
        this.name = po.getName();
        this.beginTime = po.getBeginTime();
        this.payTime = po.getPayTime();
        this.state = po.getState();
        this.endTime = po.getEndTime();
        this.quantity = po.getQuantity();
        this.advancePayPrice = po.getAdvancePayPrice();
        this.restPayPrice = po.getRestPayPrice();
        this.gmtCreated = po.getGmtCreated();
        this.gmtModified = po.getGmtModified();
/*        this.goodsSpuId = po.getGoodsSpuId();
        this.shopId = po.getShopId();*/
    }

    private Long id;


    private String name;

    private LocalDateTime beginTime;

    private LocalDateTime payTime;

    private LocalDateTime endTime;


    private Byte state;

/*    private Long shopId;

    private Long goodsSpuId;*/


    private Integer quantity;

    private Long advancePayPrice;

    private Long restPayPrice;


    private LocalDateTime gmtCreated;


    private LocalDateTime gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getAdvancePayPrice() {
        return advancePayPrice;
    }

    public void setAdvancePayPrice(Long advancePayPrice) {
        this.advancePayPrice = advancePayPrice;
    }

    public Long getRestPayPrice() {
        return restPayPrice;
    }

    public void setRestPayPrice(Long restPayPrice) {
        this.restPayPrice = restPayPrice;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public Object createVo() {
        return new PreSaleVo(this);
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }
}
