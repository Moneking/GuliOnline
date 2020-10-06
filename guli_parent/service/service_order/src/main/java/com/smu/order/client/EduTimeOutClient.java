package com.smu.order.client;

import com.smu.utils.ClientCourseInfo;
import org.springframework.stereotype.Component;

@Component
public class EduTimeOutClient implements EduClient{
    @Override
    public ClientCourseInfo getCourseInfoByCourseId(String courseId) {
        return null;
    }
}
