package com.smu.sta.client;

import org.springframework.stereotype.Component;

@Component
public class UcenterTimeOut implements UcenterClient{
    @Override
    public Integer countRegister(String day) {
        return 0;
    }
}
