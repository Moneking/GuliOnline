package com.smu.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smu.edu.dao.SubjectDao;
import com.smu.edu.domain.Subject;
import com.smu.edu.domain.excel.SubjectData;
import com.smu.edu.listener.SubjectExcelListener;
import com.smu.edu.service.SubjectService;
import com.smu.edu.vo.SubjectList;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-20
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectDao, Subject> implements SubjectService {

    @Override
    public void saveSubject(MultipartFile file,SubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SubjectList> getAllSubject() {
        QueryWrapper<Subject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<Subject> oneSubject = baseMapper.selectList(wrapperOne);

        List<SubjectList> list = new ArrayList<>();
        for (Subject subject:oneSubject){
            //封装一级分类
            SubjectList subjectList = new SubjectList();
            BeanUtils.copyProperties(subject,subjectList);
            list.add(subjectList);
            //封装二级分类
            QueryWrapper<Subject> wrapperTwo = new QueryWrapper<>();
            wrapperTwo.eq("parent_id",subject.getId());
            List<Subject> twoSubject = baseMapper.selectList(wrapperTwo);
            subjectList.setChildren(twoSubject);
        }
        return list;
    }


}
