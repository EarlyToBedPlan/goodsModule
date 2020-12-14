package cn.edu.xmu.comment.model.bo;

import cn.edu.xmu.comment.model.po.CommentPo;
import cn.edu.xmu.ooad.model.VoObject;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ruzhen Chang
 */
@Data
public class Comment implements VoObject{

    public Comment() {

    }

    /**
     * @author Ruzhen Chang
     */

    public enum State {
        NEW(0, "未审核"),
        NORM(1, "评论成功"),
        FORBID(2, "未通过");

        private static final Map<Integer, Comment.State> stateMap;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap();
            for (Comment.State enum1 : values()) {
                stateMap.put(enum1.code, enum1);
            }
        }

        private int code;
        private String description;

        State(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static Comment.State getTypeByCode(Integer code) {
            return stateMap.get(code);
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    private long id;

    private long customerId;

    private long goodsSkuId;

    private long orderItemId;

    private int type;
    /*评价等级 0好评1中评2差评*/

    private String content;

    private State state=State.NEW;

    public Comment(CommentPo po){
        this.id=po.getId();
        this.customerId= po.getCustomerId();
        this.goodsSkuId= po.getGoodsSkuId();
        this.orderItemId= po.getOrderitemId();
        this.type= po.getType();
        this.content= po.getContent();
        if(null!=po.getState()){
            this.state=State.getTypeByCode(po.getState().intValue());
        }
    }

    @Override
    public Object createVo() {
        return null;
    }

    @Override
    public VoObject createSimpleVo() {
        return null;
    }
}
