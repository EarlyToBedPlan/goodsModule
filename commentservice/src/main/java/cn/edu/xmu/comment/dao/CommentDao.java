package cn.edu.xmu.comment.dao;

import cn.edu.xmu.comment.mapper.CommentPoMapper;
import cn.edu.xmu.comment.model.bo.Comment;
import cn.edu.xmu.comment.model.po.CommentPo;
import cn.edu.xmu.comment.model.po.CommentPoExample;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.ooad.util.encript.SHA256;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public class CommentDao implements InitializingBean{
    @Autowired
    private CommentPoMapper commentPoMapper;

    private static final Logger logger=LoggerFactory.getLogger(CommentDao.class);

    @Value("${privilegeservice.initialization}")
    private Boolean initialization;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    /**
     * @Description 获得评论的所有状态
     * @author Ruzhen Chang
     */
    public byte getCommentState(long commentId){
        CommentPo commentPo=new CommentPo();
        Byte commentState=0;
        try{
            commentPo=commentPoMapper.selectByPrimaryKey(commentId);
            commentState= commentPo.getState();
        }catch (DataAccessException e){
            logger.debug("getCommentState" + e.getMessage());
        }
        return commentState;
    }

    /**
     * @Description 新增sku评论
     * @author Ruzhen Chang
     *
     *
     */
    public ReturnObject applyGoodsSkuComment(long goodsSkuId,long customerId,long orderItemId,byte type,String content){
        ReturnObject retobj=null;
        CommentPo commentPo=new CommentPo();
        try {
            retobj = new ReturnObject(commentPoMapper.insert(commentPo));
            logger.debug("success apply comment:" + commentPo.getId());
            commentPo.setState((byte)Comment.State.NEW.getCode().intValue());
            commentPo.setContent(content);
            commentPo.setCustomerId(customerId);
            commentPo.setGoodsSkuId(goodsSkuId);
            commentPo.setGmtCreated(LocalDateTime.now());
            commentPo.setGmtModified(LocalDateTime.now());
            commentPo.setOrderitemId(orderItemId);
            commentPo.setType(type);
        }catch (DataAccessException e){
            logger.debug("apply comment failed:"+e.getMessage());
            retobj =new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST,String.format("发生数据库错误：%s",e.getMessage()));
        }
        return retobj;
    }

    /**
     * @Description 由商品id获得评论列表
     * @Param shopId
     * @author Ruzhen Chang
     */
    public List<CommentPo> getGoodsSkuCommentList(long goodsSkuId){
        CommentPoExample example=new CommentPoExample();
        CommentPoExample.Criteria criteria=example.createCriteria();
        criteria.andGoodsSkuIdEqualTo(goodsSkuId);
        List<CommentPo> commentPos=commentPoMapper.selectByExample(example);

        for(CommentPo commentPo:commentPos) {
            try {
                if((byte)commentPo.getState()==Comment.State.NORM.getCode()) {
                    commentPos.add(commentPo);
                    logger.debug("getGoodsSkuCommentList: goodsSkuId = " + commentPo.getGoodsSkuId());
                }else {
                    logger.debug("getGoodsSkuCommentList: id not exist = " + commentPo.getGoodsSkuId());
                }
            } catch (DataAccessException e) {
                logger.debug("getGoodsSkuCommentList:" + e.getMessage());
            }
        }
        return commentPos;
    }


    /**
     * @Description 管理员审核评论
     * @author Ruzhen Chang
     */
    public ReturnObject<Object> auditComment(long commentId,boolean conclusion){
        ReturnObject returnObject=null;
        CommentPo commentPo=new CommentPo();
        commentPo.setId(commentId);
        if(conclusion)
            commentPo.setState((byte)Comment.State.NORM.getCode().intValue());
        else
            commentPo.setState((byte)Comment.State.FORBID.getCode().intValue());

        CommentPoExample commentPoExample=new CommentPoExample();
        CommentPoExample.Criteria criteria=commentPoExample.createCriteria();

        criteria.andIdEqualTo(commentId);
        try{
            int ret=commentPoMapper.deleteByPrimaryKey(commentId);
            if(ret==0){
                logger.debug("auditComment: id not exist ="+commentId);
                returnObject=new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST,String.format("操作的评论id不存在"+commentId));
            }else {
                logger.debug("auditShop: audited comment id ="+commentId);
                returnObject=new ReturnObject();
            }
        }catch (Exception e){
            logger.error("发生了严重的内部错误："+e.getMessage());
        }
        return returnObject;
    }

    /**
     * @Description 查看自己的评论
     * @author Ruzhen Chang
     */
    public List<CommentPo> getSelfCommentList(long userId){
        CommentPoExample example=new CommentPoExample();
        CommentPoExample.Criteria criteria=example.createCriteria();
        criteria.andCustomerIdEqualTo(userId);
        List<CommentPo> commentPos=commentPoMapper.selectByExample(example);

        for(CommentPo commentPo:commentPos) {
            try {
                if((byte)commentPo.getState()==Comment.State.NORM.getCode()) {
                    commentPos.add(commentPo);
                    logger.debug("getSelfCommentList: goodsSkuId = " + commentPo.getGoodsSkuId());
                }else {
                    logger.debug("getSelfCommentList: id not exist = " + commentPo.getGoodsSkuId());
                }
            } catch (DataAccessException e) {
                logger.debug("getSelfCommentList:" + e.getMessage());
            }
        }
        return commentPos;
    }


    /**
     * @Description 查看已审核/未审核评论列表
     * @author Ruzhen Chang
     */
    public List<CommentPo> getAllStateComment(){
        CommentPoExample example=new CommentPoExample();
        CommentPoExample.Criteria criteria=example.createCriteria();
        criteria.andStateNotEqualTo((byte) Comment.State.FORBID.getCode().intValue());
        List<CommentPo> commentPos=commentPoMapper.selectByExample(example);

        for(CommentPo commentPo:commentPos) {
            try {
                if((byte)commentPo.getState()==Comment.State.NORM.getCode()) {
                    commentPos.add(commentPo);
                    logger.debug("getAllStateComment: goodsSkuId = " + commentPo.getGoodsSkuId());
                }else {
                    logger.debug("getAllStateComment: id not exist = " + commentPo.getGoodsSkuId());
                }
            } catch (DataAccessException e) {
                logger.debug("getAllStateComment:" + e.getMessage());
            }
        }
        return commentPos;
    }



}
