package com.smu.edu.client;

import org.springframework.stereotype.Component;

@Component
public class OrderTimeOutClient implements OrderClient{
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
