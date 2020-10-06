package com.smu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smu.edu.dao.TeacherDao;
import com.smu.edu.domain.Teacher;
import com.smu.edu.service.TeacherService;
import com.smu.edu.vo.PageList;
import com.smu.edu.vo.PageParams;
import com.smu.edu.vo.TeacherQuery;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-08-26
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, Teacher> implements TeacherService {

    @Override
    public PageList<Teacher> pageTeacherList(PageParams pageParams, TeacherQuery teacherQuery) {
        Page<Teacher> page = new Page<>(pageParams.getCurrent(),pageParams.getSize());
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (name!=null){
            wrapper.like("name",name);
        }
        if (level!=null){
            wrapper.eq("level",level);
        }
        if (begin!=null){
            wrapper.ge("gmt_create",begin);
        }
        if (end!=null){
            wrapper.le("gmt_modified",end);
        }

        wrapper.orderByDesc("gmt_modified");

        baseMapper.selectMapsPage(page,wrapper);
        PageList<Teacher> pageList = new PageList<>();
        pageList.setTotal(page.getTotal());
        pageList.setRecords(page.getRecords());
        return pageList;
    }

    @Override
    @Cacheable(value = "teacher",key = "'indexTeacherList'")
    public List<Teacher> indexTeacher() {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        wrapper.last("limit 4");
        List<Teacher> teacherList = baseMapper.selectList(wrapper);
        return teacherList;
    }

    @Override
    public List<Teacher> findAll() {
        List<Teacher> list = baseMapper.selectList(null);
        return list;
    }

    @Override
    @CacheEvict(value = "teacher",allEntries = true)
    public int deleteById(String id) {
        int result = baseMapper.deleteById(id);
        return result;
    }

    @Override
    public Page<Teacher> pageList(Long current, Long size) {
        Page<Teacher> page = new Page<>(current,size);
        baseMapper.selectPage(page, null);
        return page;
    }

    @Override
    @CacheEvict(value = "teacher",allEntries = true)
    public int addTeacher(Teacher teacher) {
        int result = baseMapper.insert(teacher);
        return result;
    }

    @Override
    public Teacher getTeacherById(String id) {
        Teacher teacher = baseMapper.selectById(id);
        return teacher;
    }

    @Override
    @CacheEvict(value = "teacher",allEntries = true)
    public int updateTeacherById(Teacher teacher) {
        int result = baseMapper.updateById(teacher);
        return result;
    }

    @Override
    public PageList<Teacher> frontPageTeacherList(PageParams pageParams) {
        Page<Teacher> page = new Page<>(pageParams.getCurrent(),pageParams.getSize());
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        baseMapper.selectMapsPage(page,wrapper);

        PageList<Teacher> pageList = new PageList<>();
        pageList.setTotal(page.getTotal());
        pageList.setRecords(page.getRecords());
        return pageList;
    }

}
