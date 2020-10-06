package com.smu.edu.controller;


import com.smu.edu.domain.Course;
import com.smu.edu.service.CourseService;
import com.smu.edu.vo.*;
import com.smu.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
@RestController
@RequestMapping("/edu/course")
public class CourseController {
    @Resource
    private CourseService courseService;

    @PostMapping("/pageCourseCondition/{current}/{size}")
    public Result pageCourseCondition(@PathVariable("current") Long current,@PathVariable("size") Long size, @RequestBody(required = false) CourseQuery courseQuery){
        PageParams pageParams = new PageParams();
        pageParams.setCurrent(current);
        pageParams.setSize(size);
        PageList<Course> pageList = courseService.pageCourseList(pageParams,courseQuery);
        return Result.success().data("total",pageList.getTotal()).data("rows",pageList.getRecords());
    }


    @PostMapping("/addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfo courseInfo){
        String courseId = courseService.saveCourseInfo(courseInfo);
        return Result.success().data("courseId",courseId);
    }

    @GetMapping("/getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable("courseId") String courseId){
        CourseInfo courseInfo = courseService.getCourseInfo(courseId);
        return Result.success().data("courseInfo",courseInfo);
    }

    @PostMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfo courseInfo){
        courseService.updateCourseInfo(courseInfo);
        return Result.success();
    }

    @GetMapping("/getCoursePublishInfo/{courseId}")
    public Result getCoursePublishInfo(@PathVariable String courseId){
        CoursePublishInfo coursePublishInfo = courseService.getCoursePublishInfo(courseId);
        return Result.success().data("coursePublishInfo",coursePublishInfo);
    }

    @PostMapping("/publishCourse/{courseId}")
    public Result publishCourse(@PathVariable String courseId){
        Course course = new Course();
        course.setId(courseId);
        course.setStatus("Normal");
        courseService.updateById(course);
        return Result.success();
    }

    @DeleteMapping("/deleteCourse/{courseId}")
    public Result deleteCourse(@PathVariable String courseId){
        courseService.removeCourseById(courseId);
        return Result.success();
    }
}

