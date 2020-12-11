package cn.edu.xmu.comment.dao;

import cn.edu.xmu.comment.mapper.CommentPoMapper;
import cn.edu.xmu.comment.model.bo.Comment;
import cn.edu.xmu.comment.model.po.CommentPo;
import cn.edu.xmu.comment.model.po.CommentPoExample;
import cn.edu.xmu.goods.model.vo.StateVo;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.shop.model.bo.Shop;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Ruzhen Chang
 */
@Repository
public class CommentDao implements InitializingBean{
    @Autowired
    private CommentPoMapper commentPoMapper;

    private static final Logger logger=LoggerFactory.getLogger(CommentDao.class);

    @Value("${commentservice.initialization}")
    private Boolean initialization;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    /**
     * @Description 获得评论详细信息
     * @author Ruzhen Chang
     */
    public CommentPo getCommentById(Long commentId){
        CommentPo commentPo=new CommentPo();
        try{
            commentPo=commentPoMapper.selectByPrimaryKey(commentId);
        } catch (Exception e){
            StringBuilder message=new StringBuilder().append("getCommentById:").append(e.getMessage());
            logger.debug(message.toString());
        }
        return commentPo;
    }


    /**
     * @Description 获得评论的所有状态
     * @author Ruzhen Chang
     */
    public byte getCommentState(long commentId){
        CommentPo commentPo=new CommentPo();
        byte commentState= commentPo.getState();
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
     */
    public ReturnObject newGoodsSkuComment(long goodsSkuId,long customerId,long orderItemId,byte type,String content){
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
    public List<CommentPo> getGoodsSkuAllCommentList(long goodsSkuId,byte state){
        CommentPoExample example=new CommentPoExample();
        CommentPoExample.Criteria criteria=example.createCriteria();
        criteria.andGoodsSkuIdEqualTo(goodsSkuId);
        criteria.andStateEqualTo(state);
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
     * @Description 根据商品skuId获得评论id列表
     * @author Ruzhen Chang
     */
    public PageInfo<CommentPo> getCommentListByGoodsSkuId(Long goodsSkuId, Integer page, Integer pageSize){
        PageHelper.startPage(page,pageSize);
        CommentPoExample example=new CommentPoExample();
        CommentPoExample.Criteria criteria=example.createCriteria();
        criteria.andGoodsSkuIdEqualTo(goodsSkuId);
        List<CommentPo> commentPos=commentPoMapper.selectByExample(example);
        logger.debug("getCommentIdListByGoodsSkuId:" +goodsSkuId);
        return new PageInfo<>(commentPos);
    }



    /**
     * @Description 根据用户id获得评论id列表
     * @author Ruzhen Chang
     */
    public PageInfo<CommentPo> getCommentIdListByCustomerId(long customerId,Integer page,Integer pageSize){

        PageHelper.startPage(page,pageSize);
        CommentPoExample example=new CommentPoExample();
        CommentPoExample.Criteria criteria=example.createCriteria();
        criteria.andCustomerIdEqualTo(customerId);
        List<CommentPo> commentPos=commentPoMapper.selectByExample(example);
        logger.debug("getCommentIdListByCustomerId:" +customerId);
        return new PageInfo<>(commentPos);
    }


    /**
     * @Description 修改评论状态
     * @author Ruzhen Chang
     */
    public ReturnObject updateCommentState(Comment comment){
        CommentPo commentPo=new CommentPo();
        commentPo.setState((byte)comment.getState().getCode().intValue());
        ReturnObject returnObject=null;
        try{
            int ret=commentPoMapper.updateByPrimaryKeySelective(commentPo);
            if(ret==0){
                logger.debug("updateCommentState: update faild. comment id:"+commentPo.getId());
                returnObject=new ReturnObject(ResponseCode.RESOURCE_ID_NOTEXIST);
            } else {
                logger.debug("updateCommentState: update success:"+commentPo.getId());
                returnObject=new ReturnObject();
            }
        } catch (Exception e) {
            logger.error("发生了严重的服务器内部错误：" + e.getMessage());
        }
        return returnObject;
    }


    /**
     * @Description 获取评论所有状态
     * @author Ruzhen Chang
     */
    public List<StateVo> findCommentStates(){
        List<StateVo> stateVos= null;
        for (int i = 0; i < 3; i++) {
            StateVo vo=new StateVo((byte) i,Comment.State.getTypeByCode(i).name());
            stateVos.add(vo);
        }
        return stateVos;
    }

}
