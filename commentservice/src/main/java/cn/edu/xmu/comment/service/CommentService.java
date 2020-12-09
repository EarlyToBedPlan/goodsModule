package cn.edu.xmu.comment.service;

import cn.edu.xmu.comment.model.bo.Comment;
import cn.edu.xmu.comment.model.po.CommentPo;
import cn.edu.xmu.comment.model.po.CommentPoExample;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ruzhen Chang
 */

public interface CommentService {
    /**
     * @Description 新增sku评论
     */
    public ReturnObject newGoodsSkuComment(long goodsSkuId, long customerId, long orderItemId, byte type, String content);

    /**
     * @Description 由商品id获得评论列表
     */
    ReturnObject<PageInfo<VoObject>> getGoodsSkuCommentsList(long goodsSkuId, Integer page, Integer pagesize);


    /**
     * @Description 管理员审核评论
     */
    ReturnObject auditComment(Comment comment);

    /**
     * @Description 查看自己的评论
     */
    ReturnObject<PageInfo<VoObject>> getSelfCommentList(long userId, Integer page, Integer pagesize);


    /**
     * @Description 查看已审核/未审核评论列表
     */
    ReturnObject<PageInfo<VoObject>> getAllCommentListByShopId(long shopId, Integer page, Integer pagesize);
}
