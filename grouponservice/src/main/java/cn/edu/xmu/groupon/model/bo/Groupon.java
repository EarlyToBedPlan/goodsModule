package cn.edu.xmu.groupon.model.bo;

import cn.edu.xmu.groupon.model.po.GrouponPo;
import cn.edu.xmu.groupon.model.vo.GrouponVo;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LJP_3424
 * @create 2020-12-02 20:52
 */
public class Groupon implements VoObject {

    public enum State {
        RELEASE(0, "发布"),
        ONLINE(1, "上线"),
        OFFLINE(2, "下线"),
        END(3, "未成团"),
        DELETE(4, "已成团");

        private static final Map<Integer, State> stateMap;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap();
            for (Groupon.State enum1 : values()) {
                stateMap.put(enum1.code, enum1);
            }
        }

        private int code;
        private String description;

        State(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static Groupon.State getTypeByCode(Integer code) {
            return stateMap.get(code);
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    private Long id;

    private String name;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private Byte state;

    private Long shopId;

    private Long goodsSpuId;

    private String strategy;

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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getGoodsSpuId() {
        return goodsSpuId;
    }

    public void setGoodsSpuId(Long goodsSpuId) {
        this.goodsSpuId = goodsSpuId;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
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

    public Groupon(GrouponVo vo) {
        this.gmtModified = vo.getGmtModified();
        this.gmtCreated = vo.getGmtCreated();
        this.endTime = vo.getEndTime();
        this.beginTime = vo.getBeginTime();
        this.goodsSpuId = vo.getGoodsSpuId();
        this.name = vo.getName();
        this.id = vo.getId();
        this.shopId = vo.getShopId();
        this.state = vo.getState();
        this.strategy = vo.getStrategy();
    }

    public Groupon(GrouponPo po) {
        this.gmtModified = po.getGmtModified();
        this.gmtCreated = po.getGmtCreated();
        this.endTime = po.getEndTime();
        this.beginTime = po.getBeginTime();
        this.goodsSpuId = po.getGoodsSpuId();
        this.name = po.getName();
        this.id = po.getId();
        this.shopId = po.getShopId();
        this.state = po.getState();
        this.strategy = po.getStrategy();
    }

    @Override
    public Object createVo() {
        GrouponVo grouponVo = new GrouponVo();
        grouponVo.setBeginTime(this.beginTime);
        grouponVo.setEndTime(this.endTime);
        grouponVo.setGmtCreated(this.gmtCreated);
        grouponVo.setGmtModified(this.gmtModified);
        grouponVo.setGoodsSpuId(this.goodsSpuId);
        grouponVo.setId(this.id);
        grouponVo.setName(this.name);
        grouponVo.setShopId(this.shopId);
        grouponVo.setState(this.state);
        grouponVo.setStrategy(this.strategy);
        return grouponVo;
    }

    public Object createPo() {
        GrouponPo grouponPo = new GrouponPo();
        grouponPo.setBeginTime(this.beginTime);
        grouponPo.setEndTime(this.endTime);
        grouponPo.setGmtCreated(this.gmtCreated);
        grouponPo.setGmtModified(this.gmtModified);
        grouponPo.setGoodsSpuId(this.goodsSpuId);
        grouponPo.setId(this.id);
        grouponPo.setName(this.name);
        grouponPo.setShopId(this.shopId);
        grouponPo.setState(this.state);
        grouponPo.setStrategy(this.strategy);
        return grouponPo;
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }
}
