package com.smu.edu.client;

import com.smu.utils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public Result removeAliYunVideo(String videoId) {
        return Result.error().message("删除视频出错了");
    }

    @Override
    public Result removeAliYunVideos(List<String> videoIdList) {
        return Result.error().message("批量删除视频出错了");
    }
}
