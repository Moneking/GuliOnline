package com.smu.sta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterTimeOut.class)
public interface UcenterClient {
    @GetMapping("/ucenter/member/countRegister/{day}")
    Integer countRegister(@PathVariable("day") String day);
}
