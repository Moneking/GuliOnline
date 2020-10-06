package com.smu.edu.controller;


import com.smu.edu.domain.Video;
import com.smu.edu.service.VideoService;
import com.smu.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
@RestController
@RequestMapping("/edu/video")
public class VideoController {
    @Resource
    private VideoService videoService;

    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody Video video){
        videoService.save(video);
        return Result.success();
    }

    @DeleteMapping("/deleteVideo/{id}")
    public Result deleteVideo(@PathVariable String id){
        int result = videoService.removeVideoById(id);
        if (result==1){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @GetMapping("/getVideo/{videoId}")
    public Result getVideo(@PathVariable String videoId){
        Video video = videoService.getById(videoId);
        return Result.success().data("video",video);
    }
    @PostMapping("/updateVideo")
    public Result updateVideo(@RequestBody Video video){
        videoService.updateById(video);
        return Result.success();
    }

}

