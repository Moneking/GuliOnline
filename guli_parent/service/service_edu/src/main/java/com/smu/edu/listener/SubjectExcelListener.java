package com.smu.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smu.base.exception.GuliException;
import com.smu.edu.domain.Subject;
import com.smu.edu.domain.excel.SubjectData;
import com.smu.edu.service.SubjectService;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public SubjectService subjectService;

    public SubjectExcelListener() {
    }
    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData==null){
            throw new GuliException(20001,"文件数据为空");
        }
        //一行一行读取，第一个值为一级分类，第二个值为二级分类
        //添加一级分类
        Subject existOneSubject = this.existOneSubject(subjectData.getOneSubjectName());
        if (existOneSubject==null){
            existOneSubject = new Subject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }

        //获取一级分类的ID
        String pid = existOneSubject.getId();

        //添加二级分类
        Subject existTwoSubject = this.existTwoSubject(subjectData.getTwoSubjectName(), pid);
        if (existTwoSubject==null){
            existTwoSubject = new Subject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }


    }

    //判断一级分类不能重复添加
    private Subject existOneSubject(String name){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        Subject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }
    //判断二级分类
    private Subject existTwoSubject(String name,String pid){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        Subject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
