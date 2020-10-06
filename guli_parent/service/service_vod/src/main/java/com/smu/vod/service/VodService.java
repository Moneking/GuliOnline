package com.smu.vod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadAliyunVideo(MultipartFile file);

    void removeAliYunVideo(String videoId) throws ClientException;

    void removeAliYunVideos(List videoIdList) throws ClientException;
}
