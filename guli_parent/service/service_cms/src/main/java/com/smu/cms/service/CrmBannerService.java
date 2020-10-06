package com.smu.cms.service;

import com.smu.cms.domain.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smu.cms.vo.PageList;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-26
 */
public interface CrmBannerService extends IService<CrmBanner> {

    PageList<CrmBanner> pageBanner(long current, long size);

    int addBanner(CrmBanner banner);

    CrmBanner getBanner(String id);

    int updateBanner(CrmBanner banner);

    int deleteBanner(String id);

    List<CrmBanner> getBannerList();
}
