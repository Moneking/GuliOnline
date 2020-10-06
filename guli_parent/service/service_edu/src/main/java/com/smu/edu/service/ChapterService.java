package com.smu.edu.service;

import com.smu.edu.domain.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smu.edu.vo.ChapterList;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterList> listChapter(String courseId);

    boolean deleteChapter(String chapterId);

    void removeByCourseId(String courseId);
}
