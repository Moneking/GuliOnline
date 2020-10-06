package com.smu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.smu.base.exception.GuliException;
import com.smu.utils.Result;
import com.smu.vod.service.VodService;
import com.smu.vod.utils.ConstantVodUtils;
import com.smu.vod.utils.InitVodClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
public class VodController {

    @Resource
    private VodService vodService;

    //上传视频
    @PostMapping("/uploadAliyunVideo")
    public Result uploadAliyunVideo(MultipartFile file){
        String videoId = vodService.uploadAliyunVideo(file);
        return Result.success().data("videoId",videoId);
    }

    //删除单个视频
    @DeleteMapping("/removeAliYunVideo/{videoId}")
    public Result removeAliYunVideo(@PathVariable String videoId){
        try {
            vodService.removeAliYunVideo(videoId);
            return Result.success();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    //删除多个视频
    @DeleteMapping("/removeAliYunVideos")
    public Result removeAliYunVideos(@RequestParam("videoIdList") List<String> videoIdList){
        try {
            vodService.removeAliYunVideos(videoIdList);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"批量删除视频失败");
        }
    }

    //根据视频ID获取视频播放凭证
    @GetMapping("/getAuth/{videoId}")
    public Result getAuth(@PathVariable("videoId") String videoId){
        System.out.println(videoId);
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            request.setVideoId(videoId);
            response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            System.out.println(playAuth);
            return Result.success().data("playAuth",playAuth);
        } catch (ClientException e) {
            e.printStackTrace();
            return null;
        }

    }
}
