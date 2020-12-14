package cn.edu.xmu.comment.service;

import cn.edu.xmu.comment.model.bo.Comment;
import cn.edu.xmu.comment.model.po.CommentPo;
import cn.edu.xmu.comment.model.po.CommentPoExample;
import cn.edu.xmu.goods.model.vo.StateVo;
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
    ReturnObject newGoodsSkuComment(Long orderItemId, Comment comment);

    /**
     * @Description 由商品id获得评论列表
     */
    ReturnObject<PageInfo<VoObject>> getGoodsSkuCommentsList(Long goodsSkuId, Integer page, Integer pagesize);


    /**
     * @Description 管理员审核评论
     */
    ReturnObject auditComment(Comment comment);

    /**
     * @Description 查看自己的评论
     */
    ReturnObject<PageInfo<VoObject>> getSelfCommentList(Long userId, Integer page, Integer pagesize);


    /**
     * @Description 查看已审核/未审核评论列表
     */
    ReturnObject<PageInfo<VoObject>> getCommentListByShopId(Long shopId, Integer page, Integer pagesize);

    /**
     * @Description 获得评论所有状态
     * @return
     */
    ReturnObject<List<StateVo>> findCommentStates();
}
