package com.smu.edu.service;

import com.smu.edu.domain.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
public interface VideoService extends IService<Video> {

    void removeByCourseId(String courseId);

    int removeVideoById(String id);
}
