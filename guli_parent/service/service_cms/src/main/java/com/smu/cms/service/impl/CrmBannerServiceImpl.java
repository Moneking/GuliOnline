package com.smu.cms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smu.cms.domain.CrmBanner;
import com.smu.cms.dao.CrmBannerDao;
import com.smu.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smu.cms.vo.PageList;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-26
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerDao, CrmBanner> implements CrmBannerService {

    @Override
    public PageList<CrmBanner> pageBanner(long current, long size) {
        Page<CrmBanner> page = new Page<>(current,size);
        baseMapper.selectPage(page,null);
        PageList<CrmBanner> pageList = new PageList<>();
        pageList.setTotal(page.getTotal());
        pageList.setRecords(page.getRecords());
        return pageList;
    }

    @Override
    public int addBanner(CrmBanner banner) {
        int result = baseMapper.insert(banner);
        return result;
    }

    @Override
    public CrmBanner getBanner(String id) {
        CrmBanner banner = baseMapper.selectById(id);
        return banner;
    }

    @Override
    public int updateBanner(CrmBanner banner) {
        int result = baseMapper.updateById(banner);
        return result;
    }

    @Override
    public int deleteBanner(String id) {
        int result = baseMapper.deleteById(id);
        return result;
    }

    @Cacheable(value = "banner",key = "'indexBannerList'")
    @Override
    public List<CrmBanner> getBannerList() {
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }
}
