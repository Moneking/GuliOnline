package com.smu.order.client;

import com.smu.utils.MemberInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-ucenter",fallback = UcenterTimeOutClient.class)
@Component
public interface UcenterClient {
    @GetMapping("/ucenter/member/getMemberInfoById/{id}")
    MemberInfo getMemberInfoById(@PathVariable("id") String id);
}
