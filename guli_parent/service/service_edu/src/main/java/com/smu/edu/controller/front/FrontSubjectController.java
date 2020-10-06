package com.smu.edu.controller.front;


import com.smu.edu.service.SubjectService;
import com.smu.edu.vo.SubjectList;
import com.smu.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-20
 */
@RestController
@RequestMapping("/edu/frontSubject")
public class FrontSubjectController {
    @Resource
    private SubjectService subjectService;

    @GetMapping("/getAllSubject")
    public Result getAllSubject(){
        List<SubjectList> list = subjectService.getAllSubject();
        return Result.success().data("list",list);
    }

}

