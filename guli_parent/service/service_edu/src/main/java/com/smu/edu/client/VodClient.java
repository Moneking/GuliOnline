package com.smu.edu.client;


import com.smu.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    @DeleteMapping("/eduvod/video/removeAliYunVideo/{videoId}")
    Result removeAliYunVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/eduvod/video/removeAliYunVideos")
    Result removeAliYunVideos(@RequestParam("videoIdList") List<String> videoIdList);
}
