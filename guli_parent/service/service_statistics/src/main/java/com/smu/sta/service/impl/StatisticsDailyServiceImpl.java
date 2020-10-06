package com.smu.sta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smu.sta.client.UcenterClient;
import com.smu.sta.dao.StatisticsDailyDao;
import com.smu.sta.domain.StatisticsDaily;
import com.smu.sta.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyDao, StatisticsDaily> implements StatisticsDailyService {

    @Resource
    private UcenterClient ucenterClient;

    @Override
    public void countRegister(String day) {
        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(dayQueryWrapper);

        Integer count = ucenterClient.countRegister(day);
        System.out.println(count);
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(count);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getShowData(String begin, String end, String type) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select(type, "date_calculated");
        wrapper.between("date_calculated", begin, end);

        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        List<String> dateList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for (StatisticsDaily sta : staList) {
            dateList.add(sta.getDateCalculated());
            switch (type) {
                case "register_num":
                    numList.add(sta.getRegisterNum());
                    break;
                case "login_num":
                    numList.add(sta.getLoginNum());
                    break;
                case "video_view_num":
                    numList.add(sta.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(sta.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("dateList",dateList);
        map.put("numList",numList);
        return map;
    }
}
