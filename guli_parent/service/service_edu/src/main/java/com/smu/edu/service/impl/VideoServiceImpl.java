package com.smu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smu.edu.client.VodClient;
import com.smu.edu.domain.Video;
import com.smu.edu.dao.VideoDao;
import com.smu.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
@Service
@Transactional
public class VideoServiceImpl extends ServiceImpl<VideoDao, Video> implements VideoService {

    @Resource
    private VodClient vodClient;

    @Override
    public void removeByCourseId(String courseId) {

        //根据courseId删视频
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<Video> videos = baseMapper.selectList(wrapperVideo);
        List<String> videoIds = new ArrayList<>();
        for (Video video : videos) {
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoIds.add(videoSourceId);
            }
        }
        if (videoIds.size()>0) {
            vodClient.removeAliYunVideos(videoIds);
        }

        //根据courseId删小节
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }

    @Override
    public int removeVideoById(String id) {
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeAliYunVideo(videoSourceId);
        }
        int result = baseMapper.deleteById(id);
        return result;
    }
}
