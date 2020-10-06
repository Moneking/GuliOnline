package com.smu.order.client;

import com.smu.utils.MemberInfo;
import org.springframework.stereotype.Component;

@Component
public class UcenterTimeOutClient implements UcenterClient {
    @Override
    public MemberInfo getMemberInfoById(String id) {
        return null;
    }
}
