package com.smu.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smu.edu.domain.Subject;
import com.smu.edu.vo.SubjectList;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-20
 */
public interface SubjectService extends IService<Subject> {

    void saveSubject(MultipartFile file,SubjectService subjectService);

    List<SubjectList> getAllSubject();
}
