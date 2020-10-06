package com.smu.edu.service;

import com.smu.edu.domain.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smu.edu.vo.PageList;
import com.smu.edu.vo.PageParams;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-03
 */
public interface CommentService extends IService<Comment> {

    PageList<Comment> pageComment(PageParams pageParams, String courseId);

    int saveComment(Comment comment);
}
