package com.smu.edu.controller;


import com.smu.edu.domain.Teacher;
import com.smu.edu.service.TeacherService;
import com.smu.edu.vo.PageList;
import com.smu.edu.vo.PageParams;
import com.smu.edu.vo.TeacherQuery;
import com.smu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 讲师 前端控制器
 *
 * @author testjava
 * @since 2020-08-26
 */
@RestController
@RequestMapping("/edu/teacher")
@Api(tags="讲师管理")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @GetMapping("/findAll")
    @ApiOperation(value = "获取所有讲师列表")
    public Result findAllTeacher(){

        List<Teacher> list = teacherService.findAll();

        return Result.success().data("items",list);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id逻辑删除讲师")
    public Result deleteTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable("id") String id){

        int result = teacherService.deleteById(id);

        if (result==1){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @GetMapping("/pageTeacher/{current}/{size}")
    @ApiOperation(value = "无条件分页查询")
    public Result pageTeacher(@PathVariable("current") Long current,@PathVariable("size") Long size){
        PageParams pageParams = new PageParams();
        pageParams.setCurrent(current);
        pageParams.setSize(size);
        PageList<Teacher> pageList = teacherService.frontPageTeacherList(pageParams);
        return Result.success().data("total",pageList.getTotal()).data("rows",pageList.getRecords());
    }

    @PostMapping("/pageTeacherCondition/{current}/{size}")
    @ApiOperation(value = "多条件组合分页查询讲师")
    public Result pageTeacherCondition(@PathVariable("current") Long current, @PathVariable("size") Long size, @RequestBody(required = false) TeacherQuery teacherQuery){
        PageParams pageParams = new PageParams();
        pageParams.setCurrent(current);
        pageParams.setSize(size);
        PageList<Teacher> pageList = teacherService.pageTeacherList(pageParams,teacherQuery);
        return Result.success().data("total",pageList.getTotal()).data("rows",pageList.getRecords());
    }

    @PostMapping("/addTeacher")
    @ApiOperation(value = "添加讲师")
    public Result addTeacher(@RequestBody(required = false) Teacher teacher){
        int result = teacherService.addTeacher(teacher);
        if (result==1){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @GetMapping("/getTeacher/{id}")
    @ApiOperation(value = "根据id查询讲师")
    public Result getTeacher(@PathVariable("id") String id){
        Teacher teacher = teacherService.getTeacherById(id);
        return Result.success().data("teacher",teacher);
    }

    @PostMapping("/updateTeacher")
    @ApiOperation(value = "更新讲师信息")
    public Result updateTeacher(@RequestBody(required = false) Teacher teacher){
        int result = teacherService.updateTeacherById(teacher);
        if (result==1){
            return Result.success();
        }else {
            return Result.error();
        }
    }
}

