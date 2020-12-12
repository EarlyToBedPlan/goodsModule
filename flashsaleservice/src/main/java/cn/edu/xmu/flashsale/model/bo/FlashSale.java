package cn.edu.xmu.flashsale.model.bo;

import cn.edu.xmu.flashsale.model.po.FlashSalePo;
import cn.edu.xmu.flashsale.model.vo.FlashSaleVo;
import cn.edu.xmu.ooad.model.VoObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LJP_3424
 * @create 2020-12-03 21:27
 */
public class FlashSale implements VoObject {

    public void setState(Byte state) {
        this.state = state;
    }

    public enum State {
        OFF(0, "未开始"),
        ON(1, "进行中"),
        DELETE(2, "已结束");

        private static final Map<Integer, State> stateMap;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap();
            for (FlashSale.State enum1 : values()) {
                stateMap.put(enum1.code, enum1);
            }
        }

        private int code;
        private String description;

        State(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static FlashSale.State getTypeByCode(Integer code) {
            return stateMap.get(code);
        }

        public byte getCode() {
            return (byte) code;
        }

        public String getDescription() {
            return description;
        }
    }
    
    private Long id;

    private LocalDateTime flashDate;

    //private Long timeSegId;
    //private TimeSegmentVo timeSegmentVo;

    private String imageUrl;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public Byte getState() {
        return state;
    }

    private Byte state;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFlashDate() {
        return flashDate;
    }

    public void setFlashDate(LocalDateTime flashDate) {
        this.flashDate = flashDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public FlashSale(FlashSalePo po) {
        id = po.getId();
        flashDate = po.getFlashDate();
        gmtCreated = po.getGmtCreate();
        gmtModified = po.getGmtModified();
        state = po.getState();
    }

    @Override
    public VoObject createVo() {
        FlashSaleVo flashSaleVo = new FlashSaleVo();
        flashSaleVo.setFlashDate(this.flashDate);
        flashSaleVo.setGmtCreated(this.gmtCreated);
        flashSaleVo.setGmtModified(this.gmtModified);
        flashSaleVo.setId(this.id);
        flashSaleVo.setState(this.state);
        return flashSaleVo;
    }

    @Override
    public VoObject createSimpleVo() {
        return null;
    }
}
