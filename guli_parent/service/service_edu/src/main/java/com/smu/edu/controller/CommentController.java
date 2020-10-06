package com.smu.edu.controller;


import com.smu.edu.client.UcenterClient;
import com.smu.edu.domain.Comment;
import com.smu.edu.service.CommentService;
import com.smu.edu.vo.PageList;
import com.smu.edu.vo.PageParams;
import com.smu.utils.JwtUtils;
import com.smu.utils.MemberInfo;
import com.smu.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-03
 */
@RestController
@RequestMapping("/edu/comment")
public class CommentController {

    @Resource
    private UcenterClient ucenterClient;

    @Resource
    private CommentService commentService;

    @ApiOperation(value = "评论分页列表")
    @GetMapping("/pageComment/{current}/{size}")
    public Result pageComment(@PathVariable("current") Long current,@PathVariable("size") Long size,String courseId){
        PageParams pageParams = new PageParams();
        pageParams.setCurrent(current);
        pageParams.setSize(size);
        PageList<Comment> pageList = commentService.pageComment(pageParams,courseId);
        return Result.success().data("total",pageList.getTotal()).data("rows",pageList.getRecords());
    }

    @PostMapping("/saveComment")
    public Result saveComment(@RequestBody Comment comment, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)){
            return Result.error().code(28004).message("请先登录");
        }
        comment.setMemberId(memberId);
        MemberInfo member = ucenterClient.getMemberInfoById(memberId);
        comment.setNickname(member.getNickname());
        comment.setAvatar(member.getAvatar());
        int result = commentService.saveComment(comment);
        if (result==1){
            return Result.success();
        }else {
            return Result.error().message("添加评论失败");
        }
    }
}

