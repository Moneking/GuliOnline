package com.smu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smu.base.exception.GuliException;
import com.smu.edu.domain.Chapter;
import com.smu.edu.dao.ChapterDao;
import com.smu.edu.domain.Video;
import com.smu.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smu.edu.service.VideoService;
import com.smu.edu.vo.ChapterList;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterDao, Chapter> implements ChapterService {
    @Resource
    private VideoService videoService;

    @Override
    public List<ChapterList> listChapter(String courseId) {
        QueryWrapper<Chapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        wrapperChapter.orderByAsc("sort");
        List<Chapter> chapters = baseMapper.selectList(wrapperChapter);

        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.orderByAsc("sort");
        List<Video> videos = videoService.list(wrapperVideo);

        List<ChapterList> list = new ArrayList<>();
        for (Chapter chapter : chapters) {
            ChapterList chapterList = new ChapterList();
            BeanUtils.copyProperties(chapter,chapterList);
            list.add(chapterList);

            for (Video video : videos) {
                if (video.getChapterId().equals(chapter.getId())){
                    chapterList.getChildren().add(video);
                }
            }
        }
        return list;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<Video> wrapper = new QueryWrapper();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if (count>0){
            throw new GuliException(20001,"不能删除章节");
        }else {
            int res = baseMapper.deleteById(chapterId);
            return res==1;
        }
    }

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
