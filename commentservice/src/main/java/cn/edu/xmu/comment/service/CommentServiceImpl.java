package cn.edu.xmu.comment.service;

/**
 * @author Ruzhen Chang
 */

import cn.edu.xmu.comment.dao.CommentDao;
import cn.edu.xmu.comment.model.bo.Comment;
import cn.edu.xmu.comment.model.po.CommentPo;
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
    public ReturnObject<PageInfo<VoObject>> getGoodsSkuCommentsList(long goodsSkuId, Integer page, Integer pagesize) {
        return null;
    }

    /**
     * @Description 由商品id获得评论列表
     */
    public ReturnObject<PageInfo<VoObject>> getGoodsSkuCommentList(long goodsSkuId, Integer page, Integer pagesize){
        PageInfo<VoObject> retobj=null;
        try{
            List<CommentPo> commentPos = commentDao.getCommentIdListByGoodsSkuId(goodsSkuId);
            List<VoObject> commentVos = new ArrayList<>(commentPos.size());
            for(CommentPo po:commentPos){
                CommentPo commentPo=commentDao.getCommentById(po.getId());
                Comment comment=new Comment(po);
                commentVos.add(comment.createSimpleVo());
            }
            PageHelper.startPage(page,pagesize);
            retobj = new PageInfo<>(commentVos);
            return new ReturnObject<>(retobj);
        } catch (Exception e){
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }


    /**
     * @Description 管理员审核评论
     * @author Ruzhen Chang
     */
    public ReturnObject auditComment(Comment comment) {
        try{
            CommentPo commentPo=commentDao.getCommentById(comment.getId());
            if(commentPo==null){
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST,String.format("评论不存在 id: "+commentPo.getId()));
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
    public ReturnObject<PageInfo<VoObject>> getSelfCommentList(long customerId, Integer page, Integer pagesize) {
        PageInfo<VoObject> retobj=null;
        try{
            List<CommentPo> commentPos = commentDao.getCommentIdListByCustomerId(customerId);
            List<VoObject> commentVos = new ArrayList<>(commentPos.size());
            for(CommentPo po:commentPos){
                CommentPo commentPo=commentDao.getCommentById(po.getId());
                Comment comment=new Comment(po);
                commentVos.add(comment.createSimpleVo());
            }
            PageHelper.startPage(page,pagesize);
            retobj = new PageInfo<>(commentVos);
            return new ReturnObject<>(retobj);
        } catch (Exception e){
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }


    /**
     * @Description 查看已审核/未审核评论列表
     */
    public ReturnObject<PageInfo<VoObject>> getAllCommentListByShopId(long shopId, Integer page, Integer pagesize) {
        PageInfo<VoObject> retobj=null;

        try{
            /*
            根据店铺获得skuIdList
             */
            List<CommentPo> commentPos = commentDao.getCommentIdListByGoodsSkuId(shopId);
            List<VoObject> commentVos = new ArrayList<>(commentPos.size());
            for(CommentPo po:commentPos){
                CommentPo commentPo=commentDao.getCommentById(po.getId());
                Comment comment=new Comment(po);
                commentVos.add(comment.createSimpleVo());
            }
            PageHelper.startPage(page,pagesize);
            retobj = new PageInfo<>(commentVos);
            return new ReturnObject<>(retobj);
        } catch (Exception e){
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }
    }


}
