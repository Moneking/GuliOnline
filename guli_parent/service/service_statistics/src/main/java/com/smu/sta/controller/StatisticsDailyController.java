package com.smu.sta.controller;


import com.smu.sta.service.StatisticsDailyService;
import com.smu.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */
@RestController
@RequestMapping("/sta")
public class StatisticsDailyController {

    @Resource
    private StatisticsDailyService statisticsDailyService;

    @GetMapping("/countRegister/{day}")
    public Result countRegister(@PathVariable("day") String day){
        statisticsDailyService.countRegister(day);
        return Result.success();
    }

    @GetMapping("/showData/{begin}/{end}/{type}")
    public Result showData(@PathVariable String begin, @PathVariable String end, @PathVariable String type){
        Map<String, Object> map = statisticsDailyService.getShowData(begin, end, type);
        return Result.success().data(map);
    }
}

