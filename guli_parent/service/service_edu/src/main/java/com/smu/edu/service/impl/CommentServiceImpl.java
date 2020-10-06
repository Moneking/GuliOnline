package com.smu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smu.edu.domain.Comment;
import com.smu.edu.dao.CommentDao;
import com.smu.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smu.edu.vo.PageList;
import com.smu.edu.vo.PageParams;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-03
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Override
    public PageList<Comment> pageComment(PageParams pageParams, String courseId) {
        Page<Comment> page = new Page<>(pageParams.getCurrent(),pageParams.getSize());
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(page,wrapper);
        List<Comment> records = page.getRecords();
        long total = page.getTotal();

        PageList<Comment> pageList = new PageList<>();
        pageList.setRecords(records);
        pageList.setTotal(total);
        return pageList;
    }

    @Override
    public int saveComment(Comment comment) {
        int result = baseMapper.insert(comment);
        return result;
    }
}
