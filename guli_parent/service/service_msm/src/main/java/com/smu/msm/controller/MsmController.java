package com.smu.msm.controller;

import com.smu.msm.service.MsmService;
import com.smu.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Resource
    private MsmService msmService;

    @GetMapping("/send/{phone}")
    public Result send(@PathVariable String phone){
        boolean isSend = msmService.send(phone);
        if (isSend){
            return Result.success();
        } else {
            return Result.error();
        }

    }
}
