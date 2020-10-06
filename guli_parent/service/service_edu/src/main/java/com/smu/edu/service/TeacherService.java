package com.smu.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smu.edu.domain.Teacher;
import com.smu.edu.vo.PageList;
import com.smu.edu.vo.PageParams;
import com.smu.edu.vo.TeacherQuery;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-08-26
 */
public interface TeacherService extends IService<Teacher> {

    PageList<Teacher> pageTeacherList(PageParams pageParams, TeacherQuery teacherQuery);

    List<Teacher> indexTeacher();

    List<Teacher> findAll();

    int deleteById(String id);

    Page<Teacher> pageList(Long current, Long size);

    int addTeacher(Teacher teacher);

    Teacher getTeacherById(String id);

    int updateTeacherById(Teacher teacher);

    PageList<Teacher> frontPageTeacherList(PageParams pageParams);
}
