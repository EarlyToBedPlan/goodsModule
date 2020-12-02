package cn.edu.xmu.goods.dao;

import cn.edu.xmu.goods.mapper.GoodsCategoryPoMapper;
import cn.edu.xmu.goods.model.bo.GoodsCategory;
import cn.edu.xmu.goods.model.po.GoodsCategoryPo;
import cn.edu.xmu.goods.model.po.GoodsCategoryPoExample;
import cn.edu.xmu.goods.model.vo.GoodsCategoryRetVo;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * @Author：谢沛辰
 * @Date: 2020.11.30
 * @Description:商品类别dao
 */
@Repository
public class GoodsCategoryDao {

    private static final Logger logger = LoggerFactory.getLogger(GoodsCategoryDao.class);
    @Autowired
    GoodsCategoryPoMapper goodsCategoryPoMapper;

    public ReturnObject<List> getCategoryByPID(Long pid){
        GoodsCategoryPoExample example=new GoodsCategoryPoExample();
        GoodsCategoryPoExample.Criteria criteria= example.createCriteria();
        criteria.andPidEqualTo(pid);
        List<GoodsCategoryPo> goodsCategorys=null;
        try {
            goodsCategorys= goodsCategoryPoMapper.selectByExample(example);
        } catch (DataAccessException e) {
            StringBuilder message = new StringBuilder().append("getUserByName: ").append(e.getMessage());
            logger.error(message.toString());
        }
        if(goodsCategorys==null || goodsCategorys.isEmpty())
        {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        List<GoodsCategoryRetVo> list=new ArrayList<>();
        for (GoodsCategoryPo goodsCategory : goodsCategorys) {
            list.add(new GoodsCategory(goodsCategory).createVo());
        }
        return new ReturnObject<>(list);
    }

    public ReturnObject<GoodsCategoryPo> insertSubcategory(GoodsCategory goodsCategory){
        GoodsCategoryPo goodsCategoryPo=goodsCategory.createPo();
        ReturnObject<GoodsCategoryPo> retObj=null;

        try{
            retObj=new ReturnObject<>(goodsCategoryPoMapper.selectByPrimaryKey((long)goodsCategoryPoMapper.insert(goodsCategoryPo)));
        }
        catch (DataAccessException e) {
            // 其他数据库错误
            logger.debug("other sql exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        }
        catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
        return retObj;
    }

    public ReturnObject<Object> deleteCategory(Long id){
        ReturnObject<Object> retObj = null;
        try{
            int ret=goodsCategoryPoMapper.deleteByPrimaryKey(id);
            if (ret == 0) {
            //删除类别
                logger.debug("deleteCategory: id not exist = " + id);
                retObj = new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("id不存在：" + id));
            }else {
            retObj=new ReturnObject<>();
            }
        }
        catch(DataAccessException e)
        {
            logger.debug("other sql exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        }
        catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }

        return retObj;
    }

    public ReturnObject<Object> updateCategory(GoodsCategory goodsCategory){
        ReturnObject<Object> retObj =null;
        GoodsCategoryPo categoryPo=goodsCategory.createPo();
        try{
            int ret=goodsCategoryPoMapper.updateByPrimaryKey(categoryPo);
            if(ret==0){
                //修改失败
                logger.debug("updateCategory: update category fail : " + categoryPo.toString());
                retObj = new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST, String.format("id不存在：" + categoryPo.getId()));
            } else{
                //修改成功
                logger.debug("updateCategory: update category = " + categoryPo.toString());
                retObj = new ReturnObject<>();
            }
        }
        catch(DataAccessException e){
            logger.debug("other sql exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        }
        catch(Exception e){
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
        return retObj;
    }

    public ReturnObject<GoodsCategory> getCategoryById(Long id){
        GoodsCategoryPo categoryPo= null;
        List<GoodsCategoryPo> goodsCategorys=null;
        try {
            categoryPo=goodsCategoryPoMapper.selectByPrimaryKey(id);
        } catch (DataAccessException e) {
            StringBuilder message = new StringBuilder().append("getUserByName: ").append(e.getMessage());
            logger.error(message.toString());
        }
        if(categoryPo==null)
        {
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        GoodsCategory category=new GoodsCategory(categoryPo);
        return new ReturnObject<>(category);
    }

}
