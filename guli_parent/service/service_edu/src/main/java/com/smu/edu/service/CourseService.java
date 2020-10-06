package com.smu.edu.service;

import com.smu.edu.domain.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smu.edu.vo.*;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfo courseInfo);

    CourseInfo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfo courseInfo);

    CoursePublishInfo getCoursePublishInfo(String courseId);

    PageList<Course> pageCourseList(PageParams pageParams, CourseQuery courseQuery);

    void removeCourseById(String courseId);

    List<Course> indexCourse();

    List<Course> getCourseListByTeacherId(String teacherId);

    CourseInfo frontGetCourseInfo(String courseId);
}
