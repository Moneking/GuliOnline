package com.smu.edu.controller.front;

import com.smu.edu.client.OrderClient;
import com.smu.edu.domain.Course;
import com.smu.edu.service.ChapterService;
import com.smu.edu.service.CourseService;
import com.smu.edu.vo.*;
import com.smu.utils.ClientCourseInfo;
import com.smu.utils.JwtUtils;
import com.smu.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/edu/frontCourse")
@Api(tags="前台课程管理")
public class CourseFrontController {

    @Resource
    private CourseService courseService;

    @Resource
    private ChapterService chapterService;

    @Resource
    private OrderClient orderClient;

    @PostMapping("/pageCourseCondition/{current}/{size}")
    public Result pageCourseCondition(@PathVariable("current") Long current,@PathVariable("size") Long size, @RequestBody(required = false) CourseQuery courseQuery){
        PageParams pageParams = new PageParams();
        pageParams.setCurrent(current);
        pageParams.setSize(size);
        PageList<Course> pageList = courseService.pageCourseList(pageParams,courseQuery);
        return Result.success().data("total",pageList.getTotal()).data("rows",pageList.getRecords());
    }


    @GetMapping("/frontGetCourseInfo/{courseId}")
    public Result frontGetCourseInfo(@PathVariable("courseId") String courseId, HttpServletRequest request){
        //根据课程ID获取课程信息
        CourseInfo courseInfo = courseService.frontGetCourseInfo(courseId);

        //根据课程ID获取章节小节
        List<ChapterList> list = chapterService.listChapter(courseId);

        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean buyCourse = false;
        if (!StringUtils.isEmpty(memberId)){
            buyCourse = orderClient.isBuyCourse(courseId, memberId);
        }
        return Result.success().data("list",list).data("courseInfo",courseInfo).data("isBuyCourse",buyCourse);
    }
    @GetMapping("/getCourseInfoByCourseId/{courseId}")
    public ClientCourseInfo getCourseInfoByCourseId(@PathVariable("courseId") String courseId){
        CourseInfo courseInfo = courseService.frontGetCourseInfo(courseId);
        ClientCourseInfo clientCourseInfo = new ClientCourseInfo();
        BeanUtils.copyProperties(courseInfo,clientCourseInfo);
        return clientCourseInfo;
    }
}
