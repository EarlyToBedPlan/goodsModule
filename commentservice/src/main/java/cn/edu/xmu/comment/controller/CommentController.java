package cn.edu.xmu.comment.controller;

import cn.edu.xmu.comment.model.bo.Comment;
import cn.edu.xmu.comment.model.po.CommentPo;
import cn.edu.xmu.comment.model.vo.CommentVo;
import cn.edu.xmu.comment.service.CommentService;
import cn.edu.xmu.goods.model.vo.StateVo;
import cn.edu.xmu.ooad.annotation.Audit;
import cn.edu.xmu.ooad.annotation.Depart;
import cn.edu.xmu.ooad.annotation.LoginUser;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ResponseUtil;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.alibaba.druid.sql.visitor.functions.Bin;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.sardine.model.Bind;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ruzhen Chang
 */

@Api(value = "评论服务",tags = "comment")
@RestController
@RequestMapping(value = "/comment",produces = "application/json;charset=UTF-8")

public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private CommentService commentService;
    @Autowired
    private HttpServletResponse httpServletResponse;
    private Long goodsSkuId;
    //修改

    private Integer page;
    private Integer pageSize;

    /*
    @ApiOperation(value = "买家新增sku的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",dataType = "String",name="authorization",value = "用户token",required = true),
            @ApiImplicitParam(paramType = "path",dataType = "integer",name="id",value = "订单明细id",required = true),
            @ApiImplicitParam(paramType = "body",dataType = "CommentVo",name="vo",value = "评价信息"，required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功")
    })
    @Audit
    @GetMapping("/orderitems/{id}/comments")
    public Object applyGoodsSkuComment(@PathVariable("goodsSkuId")Long goodsSkuId,@PathVariable){

    }*/


    /**
     * @description:查看sku的评论列表（已通过审核）（无需登录）
     * @author: Ruzhen Chang
     */
    @ApiOperation(value = "查看sku的评论列表（已通过审核）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "sku id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name="page", required = false, dataType="Integer", paramType="query"),
            @ApiImplicitParam(name="pageSize", required = false, dataType="Integer", paramType="query")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @Audit
    @GetMapping("/sku/{id}/comments")
    public Object getGoodsSkuCommentList(@PathVariable(required = false) Long goodsSkuId,
                                         @RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer pageSize)
    {
        page = (page == null) ? 1 : page;
        pageSize = (pageSize == null) ? 10 : pageSize;
        ReturnObject<PageInfo<VoObject>> returnObject = commentService.getGoodsSkuCommentsList(goodsSkuId,page,pageSize);
        if (returnObject.getData() != null)
            return ResponseUtil.ok(returnObject.getData());
        else {
            return Common.getNullRetObj(new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg()), httpServletResponse);
        }
    }



    @ApiOperation(value = "管理员审核评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "用户token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "did", value = "店铺id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value = "评论id", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "Boolean", name = "conclusion", value = "可修改的评论信息", required = true)    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @Audit
    @PutMapping("/shops/{did}/comments/{id}/confirm")
    public Object auditComment(@PathVariable Long did, @PathVariable Long id, @Depart Long departId,@RequestBody Boolean conclusion,
                               @Validated @RequestBody CommentVo vo, BindingResult bindingResult, @LoginUser Long userId) {
        Object errors = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != errors) {
            return errors;
        }
        Comment comment = vo.createComment();
        comment.setId(id);
        if(conclusion){
            comment.setState(Comment.State.NORM);
        }
        else {
            comment.setState(Comment.State.FORBID);
        }
        ReturnObject returnObject = commentService.auditComment(comment);
        if (returnObject.getData() != null) {
            return ResponseUtil.ok(returnObject.getData());
        } else {
            return Common.getNullRetObj(new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg()), httpServletResponse);
        }
    }


    /**
     * @description:买家查看自己的评价记录
     * @author: Ruzhen Chang
     */
    @ApiOperation(value = "买家查看自己的评价记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "用户token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name="page", required = false, dataType="Integer", paramType="query"),
            @ApiImplicitParam(name="pageSize", required = false, dataType="Integer", paramType="query")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @Audit
    @GetMapping("/sku/{id}/comments")
    public Object userGetGoodsSkuCommentList(@RequestParam(required = false) Long userId,
                                         @RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer pageSize
    ) {
        page = (page == null) ? 1 : page;
        pageSize = (pageSize == null) ? 10 : pageSize;
        ReturnObject<PageInfo<VoObject>> returnObject = commentService.getSelfCommentList(userId,page,pageSize);
        if (returnObject.getData() != null)
            return ResponseUtil.ok(returnObject.getData());
        else {
            return Common.getNullRetObj(new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg()), httpServletResponse);
        }
    }

    /**
     * @description:管理员查看未审核/已审核评论列表
     * @author: Ruzhen Chang
     */
    @ApiOperation(value = "管理员查看未审核/已审核评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "用户token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name="id",value = "店铺id",required = true,dataType = "Integer",paramType = "path"),
            @ApiImplicitParam(name="page", required = false, dataType="Integer", paramType="query"),
            @ApiImplicitParam(name="pageSize", required = false, dataType="Integer", paramType="query")

    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504,message="操作id不存在")
    })
    @Audit
    @GetMapping("/shops/{id}/comments/all")
    public Object getCheckedAndUncheckComment(BindingResult bindingResult,@LoginUser Long shopId,
                                              @RequestParam(required = false) Integer page,
                                              @RequestParam(required = false) Integer pageSize
    ) {
        page = (page == null) ? 1 : page;
        pageSize = (pageSize == null) ? 10 : pageSize;
        ReturnObject<PageInfo<VoObject>> returnObject = commentService.getCommentListByShopId(shopId,page,pageSize);
        if (returnObject.getData() != null)
            return ResponseUtil.ok(returnObject.getData());
        else {
            return Common.getNullRetObj(new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg()), httpServletResponse);
        }
    }


    /**
     * @description 获取评论的所有状态
     * @author Ruzhen Chang
     * @return
     */
    @ApiOperation(value = "获得评论的所有状态")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @GetMapping("/comments/states")
    public  Object getCommentStates() {
        ReturnObject<List<StateVo>> returnObject = commentService.findCommentStates();
        return Common.decorateReturnObject(returnObject);
    }


}
