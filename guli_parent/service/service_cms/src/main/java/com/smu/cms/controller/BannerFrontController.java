package com.smu.cms.controller;


import com.smu.cms.domain.CrmBanner;
import com.smu.cms.service.CrmBannerService;
import com.smu.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/cms/bannerfront")
public class BannerFrontController {

    @Resource
    private CrmBannerService bannerService;

    @GetMapping("/getBannerList")
    public Result getBannerList(){
        List<CrmBanner> list = bannerService.getBannerList();
        return Result.success().data("list",list);
    }

}

