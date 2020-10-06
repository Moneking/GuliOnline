package com.smu.edu.controller.front;

import com.smu.edu.domain.Course;
import com.smu.edu.domain.Teacher;
import com.smu.edu.service.CourseService;
import com.smu.edu.service.TeacherService;
import com.smu.edu.vo.PageList;
import com.smu.edu.vo.PageParams;
import com.smu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/edu/frontTeacher")
@Api(tags="前台讲师管理")
public class TeacherFrontController {

    @Resource
    private TeacherService teacherService;

    @Resource
    private CourseService courseService;

    @GetMapping("/frontPageTeacherList/{current}/{size}")
    @ApiOperation(value = "无条件分页查询")
    public Result frontPageTeacherList(@PathVariable("current") Long current, @PathVariable("size") Long size){
        PageParams pageParams = new PageParams();
        pageParams.setCurrent(current);
        pageParams.setSize(size);
        PageList<Teacher> pageList = teacherService.frontPageTeacherList(pageParams);
        return Result.success().data("total",pageList.getTotal()).data("rows",pageList.getRecords());
    }

    @GetMapping("/frontGetTeacherInfo/{teacherId}")
    public Result frontGetTeacherInfo(@PathVariable("teacherId") String teacherId){
        Teacher teacher = teacherService.getTeacherById(teacherId);

        List<Course> courseList = courseService.getCourseListByTeacherId(teacherId);
        return Result.success().data("teacher",teacher).data("courseList",courseList);
    }

}
