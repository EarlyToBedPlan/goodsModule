package cn.edu.xmu.comment.service;

/**
 * @author Ruzhen Chang
 */

import cn.edu.xmu.comment.dao.CommentDao;
import cn.edu.xmu.comment.model.bo.Comment;
import cn.edu.xmu.comment.model.po.CommentPo;
import cn.edu.xmu.goods.model.vo.StateVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentDao commentDao;

    private Logger logger= LoggerFactory.getLogger(CommentServiceImpl.class);


    /**
     * @Description 新增sku评论
     */
    public ReturnObject newGoodsSkuComment(long goodsSkuId, long customerId, long orderItemId, byte type, String content) {

        return null;
    }


    @Override
    public ReturnObject newGoodsSkuComment(Long goodsSkuId, Long customerId, Long orderItemId, byte type, String content) {
        return null;
    }

    /**
     * @Description 由商品id获得评论列表
     */
    public ReturnObject<PageInfo<VoObject>> getGoodsSkuCommentsList(Long goodsSkuId, Integer page, Integer pageSize){
        try {
            PageInfo<CommentPo> couponActivitiesPos = commentDao.getCommentListByGoodsSkuId(goodsSkuId,page,pageSize);
            List<VoObject> couponActivities = couponActivitiesPos.getList().stream().map(Comment::new).collect(Collectors.toList());
            PageInfo<VoObject> returnObject = new PageInfo<>(couponActivities);
            returnObject.setPages(couponActivitiesPos.getPages());
            returnObject.setPageNum(couponActivitiesPos.getPageNum());
            returnObject.setPageSize(couponActivitiesPos.getPageSize());
            returnObject.setTotal(couponActivitiesPos.getTotal());
            return new ReturnObject<>(returnObject);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误ha：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }

    @Override
    public ReturnObject auditComment(Comment comment) {
        return null;
    }


    /**
     * @Description 管理员审核评论
     * @author Ruzhen Chang
     */
    public ReturnObject auditComment(Comment comment,String conclusion) {
        ReturnObject returnObject=null;
        try{
            CommentPo commentPo=commentDao.getCommentById(comment.getId());
            if(commentPo==null){
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST,String.format("评论不存在 id: "+commentPo.getId()));
            }
            if(commentPo.getState()!=(byte)Comment.State.NEW.getCode().intValue()){
                return new ReturnObject("The comment has been audited:" +commentPo.getId());
            }
            return commentDao.updateCommentState(comment);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }

    /**
     * @Description 查看自己的评论
     */
    public ReturnObject<PageInfo<VoObject>> getSelfCommentList(Long customerId, Integer page, Integer pageSize) {
        try {
            PageInfo<CommentPo> commentPoPageInfo = commentDao.getCommentIdListByCustomerId(customerId,page,pageSize);
            List<VoObject> voObjects = commentPoPageInfo.getList().stream().map(Comment::new).collect(Collectors.toList());
            PageInfo<VoObject> returnObject = new PageInfo<>(voObjects);
            returnObject.setPages(commentPoPageInfo.getPages());
            returnObject.setPageNum(commentPoPageInfo.getPageNum());
            returnObject.setPageSize(commentPoPageInfo.getPageSize());
            returnObject.setTotal(commentPoPageInfo.getTotal());
            return new ReturnObject<>(returnObject);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }


    /**
     * @Description 查看已审核/未审核评论列表
     */
    public ReturnObject<PageInfo<VoObject>> getCommentListByShopId(Long shopId, Integer page, Integer pageSize) {
        try {
            /*
            根据店铺id获取商品id
             */

            Long goodsSkuId=null;

            PageInfo<CommentPo> commentPoPageInfo = commentDao.getCommentListByGoodsSkuId(goodsSkuId,page,pageSize);
            List<VoObject> voObjects = commentPoPageInfo.getList().stream().map(Comment::new).collect(Collectors.toList());
            PageInfo<VoObject> returnObject = new PageInfo<>(voObjects);
            returnObject.setPages(commentPoPageInfo.getPages());
            returnObject.setPageNum(commentPoPageInfo.getPageNum());
            returnObject.setPageSize(commentPoPageInfo.getPageSize());
            returnObject.setTotal(commentPoPageInfo.getTotal());
            return new ReturnObject<>(returnObject);
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }


    /**
     * @description 返回评论所有状态
     * @author Ruzhen Chang
     * @return
     */
    public ReturnObject<List<StateVo>> findCommentStates(){
        try{
            return new ReturnObject<>(commentDao.findCommentStates());
        }
        catch (Exception e){
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }

    }

}
