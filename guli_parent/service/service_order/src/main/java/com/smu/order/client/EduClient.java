package com.smu.order.client;

import com.smu.utils.ClientCourseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-edu",fallback = EduTimeOutClient.class)
@Component
public interface EduClient {
    @GetMapping("/edu/frontCourse/getCourseInfoByCourseId/{courseId}")
    ClientCourseInfo getCourseInfoByCourseId(@PathVariable("courseId") String courseId);
}
