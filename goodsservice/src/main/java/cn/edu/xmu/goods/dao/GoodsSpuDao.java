package cn.edu.xmu.goods.dao;

import cn.edu.xmu.goods.mapper.GoodsSkuPoMapper;
import cn.edu.xmu.goods.mapper.GoodsSpuPoMapper;
import cn.edu.xmu.goods.model.bo.GoodsSpu;
import cn.edu.xmu.goods.model.po.GoodsSkuPo;
import cn.edu.xmu.goods.model.po.GoodsSkuPoExample;
import cn.edu.xmu.goods.model.po.GoodsSpuPo;
import cn.edu.xmu.goods.model.po.GoodsSpuPoExample;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;
/**
 * SPU Dao
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/1 19:55
 * modifiedBy Yancheng Lai 19:55
 **/

@Repository
public class GoodsSpuDao {

    private static final Logger logger = LoggerFactory.getLogger(GoodsSpuDao.class);

    @Autowired
    private GoodsSkuPoMapper goodsSkuPoMapper;

    @Autowired
    private GoodsSpuPoMapper goodsSpuPoMapper;

    /**
    * @Description:  查询GoodsSpuPo以id
    * @Param: [id]
    * @return: GoodsSpu
    * @Author: Yancheng Lai
    * @Date: 2020/12/1 22:18
    */
    public ReturnObject<GoodsSpuPo> getGoodsSpuPoById(Long id) {
        //以后改成selectbyprimarykey

        GoodsSpuPoExample example = new GoodsSpuPoExample();
        GoodsSpuPoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<GoodsSpuPo> goodsSpuPos = null;
        try {
            goodsSpuPos = goodsSpuPoMapper.selectByExample(example);
        } catch (DataAccessException e) {
            StringBuilder message = new StringBuilder().append("getGoodsSpuPoById: ").append(e.getMessage());
            logger.error(message.toString());
        }
        if (null == goodsSpuPos || goodsSpuPos.isEmpty()) {
            return new ReturnObject<>();
        } else {
                return new ReturnObject<>(new GoodsSpuPo(goodsSpuPos.get(0)));
            }
        }




}
