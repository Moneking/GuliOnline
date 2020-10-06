package com.smu.edu.dao;

import com.smu.edu.domain.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smu.edu.vo.CourseInfo;
import com.smu.edu.vo.CoursePublishInfo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
public interface CourseDao extends BaseMapper<Course> {
    public CoursePublishInfo getCoursePublishInfo(String courseId);

    CourseInfo frontGetCourseInfo(String courseId);
}
