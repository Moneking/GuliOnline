package com.smu.cms.controller;


import com.smu.cms.domain.CrmBanner;
import com.smu.cms.service.CrmBannerService;
import com.smu.cms.vo.PageList;
import com.smu.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/cms/banneradmin")
public class BannerAdminController {

    @Resource
    private CrmBannerService bannerService;

    //分页查询
    @GetMapping("/pageBanner/{current}/{size}")
    public Result pageBanner(@PathVariable("current") long current,@PathVariable("size") long size){
        PageList<CrmBanner> pageList = bannerService.pageBanner(current,size);
        return Result.success().data("pageList",pageList);
    }

    @PostMapping("/addBanner")
    public Result addBanner(@RequestBody CrmBanner banner){
        int result = bannerService.addBanner(banner);
        if (result==1) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @GetMapping("/getBanner/{id}")
    public Result getBanner(@PathVariable String id){
        CrmBanner banner = bannerService.getBanner(id);
        return Result.success().data("banner",banner);
    }

    @PostMapping("/updateBanner")
    public Result updateBanner(@RequestBody CrmBanner banner){
        int result = bannerService.updateBanner(banner);
        if (result==1) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @DeleteMapping("/deleteBanner/{id}")
    public Result deleteBanner(@PathVariable String id){
        int result = bannerService.deleteBanner(id);
        if (result==1) {
            return Result.success();
        }else {
            return Result.error();
        }
    }
}

