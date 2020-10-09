package com.smu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smu.base.exception.GuliException;
import com.smu.edu.dao.CourseDao;
import com.smu.edu.domain.Course;
import com.smu.edu.domain.CourseDescription;
import com.smu.edu.service.ChapterService;
import com.smu.edu.service.CourseDescriptionService;
import com.smu.edu.service.CourseService;
import com.smu.edu.service.VideoService;
import com.smu.edu.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {
    @Resource
    private CourseDescriptionService courseDescriptionService;

    @Resource
    private VideoService videoService;

    @Resource
    private ChapterService chapterService;

    @Override
    @CacheEvict(value = "course",allEntries = true)
    public String saveCourseInfo(CourseInfo courseInfo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo,course);
        int res = baseMapper.insert(course);
        if (res==0){
            throw new GuliException(20001,"添加课程失败");
        }
        String cid = course.getId();
        CourseDescription description = new CourseDescription();
        description.setId(cid);
        description.setDescription(courseInfo.getDescription());
        courseDescriptionService.save(description);
        return cid;
    }

    @Override
    public CourseInfo getCourseInfo(String courseId) {
        Course course = baseMapper.selectById(courseId);
        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(course,courseInfo);
        CourseDescription description = courseDescriptionService.getById(courseId);
        courseInfo.setDescription(description.getDescription());
        return courseInfo;
    }

    @Override
    @CacheEvict(value = "course",allEntries = true)
    public void updateCourseInfo(CourseInfo courseInfo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo,course);
        int res = baseMapper.updateById(course);
        if (res == 0){
            throw new GuliException(20001,"修改课程信息失败");
        }
        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfo,courseDescription);
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishInfo getCoursePublishInfo(String courseId) {
        CoursePublishInfo coursePublishInfo = baseMapper.getCoursePublishInfo(courseId);
        return coursePublishInfo;
    }

    @Override
    public PageList<Course> pageCourseList(PageParams pageParams, CourseQuery courseQuery) {
        Page<Course> page = new Page<>(pageParams.getCurrent(),pageParams.getSize());
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (courseQuery==null){
            baseMapper.selectPage(page,wrapper);
            long total = page.getTotal();
            List<Course> records = page.getRecords();
            PageList<Course> pageList = new PageList<>();
            pageList.setTotal(total);
            pageList.setRecords(records);
            return pageList;
        }
        if (!StringUtils.isEmpty(courseQuery.getTitle())){
            wrapper.like("title",courseQuery.getTitle());
        }
        if (!StringUtils.isEmpty(courseQuery.getTeacherId())){
            wrapper.eq("teacher_id",courseQuery.getTeacherId());
        }
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseQuery.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQuery.getSubjectId())){
            wrapper.eq("subject_id",courseQuery.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQuery.getStatus()) && "Normal".equals(courseQuery.getStatus())) {
            wrapper.eq("status",courseQuery.getStatus());
        }else if (!StringUtils.isEmpty(courseQuery.getStatus()) && "Draft".equals(courseQuery.getStatus())){
            wrapper.eq("status",courseQuery.getStatus());
        }

        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort()) && "true".equals(courseQuery.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }else if (!StringUtils.isEmpty(courseQuery.getBuyCountSort()) && "false".equals(courseQuery.getBuyCountSort())){
            wrapper.orderByAsc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort()) && "true".equals(courseQuery.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }else if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort()) && "false".equals(courseQuery.getGmtCreateSort())){
            wrapper.orderByAsc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQuery.getPriceSort()) && "true".equals(courseQuery.getPriceSort())) {
            wrapper.orderByDesc("price");
        }else if (!StringUtils.isEmpty(courseQuery.getPriceSort()) && "false".equals(courseQuery.getPriceSort())){
            wrapper.orderByAsc("price");
        }
        //默认排序
        wrapper.orderByDesc("gmt_modified");
        baseMapper.selectPage(page,wrapper);
        long total = page.getTotal();
        List<Course> records = page.getRecords();
        PageList<Course> pageList = new PageList<>();
        pageList.setTotal(total);
        pageList.setRecords(records);
        return pageList;
    }

    @Override
    @Transactional
    @CacheEvict(value = "course",allEntries = true)
    public void removeCourseById(String courseId) {
        videoService.removeByCourseId(courseId);
        chapterService.removeByCourseId(courseId);
        courseDescriptionService.removeById(courseId);
        int result = baseMapper.deleteById(courseId);
        if (result!=1){
            throw new GuliException(20001,"删除课程失败");
        }
    }

    @Override
    @Cacheable(value = "course",key = "'indexCourseList'")
    public List<Course> indexCourse() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");
        List<Course> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }

    @Override
    public List<Course> getCourseListByTeacherId(String teacherId) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<Course> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }

    @Override
    public CourseInfo frontGetCourseInfo(String courseId) {
        CourseInfo courseInfo = baseMapper.frontGetCourseInfo(courseId);
        return courseInfo;
    }
}
